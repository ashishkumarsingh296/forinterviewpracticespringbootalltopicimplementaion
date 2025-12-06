# 🚀 Spring Boot Auto-Scaling MYAPP-Demo Project
# Author: Ashish Singh

A production-style Spring Boot application can be deployed on **multiple Tomcat
instances (8080,8081,8082, 8083, 8084)** with:

### Tech Stack:

-   ✅ Java 21
-   ✅ Spring Boot 3.5.x
-   ✅ JWT-based Security
-   ✅ Spring Security
-   ✅ Logback Logging
-   ✅ Swagger (OpenAPI) Documentation
-   ✅ Jenkins CI/CD with GitHub Integration
-   ✅ Auto-Scaling using Cron + Load Monitoring
-   ✅ Coustom Annotation
-   ✅ Mesaages Constants
-   ✅ Jenkins (CI/CD)
-   ✅ MySQL
-   ✅ Nginx Load Balancer
-   ✅ Rate Limiting (AOP)
-   ✅ Redis
-   ✅ JPA / Hibernate
-   ✅ 
-   ✅ Docker + Docker Compose

------------------------------------------------------------------------
##  ✅ 1. PROJECT OVERVIEW
     
     Yeh project real-world enterprise grade Spring Boot application hai jisme following implementations ki gayi hain:

     JWT based Authentication & Authorization

     Role based access control (ADMIN, USER)

     Product Management APIs

     Redis caching & rate limiting

     Global Exception Handling

     Centralized Logging (Logback + MDC)

     Swagger UI for API testing

     Jenkins CI/CD with GitHub integration

     Dockerized deployment

     Nginx based Load Balancing

     CPU Load based Auto Scaling using Cron

----------------------------------------------------------------------------     

## ✅ 2. MAJOR FEATURES

## 🔐 Security & Authentication

    JWT Token Generation

    Refresh Token API

    Custom JWT Filter

    Role Based Access using Spring Security

    Custom Access Denied & Authentication Entry Point

## ⚙️ Backend Core Features

    User Management

    Product CRUD APIs

    Audit Log using AOP

    Background Task Service

## Message Internationalization (i18n)

⚡  Redis & Rate Limiting

    Redis Cache Integration

    Custom @RateLimit annotation

    Aspect Oriented Rate Limiting control

    Resilience4j integration

## 📄 Swagger & API Docs

    Swagger YAML Configuration

    Swagger UI enabled for API testing

## 📜 Logging

    Logback + Log4j2

    MDC Logging Filter

    Daily rolling logs

    Centralized application logs    

------------------------------------------------------------------------

## 🗂 Project Structure

