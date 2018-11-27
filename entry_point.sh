#!/bin/bash

./gradlew build

cd build/libs/

java -jar test-storage-0.0.1-SNAPSHOT.war
