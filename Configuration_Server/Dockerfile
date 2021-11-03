FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine

RUN apk --no-cache add curl

WORKDIR /workspace

ADD target/configserver-latest.jar configserver-latest.jar

EXPOSE 8888

ADD service_check.sh service_check.sh
RUN chmod +x service_check.sh

ADD run.sh run.sh
RUN chmod +x run.sh

CMD ./run.sh


