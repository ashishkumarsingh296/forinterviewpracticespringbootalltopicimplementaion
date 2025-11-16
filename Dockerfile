FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy JAR from target folder
COPY target/*.jar app.jar

# Accept profile from environment
ENV SPRING_PROFILES_ACTIVE=wsl
ENTRYPOINT ["java","-jar","app.jar"]

