FROM openjdk:11-slim 

WORKDIR /workspace

ADD target/Authentication_Server-1.0.jar Authentication_Server-1.0.jar
EXPOSE 4800
ENTRYPOINT ["java","-jar","/Authentication_Server-1.0.jar"]

