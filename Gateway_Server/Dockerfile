FROM openjdk:11-slim as build

WORKDIR /workspace

ADD target/gatewayserver-latest.jar gatewayserver-latest.jar

EXPOSE 5555

ADD run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh


