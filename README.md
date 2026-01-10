# Spring Boot 企业级脚手架

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

一个基于 **Spring Boot 4.0.x** 构建的企业级通用后端脚手架，集成主流技术栈和最佳实践，帮助开发者快速搭建稳定、高效的业务系统。

## 📋 目录

- [技术栈](#-技术栈)
- [核心特性](#-核心特性)
- [项目结构](#-项目结构)
- [快速开始](#-快速开始)
- [配置说明](#-配置说明)
- [API文档](#-api文档)
- [开发指南](#-开发指南)
- [部署说明](#-部署说明)

## 🛠 技术栈

| 技术 | 版本 | 说明     |
|------|------|--------|
| Spring Boot | 4.0.1 | 核心框架   |
| Java | 17 | JDK版本  |
| Maven | 3.6+ | 项目管理   |
| Lombok | Latest | 代码简化   |
| Hutool | 5.8.38 | 工具类库   |
| FastJSON2 | 2.0.60 | JSON处理 |
| Knife4j | 4.4.0 | API文档  |
| Logback | Latest | 日志框架   |

## ✨ 核心特性

### 🔥 基础核心

- **统一结果封装**：全局统一的 `Result<T>` 响应格式，包含 code、message、data
- **全局异常处理**：基于 `@RestControllerAdvice` 统一捕获异常，支持业务异常、系统异常、参数校验异常
- **参数校验增强**：集成 `jakarta.validation`，支持自定义校验注解（手机号、身份证等）
- **多环境配置**：完善的 dev/test/prod 环境隔离，支持配置文件拆分与组合
- 全局 CORS 配置：通过WebMvcConfigurer配置跨域规则，支持自定义允许的域名、请求方法、请求头
- **标准日志体系**：基于 Logback，支持控制台美化输出 + 文件滚动存储，集成 MDC 实现全链路 traceId 追踪
- **链路追踪**：TraceIdInterceptor 拦截器自动为每个请求生成唯一 traceId，通过 MDC 机制确保日志链路追踪
- **接口文档**：集成 Knife4j (Swagger)，自动生成接口文档，支持在线调试
- **工具类库**：集成 hutool-all 和内置常用工具类（日期、加密、JSON、集合、反射、JWT等），单元测试通过100%
- **应用监控**：集成 Spring Boot Actuator，提供健康检查和应用监控
- **优雅停机**：支持优雅关闭，确保请求处理完成后再停止服务，仅处理 Web 容器，生产环境需手动关闭自定义资源（如线程池、消息队列消费者、定时任务），通过 `@PreDestroy` 或 `SmartLifecycle` 实现。
- **跨域处理**：全局CORS配置，支持自定义允许的域名、请求方法、请求头

### 🔧 内置工具类

- **JsonUtil**：基于 FastJSON2 的 JSON 序列化工具
- **CryptoUtil**：加密工具类，支持 MD5、SHA256、AES 加密
- **DateUtil**：日期时间处理工具
- **StringUtil**：字符串处理工具
- **CollectionUtil**：集合操作工具
- **ReflectUtil**：反射工具类
- **JwtUtil**：JWT令牌工具，支持访问令牌和刷新令牌的生成、验证、解析

### 🔍 链路追踪组件

- **TraceIdInterceptor**：MDC链路追踪拦截器，自动生成和管理请求链路ID

### 📝 自定义验证器

- **@Mobile**：手机号格式验证
- **@IdCard**：身份证号格式验证
- 支持扩展更多自定义验证注解

## 📁 项目结构

```
src/main/java/org/example/
├── config/                 # 配置类
│   ├── GlobalCorsConfig.java  # 全局跨域配置config/                
│   └── WebMvcConfig.java  # WebMvc配置
├── constant/              # 常量定义
├── controller/            # 控制器层
│   └── HelloController.java   # 示例控制器（展示各种功能）
├── enums/                 # 枚举类
│   └── ErrorCode.java     # 错误码枚举
├── exception/             # 异常处理
│   ├── BusinessException.java
│   ├── GlobalExceptionHandler.java
│   └── ThrowUtils.java
├── interceptor/           # 拦截器
│   └── TraceIdInterceptor.java  # MDC链路追踪拦截器
├── result/                # 响应结果封装
│   ├── PageResult.java    # 分页结果
│   └── Result.java        # 统一响应结果
├── utils/                 # 工具类
│   ├── CollectionUtil.java
│   ├── CryptoUtil.java
│   ├── DateUtil.java
│   ├── JsonUtil.java
│   ├── JwtUtil.java
│   ├── ReflectUtil.java
│   ├── StringUtil.java
│   └── ToolKit.java
├── validator/             # 自定义验证器
│   ├── annotation/        # 验证注解
│   └── constraint/        # 验证器实现
└── SpringbootTemplateApplication.java
```

## 🚀 快速开始

### 环境要求

- JDK 17+
- Maven 3.6+

### 1. 克隆项目

```bash
git clone https://github.com/jiuyue1123/springboot-template
cd springboot-template
```

### 2. 启动应用

```bash
# 开发环境启动
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# 或者打包后启动
mvn clean package
java -jar target/springboot-template-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

### 4. 访问应用
- 应用地址：http://localhost:8081
- API文档：http://localhost:8081/swagger-ui.html
- 健康检查：http://localhost:8081/actuator/health
- 示例接口：http://localhost:8081/api/hello

## ⚙️ 配置说明

### 环境配置

项目支持多环境配置，通过 `spring.profiles.active` 切换：

- **dev**：开发环境（端口8081，详细日志）
- **test**：测试环境
- **prod**：生产环境（端口8080，精简日志）

### 日志配置

- **控制台输出**：彩色格式，包含 traceId
- **文件输出**：`./logs/springboot-demo/app.log`
- **错误日志**：`./logs/springboot-demo/error.log`
- **日志滚动**：按日期和大小滚动，保留7天
- **链路追踪**：每个请求自动生成唯一 traceId，所有日志自动携带，支持跨服务传递

### 加密配置

在 `application.yml` 中配置 AES 密钥：

```yaml
aes:
  key: xxxxxxxxxxxxxxxxx  # 16位密钥
```

## 📖 API文档

项目集成了 Knife4j，提供美观的 API 文档界面：

- 访问地址：http://localhost:8081/swagger-ui.html
- 支持在线调试
- 自动生成接口文档

### 示例接口

项目提供了完整的示例接口 `HelloController`，展示了所有核心功能：

- `GET /api/hello` - 基础接口，展示统一响应格式和链路追踪
- `GET /api/hello/tools` - 工具类功能展示
- `POST /api/hello/validate` - 参数验证展示
- `GET /api/hello/error/business` - 业务异常处理展示
- `GET /api/hello/error/runtime` - 运行时异常处理展示
- `GET /api/hello/error/validation` - 参数校验异常展示
- `GET /api/hello/trace` - 链路追踪功能展示
- `GET /api/hello/health` - 健康检查接口

## 💻 开发指南

### 统一响应格式

```java
// 成功响应
return Result.success(data);
return Result.success();

// 错误响应
return Result.error(ErrorCode.PARAMS_ERROR);
return Result.error(40000, "参数错误");
```

### 异常处理

```java
// 抛出业务异常
ThrowUtils.throwIf(condition, ErrorCode.PARAMS_ERROR);

// 自定义业务异常
throw new BusinessException(ErrorCode.PARAMS_ERROR, "详细错误信息");
```

### 参数验证

```java
public class UserDTO {
    @Mobile(message = "手机号格式不正确")
    private String phone;
    
    @IdCard(message = "身份证号格式不正确")
    private String idCard;
    
    @NotBlank(message = "用户名不能为空")
    private String username;
}
```

### 工具类使用

```java
// 1. JSON 序列化与反序列化：通过 ToolKit.JSON 调用
String json = ToolKit.JSON.toJsonString(object);
User user = ToolKit.JSON.parseObject(json, User.class);

// 2. 加密处理：通过 ToolKit.CRYPTO 调用
// MD5 加密
String md5 = ToolKit.CRYPTO.md5("password");
// AES 加密（可自定义密钥）
String encrypted = ToolKit.CRYPTO.aesEncrypt("sensitive data");

// 3. 生成随机验证码：通过 ToolKit.CRYPTO 调用
String code = ToolKit.CRYPTO.randomCode(6);

// 4. JWT令牌操作：通过 ToolKit.JWT 调用
// 生成访问令牌（2小时有效期）
String accessToken = ToolKit.JWT.generateAccessToken(userId);
// 生成刷新令牌（7天有效期）
String refreshToken = ToolKit.JWT.generateRefreshToken(userId);
// 验证令牌
boolean isValid = ToolKit.JWT.validateToken(accessToken);
// 解析令牌获取载荷
Map<String, Object> payload = ToolKit.JWT.parseToken(accessToken);
// 刷新访问令牌
String newAccessToken = ToolKit.JWT.refreshToken(refreshToken);
```

### 链路追踪使用

项目集成了 MDC 链路追踪，每个请求都会自动生成唯一的 traceId：

```java
// 在业务代码中，日志会自动携带 traceId
@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    
    @GetMapping("/users/{id}")
    public Result<User> getUser(@PathVariable Long id) {
        // 日志会自动包含当前请求的 traceId
        log.info("查询用户信息，用户ID: {}", id);
        
        // 业务逻辑...
        User user = userService.findById(id);
        
        log.info("用户查询完成，用户名: {}", user.getName());
        return Result.success(user);
    }
}

// 跨服务调用时传递 traceId
@Service
public class ExternalService {
    
    public void callExternalApi() {
        // 获取当前请求的 traceId
        String traceId = MDC.get("traceId");
        
        // 在调用外部服务时传递 traceId
        HttpHeaders headers = new HttpHeaders();
        headers.set("traceId", traceId);
        
        // 发起HTTP请求...
    }
}
```

## 🚀 部署说明

### 1. 打包应用

```bash
mvn clean package -Dmaven.test.skip=true
```

### 2. 生产环境启动

```bash
java -jar springboot-template-0.0.1-SNAPSHOT.jar \
  --spring.profiles.active=prod \
  --server.port=8080
```

### 3. Docker 部署

```dockerfile
FROM openjdk:17-jre-slim
COPY target/springboot-template-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=prod"]
```

### 4. 健康检查

```bash
curl http://localhost:8080/actuator/health
```

## 📄 许可证

本项目采用 MIT 许可证。

## 🤝 贡献

欢迎提交 Issue 和 Pull Request 来改进这个项目！