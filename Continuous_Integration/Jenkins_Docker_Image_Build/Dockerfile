FROM jenkins/jenkins:lts
USER root
RUN apt-get update && apt-get install -y apt-transport-https \
       ca-certificates curl gnupg2 \
       software-properties-common
RUN curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add -
RUN apt-key fingerprint 0EBFCD88
RUN add-apt-repository \
       "deb [arch=amd64] https://download.docker.com/linux/debian \
       $(lsb_release -cs) stable"
RUN apt-get update \ 
	&& apt-get install -y maven \
	&& apt-get install -y git \
	&& apt-get install -y vim  
RUN apt-get update && apt-get install -y docker-ce-cli
USER jenkins
RUN jenkins-plugin-cli --plugins "blueocean:1.24.7 docker-plugin:1.2.3 docker-commons:1.17 docker-build-step:2.8 docker-slaves:1.0.7 docker-compose-build-step:1.0 docker-workflow:1.26"


# vim could be a security issue but i not deleting now.