#!/bin/bash
servicePath1=$1
servicePath2=$2
response='noResponse'
statusOk1='{"status":"UP"}'
statusOk2='{"status":"UP","groups":["liveness","readiness"]}'
check=false
while [[ $check = false ]]
do
	response1="$(curl http://$servicePath1/actuator/health)"
	response2="$(curl http://$servicePath2/actuator/health)"
		echo Response from server $response1
		echo Response from server $response2
		if [ $response1 = $statusOk1 ] || [ $response1 = $statusOk2 ] || [ $response2 = $statusOk1 ] || [ $response2 = $statusOk2 ]
		then
			check=true
			echo Eureka-Server is online
			break
		else
			echo Eureka-Server is offline
		fi
		sleep 1
done
