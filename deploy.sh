#!/bin/bash

mvn package
cp target/bootstrapx-0.0.1-SNAPSHOT.war /opt/resin/webapps/bootstrapx.war
rm -rf target/*
