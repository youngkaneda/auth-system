#!/bin/bash

docker-compose down
docker rmi auth
docker rmi provider
docker rmi client
