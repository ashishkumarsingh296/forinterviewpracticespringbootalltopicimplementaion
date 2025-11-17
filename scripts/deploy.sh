#!/bin/bash
# Auto-scale Spring Boot app in WSL based on CPU

MAX_INSTANCES=5
MIN_INSTANCES=1
CPU_THRESHOLD=70

current_replicas=$(docker ps -q --filter "name=myapp" | wc -l)

cpu_usage=$(docker stats myapp --no-stream --format "{{.CPUPerc}}" | sed 's/%//g' | awk '{sum+=$1} END {print sum/NR}')

echo "Current replicas: $current_replicas, CPU Usage: $cpu_usage"

if (( $(echo "$cpu_usage > $CPU_THRESHOLD" | bc -l) )) && [ $current_replicas -lt $MAX_INSTANCES ]; then
    new_replicas=$((current_replicas+1))
    echo "Scaling up to $new_replicas instances..."
    docker-compose up -d --scale myapp=$new_replicas
    docker exec nginx nginx -s reload
elif (( $(echo "$cpu_usage < 30" | bc -l) )) && [ $current_replicas -gt $MIN_INSTANCES ]; then
    new_replicas=$((current_replicas-1))
    echo "Scaling down to $new_replicas instances..."
    docker-compose up -d --scale myapp=$new_replicas
    docker exec nginx nginx -s reload
else
    echo "No scaling action required"
fi
