FROM jenkins/jenkins:2.303.2-jdk11

COPY executors.groovy  /usr/share/jenkins/ref/init.groovy.d/executors.groovy

USER root
	   
RUN apt-get update \
&& apt-get install -y maven \
&& apt-get install -y git \
&& apt-get install -y vim    

USER jenkins

RUN jenkins-plugin-cli --plugins "pipeline-model-definition github-branch-source:1.8 docker-workflow:1.26"