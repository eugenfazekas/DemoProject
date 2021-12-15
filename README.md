### Demo Project Description Content:
	
	   1.Info
	   2.Application Description
       3.Develop
       4.Full Usage
	   5.Continuous Integration

##  1.Info:	

        This demo project implementing many features as possible,
	for a small microservice based architetured application.

		Program versions used for the project:

		JAVA 11.0.11,
		STS 4.11.1,
		Docker 20.10.8,
		NPM 6.14.13,
		Angular Cli: 9.1.15,
		Node: 14.17.1,
		Jenkins - windows 2.319.1
		Minkube 1.24.0
		Kubectl Client: 1.21.5
		Kubectl Server: 1.22.3

##  2.Application Description:

		Service-Discovery with Eureka-Server,
		Environment variables storage on Configuration-Server,
		Spring Cloud Gateway-Server implementation,
		OAuth2 JWT token based with asymmetric key Authentication-Service,
		OAuth2 JWT token based with asymmetric key Resource-Service,
		Authentication-Service implementations: PostgresDB, Junit5 tests, Integrations-tests, MFA,
		Resource-Service implementations: MongoDB,
		Aungular frontend description:
			- MFA (Multi-factor-authentication),
			- User registration,
			- Basic user, Admin User,
			- User account editing,
			- User details editing with image upload,
			- Article creation with image upload,
			- Articles multi filtering,

##  3.Develop

		To make it easyare the develop process,
		I created Z_Docker_Dev_Mode\docker-compose.yml to have all needed dependencys.

###		To Start Developer-mode

		1. Cretae environment system variables 'SPRING_DEMO_PROJECT_PROFILE: dev',
			'ENCRYPT_KEY: IMSYMMETRIC'
		2. Add to C:\Windows\System32\drivers\etc\hosts
			this line '127.0.0.1   example.com' to redirect example.com to localhost
		3. Add the self-signed certificate as trusted to a browser
			'https://support.kaspersky.com/CyberTrace/1.0/en-US/174127.htm'
		4. From cmd '~path\Demo_Proiect\Z_Docker_Dev_Mode\docker-compose up',
			(if you not start the first time please use first 'docker-compose down'.),
		5. Start STS.(If you start STS before env variable has been created,
			STS will not gonna see it.),
		6. From STS Run Eureka-Server,
		7. From STS Run Configuration-Server,
		8. From STS Run Gateway-Server,
		9. From STS Run Authentication-Service,
		10. From STS Run Resource-Service,
		11. From cmd '~path\Demo_Proiect\Angular_Ui\ng serve  --ssl true \
			--ssl-cert "/ssl/domain.crt"  --ssl-key "/ssl/domain.key"  --port 443 --disable-host-check'
		   (Node js and Angular CLI needed to install)
		12. Application will be accessible on https://example.com

##  4.Full Usage	

		To Start the application with full featureas please use

			cmd '~path\Demo_Proiect\docker-compose up'

			Application accessible on https://example.com

##	5.Continuous Integration

	I. - Linux Version

		To run continuous integration (Jenkinsfile_linux) needed 2 docker images to be build:

			Step 1. Build Jenkins network: cmd 'docker network create jenkins'

			Step 2. Build Jenkins image:
				cmd '~path\Demo_Project\
					Continuous_Integration\Jenkins_Docker_Image_Build\ docker build -t jenkins-master .'

			Step 3. Create DockerDind container - Dockerdind image needed to run with the command:
				cmd 'docker run --name dockerdind --detach --privileged --network jenkins \
					--network-alias docker --env DOCKER_TLS_CERTDIR=/certs \
					--volume jenkins-docker-certs:/certs/client \
					--volume jenkins-data:/var/jenkins_home --publish-all docker:dind'

			Step 4. - Create Jenkins container - Jenkins image needed to run with the command:
				cmd 'docker run --name jenkins  --detach  --network jenkins \
					--env DOCKER_HOST=tcp://docker:2376 --env DOCKER_CERT_PATH=/certs/client \
					--env DOCKER_TLS_VERIFY=1 --volume jenkins-data:/var/jenkins_home \
					--volume jenkins-docker-certs:/certs/client:ro --publish 8000:8080 \
					--publish 50000:50000 jenkins-master'

			Step 5. - Configure Jenkins on http://localhost:8000

				run cmd 'docker logs jenkins' and copy password to configure jenkins
				Create an Item type 'pipeline' ;
				and if you don't want to make any other configuration's on Jenkins Server
				just copy Jenkinsfile_linux content to script on the bottom of the test,
				and run Build!.

		The Demo Project Jenkinsfile_linux contains 2 stages and 1 post action:

			Stage 1. -  Downloading Git Repository,

			Stage 2. -  Building externale dependencys environment(PostGreSql DB, KafkaServer, RedisServer),
					 -  Starting services(Eureka-Server,Config-Server),
					 -  Checking availability of the services,
					 -  Runnig Unit tests and Integration tests on Authentication-Service

			Post     -  Stop containers
					 -  Remove containers
					 -  Remove network

	II. - Windows version

		Steps to run Jenkinsfile_windows:

			Step 1. Install Jenkins windows version

			Step 2. Install kuberntes and kubernetes cli plugin

			Step 3. Install minikube; and if needed: openssh - program, kubernetes CLI - program

			Step 4. minikube addons enable ingress

			Step 5. Create service account for jenkins with the followed

				cmd 'kubectl apply -f Deployment/kuberntes_service_account/serviceaccount.yaml'

			Step 6. Extract jenkins kuberntes account details with the followed command to get account

				secrets name cmd:'kubectl get serviceaccounts/jenkins -o yaml -n jenkins >  \

								Deployment/kuberntes_service_account/jenkins_account_details.yaml'

			Step 7. Extract token from jenkins_account_details:secret with the followed command

				cmd 'kubectl describe secret jenkins-token-mkkng  -n jenkins > \

					Deployment/kuberntes_service_account/token.txt'

			Step 8. Create Clusterrole for jenkins_account	with the followed command

				cmd 'kubectl create clusterrolebinding  jenkins-admin-binding --clusterrole=cluster-admin \

					--serviceaccount=jenkins:jenkins --namespace=jenkins'

			Step 9. Add credential to jenkins credentials password with type secret text

					ID: jenkins_kind
					secret-text: copy from Deployment/kuberntes_service_account/token.txt => token property

			Step 10. To create " 'Deployment' STEP  of the project" we have to use  "Pipeline Syntax"

							to generate step script.

				- select from Sample Step 'kubeconfig: Setup Kuberntes CLI'

				And complete the next fields required:

					- Kubernetes server endpoint: run cmd 'kubectl config view' "server:" property;

					- Certificate of certificate authority: You can find

							at C:\Users\Some kind Admin user\.minikube\ca.crt

							copy cert without Begin and End;

					- Credentials: select credentials jenkins_kind what we created at step 8.

					- change "some block//" with this line

					bat 'kubectl apply -k Deployment/kubernetes -n  jenkins'

				So you have to make the Deployment Step only in the pipeline,
				the rest of pipeline you can use from  Jenkinsfile_windows

			Step 11. To test 'Deployment' you must port-forward ingress-controller; to do that you need few cmd commands

				1.run cmd 'kubectl get po -n ingress-nginx'

						Result:
							-ingress-nginx-admission-create--1-xrtkg     0/1     Completed   0          104s
							-ingress-nginx-admission-patch--1-mptc9      0/1     Completed   1          104s
							-ingress-nginx-controller-5f66978484-68lbg   1/1     Running     0          105s

				2.run cmd 'kubectl port-forward ingress-nginx-controller-5f66978484-68lbg -n ingress-nginx 443:443'

			Now That's it the app is accessible on https://example.com

			The Demo Project Jenkinsfile_windows contains 3 stages and 1 post action:

			Stage 1. -  Downloading Git Repository,

			Stage 2. -  Building externale dependencys environment(PostGreSql DB, KafkaServer, RedisServer),
					 -  Starting services(Eureka-Server,Config-Server),
					 -  Checking availability of the services,
					 -  Runnig Unit tests and Integration tests on Authentication-Service

			Stage 3. - Deploying Application to minikube cluster

			Post     -  Stop containers
					 -  Remove containers
					 -  Remove network