FROM openjdk:11-slim 

WORKDIR /workspace

ADD target/resource_server-1.0.jar resource_server-1.0.jar
ADD /src/main/resources/static/user/image/profile_placeholder.png /src/main/resources/static/user/image/profile_placeholder.png 

EXPOSE 4880
ENTRYPOINT ["java","-jar","/resource_server-1.0.jar"]

