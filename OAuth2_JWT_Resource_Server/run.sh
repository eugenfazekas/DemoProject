echo "Starting Configuration server"
java  -Dspring.profiles.active=default -Dspring.cloud.config.uri=http://configserver:8888 -Dspring.application.name=resource-service -jar resource_server-1.0.jar