sh ./service_check.sh authentication-service:14800 
echo "Starting Resource-Service"
java  -Dspring.profiles.active=default -Dspring.cloud.config.uri=http://configuration-server:8888 -Dspring.application.name=resource-service -jar resource_server-1.0.jar