FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine

RUN apk --no-cache add curl

ADD src/main/resources/keystore/cacerts opt/java/openjdk/lib/security/cacerts

ADD target/gatewayserver-latest.jar gatewayserver-latest.jar
ADD src/main/resources/keystore/* src/main/resources/keystore/

EXPOSE 5555

EXPOSE 15555

ADD service_check.sh service_check.sh
RUN chmod +x service_check.sh

ADD run.sh run.sh
RUN chmod +x run.sh

CMD ./run.sh

