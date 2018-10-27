#!/bin/sh

docker-compose -f ../../docker/docker-compose.yml -d

git clone https://github.com/fvcastellanos/farm-control-test.git

cd farm-control-test

mvn test
