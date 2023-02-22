#!/bin/bash
#
# Helper script to run Jakarta EE Jumpstart project

function deploy() {
  mvn clean package -DskipTests
  docker-compose up -d
}

function shutdown() {
  docker-compose stop
  mvn clean
}

function clean() {
  docker-compose down
  mvn clean
}

COMMAND="${1:-help}"

if [ "$COMMAND" = "deploy" ]; then
  deploy
fi

if [ "$COMMAND" = "stop" ]; then
  shutdown
fi

if [ "$COMMAND" = "clean" ]; then
  clean
fi

if [ "$COMMAND" = "help" ]; then
  echo ""
  echo "Usage:"
  echo "  jumpstartl.sh <command>"
  echo "Commands:"
  echo "  deploy       : Build and deploy the application in Payara container"
  echo "  stop         : Shutdown the application"
  echo "  clean     : Shutdown and remove containers"
  echo ""
fi
