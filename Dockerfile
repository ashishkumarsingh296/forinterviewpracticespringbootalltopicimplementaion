FROM tomcat:9-jdk17

RUN rm -rf /usr/local/tomcat/webapps/*

ARG DEV_WAR

COPY ${DEV_WAR} /usr/local/tomcat/webapps/app.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
