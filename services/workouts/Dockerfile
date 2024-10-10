FROM amazoncorretto:21-alpine

LABEL org.opencontainers.image.authors="mossers"
LABEL description="Image for the Workout microservice"


RUN mkdir /data
VOLUME /data
EXPOSE 9090

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=docker", "/app.jar"]