#!/bin/bash

##
# 使用release.sh, 将thememarket service替换onebox上的服务
##

ORIG_DIR=`pwd -P`
SCRIPT_DIR=`cd $(dirname $0); pwd -P`
cd $SCRIPT_DIR
mvn clean install

wwwprj="bootstrap"
echo "replace $wwprj war file"
scp target/bootstrapx-0.0.1-SNAPSHOT.war  root@oneboxhost:/opt/resin/webapps/$wwwprj.war

cd $ORIG_DIR
