#!/bin/sh
response='noResponse'
statusOk1='{"status":"UP"}'
statusOk2='{"status":"UP","groups":["liveness","readiness"]}'
check=false
while [[ $check = false ]]
do
	response="$(curl -k https://gateway-server:5555/actuator/health)"

		echo Response from server http $response

		if [ $response = $statusOk1 ] || [ $response = $statusOk2 ] 
		then
			check=true
			echo Gateway-Server is online
			break
		else
			echo Gateway-Server is offline
		fi
		sleep 1
done
