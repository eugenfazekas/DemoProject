FROM jenkins/jenkins:lts-jdk11
COPY executors.groovy  /usr/share/jenkins/ref/init.groovy.d/executors.groovy

RUN /usr/local/bin/install-plugins.sh docker-plugin


#COPY plugins.txt /usr/share/jenkins/plugins.txt
#RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/plugins.txt