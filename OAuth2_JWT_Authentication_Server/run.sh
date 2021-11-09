sh ./service_check.sh configserver:8888 
echo "Starting Authentication-Service"
java  -Dspring.profiles.active=default -Dspring.cloud.config.uri=http://configserver:8888 -Dspring.application.name=authentication-service -jar authentication_server-1.0.jar