FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/*.jar app.jar

# Accept Spring profile from environment variable
ENV SPRING_PROFILE=wsl

ENTRYPOINT ["sh", "-c", "java -jar app.jar --spring.profiles.active=${SPRING_PROFILE}"]