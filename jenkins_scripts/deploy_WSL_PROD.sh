#!/bin/bash
echo "Deploying application..."

# Stop old app
pkill -f "spring-app" || true

# Start new app
nohup java -jar spring-app.jar > app.log 2>&1 &

echo "Application restarted!"
