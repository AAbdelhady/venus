#!/usr/bin/env bash
mvn clean install
docker build -t aabdelhady/venus-backend .
docker push aabdelhady/venus-backend