src
│   ├───main
│   │   ├───java
│   │   │   └───com
│   │   │       └───example
│   │   │           └───forinterviewpracticespringbootalltopicimplementaion
│   │   │               │   ForinterviewpracticespringbootalltopicimplementaionApplication.java
│   │   │               │
│   │   │               ├───common
│   │   │               │       ActionConstants.java
│   │   │               │       ApiErrorResponse.java
│   │   │               │       AppErrorCodesI.java
│   │   │               │       AuditAspect.java
│   │   │               │       BaseException.java
│   │   │               │       BaseResponse.java
│   │   │               │       RateLimitAspect.java
│   │   │               │
│   │   │               ├───configuration
│   │   │               │       DataInit.java
│   │   │               │       MessagesConfig.java
│   │   │               │       OpenApiYamlConfig.java
│   │   │               │       RedisConfig.java
│   │   │               │       SecurityConfig.java
│   │   │               │       StartupLogger.java
│   │   │               │
│   │   │               ├───constants
│   │   │               │       ApiPathConstants.java
│   │   │               │       ApiResponseConstants.java
│   │   │               │       EntityConstants.java
│   │   │               │       MessagesConstant.java
│   │   │               │       RoleConstants.java
│   │   │               │       SchemaConstants.java
│   │   │               │       SecurityExpressions.java
│   │   │               │
│   │   │               ├───controller
│   │   │               │       AuditLogController.java
│   │   │               │       AuthController.java
│   │   │               │       ProductController.java
│   │   │               │       UserController.java
│   │   │               │
│   │   │               ├───customanotation
│   │   │               │       Auditable.java
│   │   │               │       RateLimit.java
│   │   │               │
│   │   │               ├───dto
│   │   │               │       AddProductDTO.java
│   │   │               │       AddUserDto.java
│   │   │               │       AuthRequest.java
│   │   │               │       AuthResponse.java
│   │   │               │       ModifyProductDTO.java
│   │   │               │       ModifyUserDTO.java
│   │   │               │       RefreshTokenRequest.java
│   │   │               │       RegisterRequest.java
│   │   │               │
│   │   │               ├───entity
│   │   │               │       AuditLog.java
│   │   │               │       Product.java
│   │   │               │       Role.java
│   │   │               │       User.java
│   │   │               │
│   │   │               ├───exception
│   │   │               │       GlobalExceptionHandler.java
│   │   │               │       ResourceNotFoundException.java
│   │   │               │
│   │   │               ├───logging
│   │   │               │       MdcLoggingFilter.java
│   │   │               │
│   │   │               ├───mapper
│   │   │               │       ProductMapper.java
│   │   │               │       UserMapper.java
│   │   │               │
│   │   │               ├───preference
│   │   │               │       SystemPreferences.java
│   │   │               │
│   │   │               ├───repository
│   │   │               │       AuditLogRepository.java
│   │   │               │       ProductRepository.java
│   │   │               │       UserRepository.java
│   │   │               │
│   │   │               ├───response
│   │   │               │       ApiResponse.java
│   │   │               │
│   │   │               ├───security
│   │   │               │       CustomUserDetailsService.java
│   │   │               │       JwtAccessDeniedHandler.java
│   │   │               │       JwtAuthEntryPoint.java
│   │   │               │       JwtAuthFilter.java
│   │   │               │       JwtService.java
│   │   │               │
│   │   │               ├───service
│   │   │               │       AuditLogService.java
│   │   │               │       BackgroundTaskService.java
│   │   │               │       ProductService.java
│   │   │               │       ProductServiceImpl.java
│   │   │               │       RateLimiterService.java
│   │   │               │       UserService.java
│   │   │               │
│   │   │               └───utils
│   │   │                       AppLogger.java
│   │   │                       RestAPIStringMessageParser.java
│   │   │
│   │   └───resources
│   │       │   application-local.properties
│   │       │   application-wsl-dev.properties
│   │       │   application-wsl-qa.properties
│   │       │   application-wsl.properties
│   │       │   application.properties
│   │       │   docke
│   │       │   log4j2.xml
│   │       │   logback-spring.xml
│   │       │   logs
│   │       │
│   │       ├───i18n
│   │       │       messages_en_GB.properties
│   │       │
│   │       ├───static
│   │       ├───swagger
│   │       │       swagger.yml
│   │       │
│   │       └───templates
│   └───test
│       └───java
│           └───com
│               └───example
│                   └───forinterviewpracticespringbootalltopicimplementaion
│                           ForinterviewpracticespringbootalltopicimplementaionApplicationTests.java
│
└───target
    │   myapp-demo.war
    │   myapp-demo.war.original
    │
    ├───classes
    │   │   application-dev.properties
    │   │   application-local.properties
    │   │   application-qa.properties
    │   │   application-wsl.properties
    │   │   application.properties
    │   │   docke
    │   │   logback-spring.xml
    │   │   logs
    │   │
    │   ├───com
    │   │   └───example
    │   │       └───forinterviewpracticespringbootalltopicimplementaion
    │   │           │   ForinterviewpracticespringbootalltopicimplementaionApplication.class
    │   │           │
    │   │           ├───common
    │   │           │       ActionConstants.class
    │   │           │       ApiErrorResponse.class
    │   │           │       AppErrorCodesI.class
    │   │           │       AuditAspect.class
    │   │           │       BaseException.class
    │   │           │       BaseResponse.class
    │   │           │       RateLimitAspect.class
    │   │           │
    │   │           ├───configuration
    │   │           │       DataInit.class
    │   │           │       MessagesConfig.class
    │   │           │       OpenApiYamlConfig.class
    │   │           │       RedisConfig.class
    │   │           │       SecurityConfig.class
    │   │           │       StartupLogger.class
    │   │           │
    │   │           ├───constants
    │   │           │       ApiPathConstants.class
    │   │           │       ApiResponseConstants.class
    │   │           │       EntityConstants.class
    │   │           │       MessagesConstant.class
    │   │           │       RoleConstants.class
    │   │           │       SchemaConstants.class
    │   │           │       SecurityExpressions.class
    │   │           │
    │   │           ├───controller
    │   │           │       AuditLogController.class
    │   │           │       AuthController.class
    │   │           │       ProductController.class
    │   │           │       UserController.class
    │   │           │
    │   │           ├───customanotation
    │   │           │       Auditable.class
    │   │           │       RateLimit.class
    │   │           │
    │   │           ├───dto
    │   │           │       AddProductDTO.class
    │   │           │       AddUserDto.class
    │   │           │       AuthRequest.class
    │   │           │       AuthResponse.class
    │   │           │       ModifyProductDTO.class
    │   │           │       ModifyUserDTO.class
    │   │           │       RefreshTokenRequest.class
    │   │           │       RegisterRequest.class
    │   │           │
    │   │           ├───entity
    │   │           │       AuditLog$AuditLogBuilder.class
    │   │           │       AuditLog.class
    │   │           │       Product.class
    │   │           │       Role.class
    │   │           │       User$UserBuilder.class
    │   │           │       User.class
    │   │           │
    │   │           ├───exception
    │   │           │       GlobalExceptionHandler.class
    │   │           │       ResourceNotFoundException.class
    │   │           │
    │   │           ├───logging
    │   │           │       MdcLoggingFilter.class
    │   │           │
    │   │           ├───mapper
    │   │           │       ProductMapper.class
    │   │           │       UserMapper.class
    │   │           │
    │   │           ├───preference
    │   │           │       SystemPreferences.class
    │   │           │
    │   │           ├───repository
    │   │           │       AuditLogRepository.class
    │   │           │       ProductRepository.class
    │   │           │       UserRepository.class
    │   │           │
    │   │           ├───response
    │   │           │       ApiResponse.class
    │   │           │
    │   │           ├───security
    │   │           │       CustomUserDetailsService.class
    │   │           │       JwtAccessDeniedHandler.class
    │   │           │       JwtAuthEntryPoint.class
    │   │           │       JwtAuthFilter.class
    │   │           │       JwtService.class
    │   │           │
    │   │           ├───service
    │   │           │       AuditLogService.class
    │   │           │       BackgroundTaskService.class
    │   │           │       ProductService.class
    │   │           │       ProductServiceImpl.class
    │   │           │       RateLimiterService.class
    │   │           │       UserService.class
    │   │           │
    │   │           └───utils
    │   │                   AppLogger.class
    │   │                   RestAPIStringMessageParser.class
    │   │
    │   ├───i18n
    │   │       messages_en_GB.properties
    │   │
    │   └───swagger
    │           swagger.yml
    │
    ├───generated-sources
    │   └───annotations
    ├───generated-test-sources
    │   └───test-annotations
    ├───maven-archiver
    │       pom.properties
    │
    ├───maven-status
    │   └───maven-compiler-plugin
    │       ├───compile
    │       │   └───default-compile
    │       │           createdFiles.lst
    │       │           inputFiles.lst
    │       │
    │       └───testCompile
    │           └───default-testCompile
    │                   createdFiles.lst
    │                   inputFiles.lst
    │
    ├───myapp-demo
    │   ├───META-INF
    │   └───WEB-INF
    │       ├───classes
    │       │   │   application-dev.properties
    │       │   │   application-local.properties
    │       │   │   application-qa.properties
    │       │   │   application-wsl.properties
    │       │   │   application.properties
    │       │   │   docke
    │       │   │   logback-spring.xml
    │       │   │   logs
    │       │   │
    │       │   ├───com
    │       │   │   └───example
    │       │   │       └───forinterviewpracticespringbootalltopicimplementaion
    │       │   │           │   ForinterviewpracticespringbootalltopicimplementaionApplication.class
    │       │   │           │
    │       │   │           ├───common
    │       │   │           │       ActionConstants.class
    │       │   │           │       ApiErrorResponse.class
    │       │   │           │       AppErrorCodesI.class
    │       │   │           │       AuditAspect.class
    │       │   │           │       BaseException.class
    │       │   │           │       BaseResponse.class
    │       │   │           │       RateLimitAspect.class
    │       │   │           │
    │       │   │           ├───configuration
    │       │   │           │       DataInit.class
    │       │   │           │       MessagesConfig.class
    │       │   │           │       OpenApiYamlConfig.class
    │       │   │           │       RedisConfig.class
    │       │   │           │       SecurityConfig.class
    │       │   │           │       StartupLogger.class
    │       │   │           │
    │       │   │           ├───constants
    │       │   │           │       ApiPathConstants.class
    │       │   │           │       ApiResponseConstants.class
    │       │   │           │       EntityConstants.class
    │       │   │           │       MessagesConstant.class
    │       │   │           │       RoleConstants.class
    │       │   │           │       SchemaConstants.class
    │       │   │           │       SecurityExpressions.class
    │       │   │           │
    │       │   │           ├───controller
    │       │   │           │       AuditLogController.class
    │       │   │           │       AuthController.class
    │       │   │           │       ProductController.class
    │       │   │           │       UserController.class
    │       │   │           │
    │       │   │           ├───customanotation
    │       │   │           │       Auditable.class
    │       │   │           │       RateLimit.class
    │       │   │           │
    │       │   │           ├───dto
    │       │   │           │       AddProductDTO.class
    │       │   │           │       AddUserDto.class
    │       │   │           │       AuthRequest.class
    │       │   │           │       AuthResponse.class
    │       │   │           │       ModifyProductDTO.class
    │       │   │           │       ModifyUserDTO.class
    │       │   │           │       RefreshTokenRequest.class
    │       │   │           │       RegisterRequest.class
    │       │   │           │
    │       │   │           ├───entity
    │       │   │           │       AuditLog$AuditLogBuilder.class
    │       │   │           │       AuditLog.class
    │       │   │           │       Product.class
    │       │   │           │       Role.class
    │       │   │           │       User$UserBuilder.class
    │       │   │           │       User.class
    │       │   │           │
    │       │   │           ├───exception
    │       │   │           │       GlobalExceptionHandler.class
    │       │   │           │       ResourceNotFoundException.class
    │       │   │           │
    │       │   │           ├───logging
    │       │   │           │       MdcLoggingFilter.class
    │       │   │           │
    │       │   │           ├───mapper
    │       │   │           │       ProductMapper.class
    │       │   │           │       UserMapper.class
    │       │   │           │
    │       │   │           ├───preference
    │       │   │           │       SystemPreferences.class
    │       │   │           │
    │       │   │           ├───repository
    │       │   │           │       AuditLogRepository.class
    │       │   │           │       ProductRepository.class
    │       │   │           │       UserRepository.class
    │       │   │           │
    │       │   │           ├───response
    │       │   │           │       ApiResponse.class
    │       │   │           │
    │       │   │           ├───security
    │       │   │           │       CustomUserDetailsService.class
    │       │   │           │       JwtAccessDeniedHandler.class
    │       │   │           │       JwtAuthEntryPoint.class
    │       │   │           │       JwtAuthFilter.class
    │       │   │           │       JwtService.class
    │       │   │           │
    │       │   │           ├───service
    │       │   │           │       AuditLogService.class
    │       │   │           │       BackgroundTaskService.class
    │       │   │           │       ProductService.class
    │       │   │           │       ProductServiceImpl.class
    │       │   │           │       RateLimiterService.class
    │       │   │           │       UserService.class
    │       │   │           │
    │       │   │           └───utils
    │       │   │                   AppLogger.class
    │       │   │                   RestAPIStringMessageParser.class
    │       │   │
    │       │   ├───i18n
    │       │   │       messages_en_GB.properties
    │       │   │
    │       │   └───swagger
    │       │           swagger.yml
    │       │
    │       └───lib
    │               angus-activation-2.0.3.jar
    │               antlr4-runtime-4.13.0.jar
    │               aspectjweaver-1.9.24.jar
    │               bcprov-jdk18on-1.80.jar
    │               byte-buddy-1.17.8.jar
    │               classmate-1.7.1.jar
    │               commons-lang3-3.17.0.jar
    │               HdrHistogram-2.2.2.jar
    │               hibernate-commons-annotations-7.0.3.Final.jar
    │               hibernate-core-6.6.33.Final.jar
    │               hibernate-validator-8.0.3.Final.jar
    │               HikariCP-6.3.3.jar
    │               istack-commons-runtime-4.1.2.jar
    │               jackson-annotations-2.19.2.jar
    │               jackson-core-2.19.2.jar
    │               jackson-databind-2.19.2.jar
    │               jackson-dataformat-yaml-2.19.2.jar
    │               jackson-datatype-jdk8-2.19.2.jar
    │               jackson-datatype-jsr310-2.19.2.jar
    │               jackson-module-parameter-names-2.19.2.jar
    │               jakarta.activation-api-2.1.4.jar
    │               jakarta.annotation-api-2.1.1.jar
    │               jakarta.inject-api-2.0.1.jar
    │               jakarta.persistence-api-3.1.0.jar
    │               jakarta.transaction-api-2.0.1.jar
    │               jakarta.validation-api-3.0.2.jar
    │               jakarta.xml.bind-api-4.0.4.jar
    │               jandex-3.2.0.jar
    │               jaxb-core-4.0.6.jar
    │               jaxb-runtime-4.0.6.jar
    │               jboss-logging-3.6.1.Final.jar
    │               jjwt-api-0.11.5.jar
    │               jjwt-impl-0.11.5.jar
    │               jjwt-jackson-0.11.5.jar
    │               jspecify-1.0.0.jar
    │               jul-to-slf4j-2.0.17.jar
    │               LatencyUtils-2.0.3.jar
    │               lettuce-core-6.6.0.RELEASE.jar
    │               log4j-api-2.24.3.jar
    │               log4j-to-slf4j-2.24.3.jar
    │               logback-classic-1.5.20.jar
    │               logback-core-1.5.20.jar
    │               mapstruct-1.5.5.Final.jar
    │               micrometer-commons-1.15.5.jar
    │               micrometer-core-1.15.5.jar
    │               micrometer-jakarta9-1.15.5.jar
    │               micrometer-observation-1.15.5.jar
    │               mysql-connector-j-9.4.0.jar
    │               netty-buffer-4.1.128.Final.jar
    │               netty-codec-4.1.128.Final.jar
    │               netty-common-4.1.128.Final.jar
    │               netty-handler-4.1.128.Final.jar
    │               netty-resolver-4.1.128.Final.jar
    │               netty-transport-4.1.128.Final.jar
    │               netty-transport-native-unix-common-4.1.128.Final.jar
    │               reactive-streams-1.0.4.jar
    │               reactor-core-3.7.12.jar
    │               redis-authx-core-0.1.1-beta2.jar
    │               resilience4j-annotations-2.2.0.jar
    │               resilience4j-circuitbreaker-2.2.0.jar
    │               resilience4j-circularbuffer-2.2.0.jar
    │               resilience4j-consumer-2.2.0.jar
    │               resilience4j-core-2.2.0.jar
    │               resilience4j-framework-common-2.2.0.jar
    │               resilience4j-micrometer-2.2.0.jar
    │               resilience4j-ratelimiter-2.2.0.jar
    │               resilience4j-retry-2.2.0.jar
    │               resilience4j-spring-boot3-2.2.0.jar
    │               resilience4j-spring6-2.2.0.jar
    │               resilience4j-timelimiter-2.2.0.jar
    │               slf4j-api-2.0.17.jar
    │               snakeyaml-2.4.jar
    │               spring-aop-6.2.12.jar
    │               spring-aspects-6.2.12.jar
    │               spring-beans-6.2.12.jar
    │               spring-boot-3.5.7.jar
    │               spring-boot-actuator-3.5.7.jar
    │               spring-boot-actuator-autoconfigure-3.5.7.jar
    │               spring-boot-autoconfigure-3.5.7.jar
    │               spring-boot-configuration-processor-3.5.7.jar
    │               spring-boot-starter-3.5.7.jar
    │               spring-boot-starter-actuator-3.5.7.jar
    │               spring-boot-starter-cache-3.5.7.jar
    │               spring-boot-starter-data-jpa-3.5.7.jar
    │               spring-boot-starter-data-redis-3.5.7.jar
    │               spring-boot-starter-jdbc-3.5.7.jar
    │               spring-boot-starter-json-3.5.7.jar
    │               spring-boot-starter-logging-3.5.7.jar
    │               spring-boot-starter-security-3.5.7.jar
    │               spring-boot-starter-validation-3.5.7.jar
    │               spring-boot-starter-web-3.5.7.jar
    │               spring-cloud-circuitbreaker-resilience4j-3.3.0.jar
    │               spring-cloud-commons-4.3.0.jar
    │               spring-cloud-context-4.3.0.jar
    │               spring-cloud-starter-4.3.0.jar
    │               spring-cloud-starter-circuitbreaker-resilience4j-3.3.0.jar
    │               spring-context-6.2.12.jar
    │               spring-context-support-6.2.12.jar
    │               spring-core-6.2.12.jar
    │               spring-data-commons-3.5.5.jar
    │               spring-data-jpa-3.5.5.jar
    │               spring-data-keyvalue-3.5.5.jar
    │               spring-data-redis-3.5.5.jar
    │               spring-expression-6.2.12.jar
    │               spring-jcl-6.2.12.jar
    │               spring-jdbc-6.2.12.jar
    │               spring-orm-6.2.12.jar
    │               spring-oxm-6.2.12.jar
    │               spring-security-config-6.5.6.jar
    │               spring-security-core-6.5.6.jar
    │               spring-security-crypto-6.5.6.jar
    │               spring-security-web-6.5.6.jar
    │               spring-tx-6.2.12.jar
    │               spring-web-6.2.12.jar
    │               spring-webmvc-6.2.12.jar
    │               springdoc-openapi-starter-common-2.7.0.jar
    │               springdoc-openapi-starter-webmvc-api-2.7.0.jar
    │               springdoc-openapi-starter-webmvc-ui-2.7.0.jar
    │               swagger-annotations-jakarta-2.2.25.jar
    │               swagger-core-jakarta-2.2.25.jar
    │               swagger-models-jakarta-2.2.25.jar
    │               swagger-ui-5.18.2.jar
    │               tomcat-embed-el-10.1.48.jar
    │               txw2-4.0.6.jar
    │               webjars-locator-lite-1.1.2.jar
    │
    └───test-classes
        └───com
            └───example
                └───forinterviewpracticespringbootalltopicimplementaion
                        ForinterviewpracticespringbootalltopicimplementaionApplicationTests.class

