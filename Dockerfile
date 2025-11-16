FROM tomcat:9-jdk17

# Remove default apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Build arguments for WAR files
ARG DEV_WAR
ARG QA_WAR

# Copy WARs (if provided)
COPY ${DEV_WAR:-not-exist}.war /usr/local/tomcat/webapps/dev.war
COPY ${QA_WAR:-not-exist}.war /usr/local/tomcat/webapps/qa.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
