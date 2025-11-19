#!/bin/bash

# Number of app replicas based on CPU usage (example)
MAX_REPLICAS=5
MIN_REPLICAS=1

# Fetch average CPU usage of existing containers
CPU_AVG=$(docker stats --no-stream --format "{{.CPUPerc}}" myapp | tr -d '%' | awk '{sum+=$1} END {print sum/NR}')

# Determine desired replicas
if (( $(echo "$CPU_AVG > 50" | bc -l) )); then
    REPLICAS=$(( $(docker ps -q --filter "name=myapp" | wc -l) + 1 ))
elif (( $(echo "$CPU_AVG < 20" | bc -l) )); then
    REPLICAS=$(( $(docker ps -q --filter "name=myapp" | wc -l) - 1 ))
else
    REPLICAS=$(docker ps -q --filter "name=myapp" | wc -l)
fi

# Clamp replicas
if [ $REPLICAS -gt $MAX_REPLICAS ]; then REPLICAS=$MAX_REPLICAS; fi
if [ $REPLICAS -lt $MIN_REPLICAS ]; then REPLICAS=$MIN_REPLICAS; fi

# Scale Docker Compose
docker-compose up -d --scale myapp=$REPLICAS

# Reload Nginx if using as load balancer
nginx -s reload
