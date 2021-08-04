FROM openjdk:11-slim as build

WORKDIR /workspace
ADD target/eurekaserver-latest.jar eurekaserver-latest.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","eurekaserver-latest.jar"]
