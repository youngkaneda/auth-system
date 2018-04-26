#!/bin/bash

cd auth-system
mvn clean package
cd ..
cd provider
mvn clean package
cd ..
cd client
mvn clean package
cd ..
docker-compose up
