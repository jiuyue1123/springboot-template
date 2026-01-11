package org.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author nanak
 *
 * 异步配置类
 */
@Slf4j
@Configuration
@EnableAsync // 必须开启异步支持
public class AsyncGlobalConfig implements AsyncConfigurer {

    /**
     * 1. 配置自定义线程池（生产环境必配，避免默认线程池的资源风险）
     */
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(300);
        executor.setKeepAliveSeconds(300);
        executor.setThreadNamePrefix("Async-Thread-");

        // 关键配置：拒绝策略（核心业务用CallerRunsPolicy，避免任务丢失）
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 关键配置：关闭策略（等待已提交任务完成，避免任务中断）
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(120);

        executor.initialize();
        return executor;
    }

    /**
     * 2. 配置全局 Async 异常处理器
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        // 自定义异常处理逻辑（Lambda实现接口）
        return (throwable, method, params) -> {
            // 步骤1：详细日志记录（包含方法名、参数、异常栈，便于排查）
            log.error("AsyncException:", throwable);
            log.error("method: {} params: {} throwable: info: {} {}", method.getName(), params,
                    throwable.getClass().getSimpleName(),
                    throwable.getMessage());
            // 打印异常栈（关键：避免只看信息无法定位代码行）
            throwable.printStackTrace();

            // 步骤2：业务级异常处理（根据实际场景扩展）
            // 示例1：核心业务异常触发告警（如调用告警接口、发送邮件/短信）
            /*if (throwable instanceof RuntimeException) {
                 alertService.sendAlert("Async核心业务异常：" + throwable.getMessage());
            }
             示例2：异常数据补偿（如标记任务状态为“失败”，便于后续重试）
             if (params[0] instanceof Long taskId) {
                 taskService.markTaskFailed(taskId, throwable.getMessage());
             }*/
        };
    }
}