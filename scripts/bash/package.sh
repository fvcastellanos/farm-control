#!/bin/sh

mvn clean package -DskipTests

docker build -t $REGISTRY_NAME/farm-control:test -f docker/Dockerfile .
docker images
echo "$REGISTRY_PWD" | docker login -u "$REGISTRY_USER" --password-stdin $REGISTRY_NAME
docker push $REGISTRY_NAME/farm-control:test