------------------------------------------------------------------------

✅ 3. ENVIRONMENT CONFIGURATION
Environment	  Port	    Profile
LOCAL	      8080	    local
WSL DEV	      8081	    wsl-dev
WSL QA	      8082	    wsl-qa
WSL PROD-1	  8082	    wsl-prod
WSL PROD-2	  8083	    wsl-prod
WSL PROD-3	  8084	    wsl-prod


-------------------------------------------------------------------------
✅ 4. BUILD & RUN COMMANDS
▶️ Local Run
mvn clean install
java -jar target/myapp-demo.war

▶️ WSL Run
java -jar -Dspring.profiles.active=wsl-prod target/myapp-demo.war



-------------------------------------------------------------------------
✅ 5. DOCKER DEPLOYMENT
Build Image
docker build -t myapp .

Start using Docker Compose
docker-compose up -d

Services:

Spring Boot App
MySQL
Nginx Load Balancer




------------------------------------------------------------------------

✅ 6. 🔐 Security Features

-   JWT Authentication
-   Spring Security Configuration
-   Stateless API Authentication
-   Authorization using roles
-   Protected REST APIs

------------------------------------------------------------------------

✅ 7. 📊 Logging

-   Logback enabled
-   Centralized logging support
-   Auto-scale activity stored in:

