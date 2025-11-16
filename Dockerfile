FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy JAR from target folder
COPY target/*.jar app.jar

# Accept profile from environment
ENV SPRING_PROFILE=wsl

ENTRYPOINT ["sh", "-c", "java -jar app.jar --spring.profiles.active=${SPRING_PROFILE}"]
