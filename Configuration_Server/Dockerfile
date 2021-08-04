FROM openjdk:11-slim as build

WORKDIR /workspace
ADD target/configserver-latest.jar configserver-latest.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar","configserver-latest.jar"]
