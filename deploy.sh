#!/bin/bash

mvn clean install
cp target/bootstrapx-0.0.1-SNAPSHOT.war /opt/resin/webapps/bootstrapx.war
