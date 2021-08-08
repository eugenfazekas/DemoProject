FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine

RUN apk --no-cache add curl

WORKDIR /workspace

ADD target/eurekaserver-latest.jar eurekaserver-latest.jar

EXPOSE 8761

ADD run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh


