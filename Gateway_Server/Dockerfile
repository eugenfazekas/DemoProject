FROM openjdk:11-slim as build

WORKDIR /workspace
ADD target/*.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","app.jar"]
