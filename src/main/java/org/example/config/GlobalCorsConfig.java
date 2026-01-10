package org.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author nanak
 *
 * 全局跨域(CORS)配置
 * 基于WebMvcConfigurer实现，支持自定义允许的域名、请求方法、请求头
 */
@Configuration
public class GlobalCorsConfig implements WebMvcConfigurer {

    // ===================== 可自定义配置项 =====================
    /**
     * 允许跨域的域名（支持多个，用逗号分隔；* 表示允许所有域名，生产环境不建议）
     */
    private static final String ALLOWED_ORIGINS = "*";

    /**
     * 允许的请求方法（* 表示允许所有HTTP方法）
     * 常用方法：GET,POST,PUT,DELETE,OPTIONS
     */
    private static final String ALLOWED_METHODS = "*";

    /**
     * 允许的请求头（* 表示允许所有请求头）
     * 自定义示例："Authorization,Content-Type,Token"
     */
    private static final String ALLOWED_HEADERS = "*";

    /**
     * 是否允许携带凭证（Cookie、Authorization等）
     * 注意：若设为true，ALLOWED_ORIGINS 不能为 *，需指定具体域名
     */
    private static final boolean ALLOW_CREDENTIALS = true;

    /**
     * 预检请求（OPTIONS）的有效期，单位：秒
     * 有效期内无需重复发送预检请求，减轻服务器压力
     */
    private static final long MAX_AGE = 3600;

    // ===================== 核心跨域配置 =====================
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 配置所有接口的跨域规则（也可指定特定路径，如 /api/**）
        registry.addMapping("/**")
                // 允许的域名（拆分多域名）
                .allowedOriginPatterns(ALLOWED_ORIGINS.split(","))
                // 允许的请求方法
                .allowedMethods(ALLOWED_METHODS.split(","))
                // 允许的请求头
                .allowedHeaders(ALLOWED_HEADERS.split(","))
                // 是否允许携带凭证
                .allowCredentials(ALLOW_CREDENTIALS)
                // 预检请求有效期
                .maxAge(MAX_AGE);
    }
}