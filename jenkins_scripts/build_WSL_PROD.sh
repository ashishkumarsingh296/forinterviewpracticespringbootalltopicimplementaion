#!/bin/bash
echo "Running WSL_PROD build..."
./mvnw clean package -DskipTests
echo "PROD build completed!"
