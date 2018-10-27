#!/bin/sh

git clone https://github.com/fvcastellanos/farm-control-test.git

echo "Prepare application"
docker-compose -f docker/docker-compose.yml up -d

echo "Running functional tests"
cd farm-control-test

mvn test