```{=html}
<!-- -->
```
    /home/aashudev/autoscale/autoscale.log

------------------------------------------------------------------------

## 🔄 CI/CD with Jenkins

-   GitHub PipleLine Jobs hit mannually 
-   Jenkins Pipeline:
    -   Pulls code from GitHub
    -   Builds WAR using Maven
    -   Deploys to Tomcat instances
    -   Runs Health Checks
    -   Rolling Deployment Supported

------------------------------------------------------------------------

## 📘 Swagger API Documentation

Access Swagger UI wsl prod-server for further so on:

    http://localhost:8082/swagger-ui.html

(Repeat for 8083, 8084 when scaled)

------------------------------------------------------------------------

## ⚙ Auto-Scaling Ports for Production

  Instance   Port
  ---------- ------
  PROD-1     8082
  PROD-2     8083
  PROD-3     8084

Auto-scaling is triggered by: - System Load - Cron Job Execution

------------------------------------------------------------------------

✅ 8. How to Test Auto-Scaling

   ✅  AUTO SCALING TESTING STEPS (IMPORTANT ✅)
   ✅ Step 1: Install Stress Tool
       sudo apt install stress -y

   ✅ Step 2: Monitor Logs
       tail -f /home/aashudev/autoscale/autoscale.log

   ✅ Step 3: Generate Load
       stress --cpu 4 --timeout 120

   ✅ Step 4: Verify New Servers Started
         sudo lsof -i :8083
         sudo lsof -i :8084

   ✅ Step 5: Stop Load
       pkill stress

   ✅ Step 6: Verify Auto Scale Down
       sudo lsof -i :8083
       sudo lsof -i :8084


