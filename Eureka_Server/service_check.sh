#!/bin/bash
servicePath=$1
response='noResponse'
statusOk1='{"status":"UP"}'
statusOk2='{"status":"UP","groups":["liveness","readiness"]}'
check=false
while [[ $check = false ]]
do
	response="$(curl http://$servicePath/actuator/health)"
		echo Response from server $response
		if [ $response = $statusOk1 ] || [ $response = $statusOk2 ]
		then
			check=true
			echo Eureka-Server is online
			break
		else
			echo Eureka-Server is offline
		fi
		sleep 1
done
