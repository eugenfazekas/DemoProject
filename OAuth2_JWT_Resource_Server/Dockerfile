FROM openjdk:11-slim 

COPY target/resource_server-1.0.jar resource_server-1.0.jar
COPY /src/main/resources/static/user/image/profile_placeholder.png /src/main/resources/static/user/image/profile_placeholder.png 

EXPOSE 4880

ADD run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh



