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
		Node: 14.17.1
		
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
		2. From cmd '~path\Demo_Proiect\Z_Docker_Dev_Mode\docker-compose up',  
			(if you not start the first time please use first 'docker-compose down'.),
		3. Start STS.(If you start STS before env variable has been created, 
			STS will not gonna see it.),	
		4. From STS Run Eureka-Server,
		5. From STS Run Configuration-Server,
		6. From STS Run Gateway-Server,
		7. From STS Run Authentication-Service,
		8. From STS Run Resource-Service,
		9. From cmd '~path\Demo_Proiect\Angular_Ui\ ng serve'
		   (Node js and Angular CLI needed to install)
		
##  4.Full Usage	

		To Start the application with full featureas please use
		
			cmd '~path\Demo_Proiect\docker-compose up'
			
			Application accessible on http://localhost:4200
		
##	5.Continuous Integration

	To run continuous integration (Jenkinsfile) needed 2 docker images to be build:
	
		Step 1. Build Jenkins image:
			cmd '~path\Demo_Project\
				Continuous_Integration\Jenkins_Docker_Image_Builddocker\ build -t jenkins-master .'
	
		Step 2. Create DockerDind container - Dockerdind image needed to run with the command:
			cmd 'docker run --name dockerdind --detach \
				--privileged --network jenkins --network-alias docker \
				--env DOCKER_TLS_CERTDIR=/certs \
				--volume jenkins-docker-certs:/certs/client \
				--volume jenkins-data:/var/jenkins_home --publish-all docker:dind'
						
		Step 3. - Create Jenkins container - Jenkins image needed to run with the command:
			cmd 'docker run --name dockerdind --detach \ 
				--privileged --network jenkins --network-alias docker \ 
				--env DOCKER_TLS_CERTDIR=/certs --volume jenkins-docker-certs:/certs/client \
				--volume jenkins-data:/var/jenkins_home docker:dind'

		Step 4. - Configure Jenkins on http://localhost:8000
		
			Create an Item type 'pipeline' ;
			and if you don't want to make any other configuration's on Jenkins Server
			just copy Jenkinsfile content to script on the bottom of the test,
			and run Build!.
	
	The Demo Project Jenkinsfile contains 2 stages and 1 post action: 
	
		Stage 1. -  Downloading Git Repository,
		
		Stage 2. -  Building externale dependencys environment(PostGreSql DB, KafkaServer, RedisServer),
				 -  Starting services(Eureka-Server,Config-Server),
				 -  Checking availability of the services,
				 -  Runnig Unit tests and Integration tests on Authentication-Service
	
		Post     -  Stop containers
				 -  Remove containers
				 -  Remove network	
													
		
		