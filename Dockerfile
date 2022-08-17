FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY build/libs/*.jar messaging.jar
ENTRYPOINT ["java","-jar","/messaging.jar"]