#!/bin/bash

servicePath=$1

serviceName=$2

mvn -f $servicePath spring-boot:run &

echo Service with the name "$serviceName" started 