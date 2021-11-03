sh ./service_check.sh configserver:8888 Eureka_Server	
echo "Starting Authentication Server"
java  -Dspring.profiles.active=default -Dspring.cloud.config.uri=http://configserver:8888 -Dspring.application.name=authentication-service -jar authentication_server-1.0.jar