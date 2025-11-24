#!/bin/bash
echo "Running LOCAL build..."
./mvnw clean package -DskipTests
echo "LOCAL build completed!"
