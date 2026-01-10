package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.example.enums.ErrorCode;
import org.example.exception.ThrowUtils;
import org.example.result.Result;
import org.example.utils.ToolKit;
import org.example.validator.annotation.Mobile;
import org.slf4j.MDC;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nanak
 * 
 * Hello控制器 - 用于展示项目各种功能
 * 包括：统一响应格式、异常处理、参数验证、工具类使用、JWT认证、链路追踪等
 */
@Slf4j
@RestController
@RequestMapping("/api/hello")
@Tag(name = "Hello接口", description = "用于展示项目功能的示例接口")
@Validated
public class HelloController {

    /**
     * 基础Hello接口 - 展示统一响应格式和链路追踪
     */
    @GetMapping
    @Operation(summary = "基础Hello接口", description = "返回欢迎信息，展示统一响应格式和链路追踪")
    public Result<String> hello() {
        // 获取当前请求的traceId（由TraceIdInterceptor自动设置）
        String traceId = MDC.get("traceId");
        log.info("收到Hello请求，当前traceId: {}", traceId);
        
        String message = "Hello! 欢迎使用Spring Boot企业级脚手架！当前请求traceId: " + traceId;
        
        log.info("Hello请求处理完成");
        return Result.success(message);
    }

    /**
     * 展示工具类使用
     */
    @GetMapping("/tools")
    @Operation(summary = "工具类展示", description = "展示各种内置工具类的使用")
    public Result<Map<String, Object>> showTools() {
        log.info("开始展示工具类功能");
        
        Map<String, Object> result = new HashMap<>();
        
        // 1. 日期工具
        String currentTime = ToolKit.DATE.now();
        result.put("currentTime", currentTime);
        log.info("当前时间: {}", currentTime);
        
        // 2. 加密工具
        String originalText = "Hello World";
        String md5Hash = ToolKit.CRYPTO.md5(originalText);
        String randomCode = ToolKit.CRYPTO.randomCode(6);
        result.put("md5Hash", md5Hash);
        result.put("randomCode", randomCode);
        log.info("MD5加密: {} -> {}", originalText, md5Hash);
        
        // 3. JSON工具
        Map<String, String> testObj = Map.of("name", "张三", "age", "25");
        String jsonString = ToolKit.JSON.toJsonString(testObj);
        result.put("jsonExample", jsonString);
        log.info("JSON序列化: {}", jsonString);
        
        // 4. 字符串工具
        String sensitiveData = "13812345678";
        String maskedData = ToolKit.STRING.sensitive(sensitiveData);
        result.put("sensitiveDataMasked", maskedData);
        log.info("敏感数据脱敏: {} -> {}", sensitiveData, maskedData);
        
        // 5. JWT工具
        Long userId = 12345L;
        String accessToken = ToolKit.JWT.generateAccessToken(userId);
        boolean isValidToken = ToolKit.JWT.validateToken(accessToken);
        result.put("jwtToken", accessToken.substring(0, 20) + "...");
        result.put("tokenValid", isValidToken);
        log.info("JWT令牌生成成功，用户ID: {}", userId);
        
        log.info("工具类功能展示完成");
        return Result.success(result);
    }

    /**
     * 展示参数验证
     */
    @PostMapping("/validate")
    @Operation(summary = "参数验证展示", description = "展示自定义参数验证功能")
    public Result<String> validateParams(@Valid @RequestBody UserRequest request) {
        log.info("收到参数验证请求: {}", request);
        
        String message = String.format("参数验证通过！用户名: %s, 手机号: %s", 
                                      request.getUsername(), request.getPhone());
        
        log.info("参数验证成功");
        return Result.success(message);
    }

    /**
     * 展示异常处理 - 业务异常
     */
    @GetMapping("/error/business")
    @Operation(summary = "业务异常展示", description = "展示业务异常的统一处理")
    public Result<String> businessError(@RequestParam(defaultValue = "false") boolean throwError) {
        log.info("收到业务异常测试请求，throwError: {}", throwError);
        
        // 使用ThrowUtils抛出业务异常
        ThrowUtils.throwIf(throwError, ErrorCode.PARAMS_ERROR);
        
        return Result.success("没有抛出异常");
    }

