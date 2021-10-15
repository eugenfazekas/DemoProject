pipeline {
    agent any 
    
    environment {
        ENCRYPT_KEY = 'IMSYMMETRIC'
        SPRING_DEMO_PROJECT_PROFILE = 'test'
    }

    stages {
        
        stage('Checkout') {
            steps {
                git url: 'https://github.com/eugenfazekas/DemoProject.git'
            }
        }
        
        stage('Building Test Environment and run Tests') {
            
            steps {
                
                sh 'docker network create jenkinsTest'
                
                sh 'docker run --detach --name zookeeper --network host  wurstmeister/zookeeper:latest'
                 
                sh 'docker run --detach --name postgres -p 5432:5432  --env POSTGRES_USER=postgres  --env POSTGRES_PASSWORD=e21Wz44 --env POSTGRES_DB=db_users postgres:latest'

                sh 'docker run --detach --name mongodb -p 27017:27017 --env MONGO_INITDB_USER=John_Woo --env MONGO_INITDB_DATABASE=db_nx --env MONGO_INITDB_PWD="2hJ$lDS#nA&8" mongo:latest'
                
                sh 'docker run --detach --name zipkin -p 9411:9411 openzipkin/zipkin:latest'
                
                sh 'docker run --detach --name kafkaserver --network host  --env KAFKA_ADVERTISED_HOST_NAME=dockerdind --env KAFKA_ADVERTISED_PORT=9092 --env KAFKA_ZOOKEEPER_CONNECT=0.0.0.0:2181 wurstmeister/kafka:latest'
                
                sh 'docker run --detach --name redisserver -p 6379:6379 redis:alpine'
                
                sh 'chmod 777 Continuous_Integration/start_service.sh'
                
                sh 'chmod 777 Continuous_Integration/service_check.sh'
      
                sh 'Continuous_Integration/start_service.sh Eureka_Server/pom.xml Eureka_Server'
                
                sh 'Continuous_Integration/service_check.sh localhost:8761 Eureka_Server'
                
                sh 'Continuous_Integration/start_service.sh Configuration_Server/pom.xml Configuration_Server'
                
                sh 'Continuous_Integration/service_check.sh localhost:8888 Configuration_Server'
                
                sh 'mvn test -f OAuth2_JWT_Authentication_Server/pom.xml'
            }    
        }       
    }
	
	post { 
           
	    always { 
			
			 sh 'docker stop $(docker ps -a -q)' 
			
			 sh 'docker container prune -f'   
			 
			 sh 'docker network rm jenkinsTest'
        }
    }
}