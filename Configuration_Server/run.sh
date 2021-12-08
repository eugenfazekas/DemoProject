sh ./service_check.sh eureka-server:8761 
echo "Starting Configuration-Server"
java -Deureka.client.serviceUrl.defaultZone=http://eureka-server:8761/eureka/ -Dspring.application.name=config-server -jar configserver-latest.jar