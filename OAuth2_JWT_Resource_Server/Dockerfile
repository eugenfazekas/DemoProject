FROM openjdk:11-slim 

WORKDIR /workspace

ADD target/Resource_Server-1.0.jar Resource_Server-1.0.jar
ADD /src/main/resources/static/user/image/profile_placeholder.png /src/main/resources/static/user/image/profile_placeholder.png 

EXPOSE 4880
ENTRYPOINT ["java","-jar","/Resource_Server-1.0.jar"]

