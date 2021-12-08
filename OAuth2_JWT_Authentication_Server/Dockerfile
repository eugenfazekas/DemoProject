FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine

RUN apk --no-cache add curl

ADD src/main/resources/cacerts opt/java/openjdk/lib/security/cacerts

WORKDIR /workspace

ADD target/authentication_server-1.0.jar authentication_server-1.0.jar
ADD src/main/resources/keystore/* src/main/resources/keystore/

EXPOSE 4800

ADD service_check.sh service_check.sh
RUN chmod +x service_check.sh

ADD run.sh run.sh
RUN chmod +x run.sh

CMD ./run.sh



