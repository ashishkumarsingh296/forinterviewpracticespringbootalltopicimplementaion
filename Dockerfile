# Dockerfile
FROM tomcat:9.0-jdk21
COPY target/forinterviewpracticespringbootalltopicimplementaion-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
