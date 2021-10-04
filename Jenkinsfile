pipeline {
	agent any
	stages {
		stage('Building and Testing Services') {
		
			steps {
				bat  'docker-compose -f Z_Docker_Dev_Mode/docker-compose.yml down --remove-orphans' 
			}
			steps {
				bat  'docker-compose -f Z_Docker_Dev_Mode/docker-compose.yml up' 
			}
			
			steps {
				bat  'mvn clean install -f Eureka_Server/pom.xml' 
			}
			
			steps {
				bat  'java -jar Eureka_Server/target/eurekaserver-latest.jar' 
			}
			
			steps {
				bat  'mvn clean install -f Configuration_Server/pom.xml' 
			}
			
			steps {
				bat  'java -jar Configuration_Server/target/configserver-latest.jar' 
			}

			steps {
				bat  'mvn clean install -f Gateway_Server/pom.xml' 
			}		
			
			steps {
				bat  'java -jar Gateway_Server/target/gatewayserver-latest.jar' 
			}																					

			steps {
				bat  'mvn clean install -f OAuth2_JWT_Authentication_Server/pom.xml -Dmaven.test.skip==false' 
			}		
			steps {
				bat  'java -jar OAuth2_JWT_Authentication_Server/target/authentication_server-1.0.jar'
			}	

			steps {
			    bat  'mvn clean install -f OAuth2_JWT_Resource_Server/pom.xml'
			}
			
			steps {
			    bat  'java -jar OAuth2_JWT_Resource_Server/target/resource_server-1.0.jar'
			}
			
		}
	}
}




				