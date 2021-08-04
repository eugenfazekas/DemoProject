FROM openjdk:11-slim as build

WORKDIR /workspace
ADD target/gatewayserver-latest.jar gatewayserver-latest.jar
EXPOSE 5555
ENTRYPOINT ["java","-jar","gatewayserver-latest.jar"]
