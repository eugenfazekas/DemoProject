sh ./service_check.sh configuration-server:8888 
echo "Starting Gateway-server"
java  -Dspring.profiles.active=default -Dspring.cloud.config.uri=http://configuration-server:8888 -Dspring.application.name=gateway-server -Dreactor.netty.http.server.accessLogEnabled=true -jar gatewayserver-latest.jar 