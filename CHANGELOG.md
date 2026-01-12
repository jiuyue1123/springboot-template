# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.1.0] - 2026-01-12

### Added
- **AOP Framework Implementation**: Complete implementation of IdempotentAspect and TimeConsumingAspect
- **Database Support**: Integrated MyBatis-Plus 3.5.15 ORM framework and Druid 1.2.27 connection pool
- **Error Code Enhancement**: Added OPERATION_ERROR (40500) for operation failures
- **Comprehensive Testing**: Added complete unit tests and integration tests for AOP functionality

### Changed
- **Spring Boot Version**: Downgraded from 4.0.1 to 3.5.9 for better stability and AOP compatibility
- **Dependency Updates**: Updated Knife4j to 4.5.0 with dependency conflict resolution
- **Web Starter**: Changed from spring-boot-starter-webmvc to spring-boot-starter-web
- **Log Language**: Changed all log messages from Chinese to English for better internationalization

### Fixed
- **AOP Compatibility**: Resolved Spring Boot 4.0.1 AOP dependency compatibility issues
- **Test Configuration**: Fixed AOP integration test bean registration issues
- **Dependency Conflicts**: Resolved Knife4j and SpringDoc version conflicts

## [1.0.0] - 2026-01-12

### Added
- **Core Framework**: Spring Boot 4.0.1 enterprise template with comprehensive features
- **Unified Response**: Global `Result<T>` response format with standardized error handling
- **Global Exception Handling**: `@RestControllerAdvice` based exception management
- **Parameter Validation**: Enhanced validation with custom validators (`@Mobile`, `@IdCard`)
- **Multi-Environment Configuration**: Complete dev/test/prod environment isolation
- **Logging System**: Logback with console beautification and file rolling storage
- **API Documentation**: Knife4j (Swagger) integration with online debugging
- **Application Monitoring**: Spring Boot Actuator for health checks and monitoring
- **Graceful Shutdown**: Proper application shutdown handling

#### Utility Classes (100% Test Coverage)
- **JsonUtil**: FastJSON2-based JSON serialization utilities
- **CryptoUtil**: Encryption utilities (MD5, SHA256, AES)
- **DateUtil**: Date and time processing utilities
- **StringUtil**: String manipulation utilities
- **CollectionUtil**: Collection operation utilities
- **ReflectUtil**: Reflection utilities
- **JwtUtil**: JWT token management (access/refresh tokens)
- **ToolKit**: Facade pattern for unified utility access

#### Advanced Features
- **JWT Authentication**: Complete JWT token lifecycle management
- **CORS Support**: Global CORS configuration with customizable rules
- **MDC Distributed Tracing**: Automatic traceId generation and propagation
- **Async Processing**: Custom thread pool configuration with exception handling
- **Custom Validators**: Phone number and ID card validation annotations

#### Testing Infrastructure
- **Comprehensive Unit Tests**: 100% coverage for all utility classes
- **Integration Tests**: Complete testing for controllers and interceptors
- **Test Utilities**: Comprehensive test fixtures and helpers

#### AOP Framework Structure (TODO)
- **@Idempotent**: Annotation for preventing duplicate requests (Spring Boot 4.0.1 compatibility pending)
- **@TimeConsuming**: Annotation for method execution time monitoring (Spring Boot 4.0.1 compatibility pending)

### Technical Specifications
- **Java**: 17
- **Spring Boot**: 4.0.1
- **Build Tool**: Maven 3.6+
- **Dependencies**: Hutool 5.8.38, FastJSON2 2.0.60, Knife4j 4.4.0

### Documentation
- **Comprehensive README**: Complete setup, configuration, and usage guide
- **API Examples**: Full demonstration through HelloController
- **Development Guide**: Best practices and usage patterns
- **Deployment Instructions**: Production deployment guidelines

### Known Issues
- AOP functionality temporarily disabled due to Spring Boot 4.0.1 compatibility issues
- Alternative solutions documented for AOP requirements

[1.1.0]: https://github.com/jiuyue1123/springboot-template/releases/tag/v1.1.0
[1.0.0]: https://github.com/jiuyue1123/springboot-template/releases/tag/v1.0.0