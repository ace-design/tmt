FROM amazoncorretto:21-alpine

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

RUN apk --no-cache add curl

ENTRYPOINT ["java","-jar","/app.jar"]