    /**
     * 展示异常处理 - 运行时异常
     */
    @GetMapping("/error/runtime")
    @Operation(summary = "运行时异常展示", description = "展示运行时异常的统一处理")
    public Result<String> runtimeError(@RequestParam(defaultValue = "false") boolean throwError) {
        log.info("收到运行时异常测试请求，throwError: {}", throwError);
        
        if (throwError) {
            // 故意抛出运行时异常
            throw new RuntimeException("这是一个测试用的运行时异常");
        }
        
        return Result.success("没有抛出异常");
    }

    /**
     * 展示参数校验异常
     */
    @GetMapping("/error/validation")
    @Operation(summary = "参数校验异常展示", description = "展示参数校验异常的统一处理")
    public Result<String> validationError(
            @Parameter(description = "用户名，不能为空") 
            @RequestParam @NotBlank(message = "用户名不能为空") String username,
            
            @Parameter(description = "手机号，必须符合格式") 
            @RequestParam @Mobile(message = "手机号格式不正确") String phone) {
        
        log.info("收到参数校验测试请求，用户名: {}, 手机号: {}", username, phone);
        
        return Result.success("参数校验通过");
    }

    /**
     * 展示链路追踪
     */
    @GetMapping("/trace")
    @Operation(summary = "链路追踪展示", description = "展示MDC链路追踪功能")
    public Result<Map<String, String>> showTrace() {
        String traceId = MDC.get("traceId");
        log.info("开始处理链路追踪展示请求");
        
        // 模拟调用其他服务
        simulateServiceCall();
        
        Map<String, String> result = new HashMap<>();
        result.put("traceId", traceId);
        result.put("message", "链路追踪演示完成，请查看日志中的traceId");
        
        log.info("链路追踪展示请求处理完成");
        return Result.success(result);
    }

    /**
     * 模拟服务调用
     */
    private void simulateServiceCall() {
        log.info("模拟调用用户服务");
        
        // 模拟一些业务逻辑
        try {
            Thread.sleep(100); // 模拟耗时操作
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        log.info("用户服务调用完成");
        
        // 模拟调用另一个服务
        simulateAnotherServiceCall();
    }

    /**
     * 模拟另一个服务调用
     */
    private void simulateAnotherServiceCall() {
        log.info("模拟调用订单服务");
        
        // 模拟一些业务逻辑
        try {
            Thread.sleep(50); // 模拟耗时操作
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        log.info("订单服务调用完成");
    }

    /**
     * 健康检查接口
     */
    @GetMapping("/health")
    @Operation(summary = "健康检查", description = "检查应用健康状态")
    public Result<Map<String, Object>> health() {
        Map<String, Object> healthInfo = new HashMap<>();
        healthInfo.put("status", "UP");
        healthInfo.put("timestamp", ToolKit.DATE.now());
        healthInfo.put("version", "1.0.0");
        healthInfo.put("traceId", MDC.get("traceId"));
        
        log.info("健康检查请求处理完成");
        return Result.success(healthInfo);
    }

    /**
     * 用户请求DTO - 用于参数验证展示
     */
    public static class UserRequest {
        @NotBlank(message = "用户名不能为空")
        private String username;
        
        @Mobile(message = "手机号格式不正确")
        private String phone;
        
        // Getters and Setters
        public String getUsername() {
            return username;
        }
        
        public void setUsername(String username) {
            this.username = username;
        }
        
        public String getPhone() {
            return phone;
        }
        
        public void setPhone(String phone) {
            this.phone = phone;
        }
        
        @Override
        public String toString() {
            return "UserRequest{" +
                    "username='" + username + '\'' +
                    ", phone='" + phone + '\'' +
                    '}';
        }
    }
}