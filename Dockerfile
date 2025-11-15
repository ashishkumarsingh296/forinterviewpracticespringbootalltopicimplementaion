# Use official Tomcat 9 image
FROM tomcat:9.0-jdk21

# Remove default webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR built by Maven
COPY target/forinterviewpracticespringbootalltopicimplementaion-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Expose Tomcat port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
