#!/usr/bin/env bash

mvn clean package
mvn exec:java -q -Dexec.args="./maps/map03.json"