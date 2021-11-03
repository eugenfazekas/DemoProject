sh ./service_check.sh configserver:8888 Eureka_Server	
echo "Starting Gateway server"
java  -Dspring.profiles.active=default -Dspring.cloud.config.uri=http://configserver:8888 -Dspring.application.name=gateway-server  -jar gatewayserver-latest.jar