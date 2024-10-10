FROM amazoncorretto:21-alpine

LABEL org.opencontainers.image.authors="mossers"
LABEL description="Image for the Service Registry (Eureka) infrastructure"
EXPOSE 8761

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Installing curl to support healthchek of running containers
RUN apk --no-cache add curl

ENTRYPOINT ["java","-jar","/app.jar"]