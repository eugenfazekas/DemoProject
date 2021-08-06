FROM openjdk:11-slim 

WORKDIR /workspace

ADD target/authentication_server-1.0.jar authentication_server-1.0.jar
EXPOSE 4800
ENTRYPOINT ["java","-jar","/authentication_server-1.0.jar"]

