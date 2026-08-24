FROM amazoncorretto:17
#FROM --platform=linux/amd64 amazoncorretto:17
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
# Expose port 8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
