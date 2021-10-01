pipeline {
		    agent any
			stages {
				stage('Building Development Docker Environment') {
					steps {
						bat  'docker-compose down --remove-orphans -f ~/Z_Docker_Dev_Mode/docker-compose.yml' 
					}
					steps {
						bat  'docker-compose up  -f ~/Z_Docker_Dev_Mode/docker-compose.yml' 
					}
					stages {
						stage('Building Eureka-Server') {
							steps {
								bat  'mvn clean install -f Eureka_Server/pom.xml' 
							}
							steps {
								bat  'java -jar Eureka_Server/target/eurekaserver-latest.jar' 
							}
							stages {
								stage('Building Config-Server') {
									steps {
										bat  'mvn clean install -f Configuration_Server/pom.xml' 
									}
									steps {
										bat  'java -jar Configuration_Server/target/configserver-latest.jar' 
									}
									stages {	  
										stage('building Gateway-Server') {
											steps {
												bat  'mvn clean install -f Gateway_Server/pom.xml' 
											}			
											steps {
												bat  'java -jar Gateway_Server/target/gatewayserver-latest.jar' 
											}																					
										}				
									}
									stages {	  
										stage('uilding Authentication-Server') {
											steps {
												bat  'mvn clean install -f OAuth2_JWT_Authentication_Server/pom.xml -Dmaven.test.skip==false' 
											}		
											steps {
												bat  'java -jar OAuth2_JWT_Authentication_Server/target/authentication_server-1.0.jar'
											}	
											stages {
												stage('Building Resource-Server') {
													steps {
													  bat  'mvn clean install -f OAuth2_JWT_Resource_Server/pom.xml'
													}		
													steps {
													  bat  'java -jar OAuth2_JWT_Resource_Server/target/resource_server-1.0.jar'
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}




				