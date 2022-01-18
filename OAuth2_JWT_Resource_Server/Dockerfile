FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine

RUN apk --no-cache add curl

COPY target/resource_server-1.0.jar resource_server-1.0.jar
COPY /src/main/resources/static/user/image/profile_placeholder.png /src/main/resources/static/user/image/profile_placeholder.png 
COPY /src/main/resources/keystore/* /src/main/resources/keystore/
COPY /src/main/resources/keystore/cacerts /opt/java/openjdk/lib/security/cacerts

EXPOSE 4880

EXPOSE 14880

ADD service_check.sh service_check.sh
RUN chmod +x service_check.sh

ADD run.sh run.sh
RUN chmod +x run.sh

CMD ./run.sh



