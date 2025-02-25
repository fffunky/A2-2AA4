#!/usr/bin/env bash

mvn -q clean package
mvn exec:java -q -Dexec.args="./maps/map03.json"