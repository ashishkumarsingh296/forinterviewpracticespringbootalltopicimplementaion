#!/bin/bash
THRESHOLD=50
MAX_REPLICAS=5
MIN_REPLICAS=1

current_replicas=$(docker ps --filter "name=interviewallversion-app" -q | wc -l)

cpu_avg=$(docker stats --no-stream --format "{{.Name}} {{.CPUPerc}}" | grep app | awk '{gsub("%","",$2); sum+=$2} END {print sum/NR}')

echo "Average CPU: $cpu_avg%"
echo "Current replicas: $current_replicas"

if (( $(echo "$cpu_avg > $THRESHOLD" | bc -l) )) && [ $current_replicas -lt $MAX_REPLICAS ]; then
    new_replicas=$((current_replicas + 1))
    echo "Scaling up to $new_replicas replicas..."
    docker-compose up -d --scale app=$new_replicas
    docker exec nginx nginx -s reload
elif (( $(echo "$cpu_avg < $THRESHOLD" | bc -l) )) && [ $current_replicas -gt $MIN_REPLICAS ]; then
    new_replicas=$((current_replicas - 1))
    echo "Scaling down to $new_replicas replicas..."
    docker-compose up -d --scale app=$new_replicas
    docker exec nginx nginx -s reload
else
    echo "No scaling needed."
fi
