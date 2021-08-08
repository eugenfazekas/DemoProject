FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine

RUN apk --no-cache add curl

ADD target/authentication_server-1.0.jar authentication_server-1.0.jar

EXPOSE 4800

ADD run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh



