#!/bin/bash

export CERTIFICATE_CHAIN=$(cat .credentials/chain.crt)
export PRIVATE_KEY=$(cat .credentials/private.pem)
export PRIVATE_KEY_PASSWORD=$(cat .credentials/privatekey.pw)
export PUBLISH_TOKEN=$(cat .credentials/token)

./gradlew publishPlugin