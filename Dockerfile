FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
ENV TZ=Europe/Moscow
COPY target/culturescheduler-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar", "/app.jar"]