### Expected Behavior:

    High load → PROD-2 & PROD-3 will start automatically
    Low load → PROD-3 & PROD-2 will stop automatically

------------------------------------------------------------------------

## 🔍 Health Check Endpoint

    http://localhost:8082/actuator/health

(Repeat for 8083 and 8084)

------------------------------------------------------------------------

## 🧪 API Testing

Test any secured API using: - Authorization Header with JWT Token -
Postman or Swagger UI

------------------------------------------------------------------------

✅ 9. NGINX LOAD BALANCING

Nginx handles:

Traffic distribution between:

PROD-1 → 8082

PROD-2 → 8083

PROD-3 → 8084

Health check based routing

Zero downtime access


------------------------------------------------------------------------
✅ 10. AUTO SCALING (CPU LOAD BASED)
Auto Scaling Scripts Location:
/home/aashudev/autoscale/

Cron Jobs:
* * * * * /home/aashudev/autoscale/start-on-load.sh
* * * * * /home/aashudev/autoscale/stop-on-low-load.sh


Scaling Rules:
CPU Load	Action
Load > 4	Start PROD-2 (8083)
Load > 6	Start PROD-3 (8084)
Load < 3	Stop PROD-3
Load < 2	Stop PROD-2


Log File:

/home/aashudev/autoscale/autoscale.log

------------------------------------------------------------------------

## 🧾 WAR Deployment

    target/demo.war

Deploy the WAR to: - Tomcat-8082 - Tomcat-8083 - Tomcat-8084

------------------------------------------------------------------------

## ✅ Features Summary

-   Spring Boot 3.x
-   JWT + Spring Security
-   Swagger OpenAPI
-   Logback Logging
-   Multi-Tomcat Deployment
-   Auto-Scaling using Cron
-   Jenkins CI/CD
-   GitHub Integration
-   Health Monitoring

------------------------------------------------------------------------

✅ 11. SWAGGER ACCESS

Swagger UI:

http://localhost:8080/swagger-ui.html
http://localhost:8082/swagger-ui.html


------------------------------------------------------------------------

## 👨‍💻 Author

**Ashish Singh**\
Java Backend Developer (6+ Years Experience)
Spring Boot | Microservices | DevOps | Redis | Jenkins | Docker
------------------------------------------------------------------------

✅ *This project simulates real-world cloud-style auto-scaling
deployment on WSL/Linux.*  elastic= DaX4eDTCsrNvBKAnDUSU
