echo "Starting Configuration server"
java -Deureka.client.serviceUrl.defaultZone=http://eurekaserver:8761/eureka/ -Dspring.application.name=config-server -jar configserver-latest.jar