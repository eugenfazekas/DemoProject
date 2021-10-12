#!/bin/bash

servicePath=$1

serviceName=$2

response=noResponse

statusOk='{"status":"UP"}'

check=false

while [[ $check = false ]]
do
	response="$(curl http://servicePath/actuator/health)"
	
		if [[ $response = $statusOk ]]; then
		
			check=true
			echo Service "$serviceName" is online
			break
		else
			echo Service "$serviceName" is offline
		fi
		
		sleep 1			
	done		