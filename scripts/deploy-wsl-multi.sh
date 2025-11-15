#!/usr/bin/env bash
set -euo pipefail

ARTIFACT="$1"     # target jar filename (in /mnt/c/springboot-app)
PROFILE="$2"      # wsl
PORT1="$3"        # 8081
PORT2="$4"        # 8082

APP_DIR="/mnt/c/springboot-app"
WSL_APP1="/home/aashu/Kems/app1.jar"
WSL_APP2="/home/aashu/Kems/app2.jar"
BACK1="${WSL_APP1}.bak"
BACK2="${WSL_APP2}.bak"
LOG1="/home/aashu/Kems/app1.log"
LOG2="/home/aashu/Kems/app2.log"

echo ">>> Copying artifact from ${APP_DIR}/${ARTIFACT} to WSL paths..."
cp "${APP_DIR}/${ARTIFACT}" "${WSL_APP1}"
cp "${APP_DIR}/${ARTIFACT}" "${WSL_APP2}"

echo ">>> Backing up previous JARs (if any)..."
[ -f "${WSL_APP1}" ] && cp -f "${WSL_APP1}" "${BACK1}" || true
[ -f "${WSL_APP2}" ] && cp -f "${WSL_APP2}" "${BACK2}" || true

echo ">>> Stopping old Java processes by PORT..."
fuser -k ${PORT1}/tcp || true
fuser -k ${PORT2}/tcp || true
sleep 2


echo ">>> Starting new instances..."
nohup java -jar "${WSL_APP1}" --spring.profiles.active="${PROFILE}" --server.port="${PORT1}" > "${LOG1}" 2>&1 &
nohup java -jar "${WSL_APP2}" --spring.profiles.active="${PROFILE}" --server.port="${PORT2}" > "${LOG2}" 2>&1 &

echo ">>> Checking Java processes..."
ps aux | grep java

############################
# 🔥 FAST HEALTH CHECK
############################
check_port() {
  local PORT=$1
  local LOG_FILE=$2
  local MAX_WAIT=40
  local i=1

  echo ">>> Health check for port ${PORT}..."

  while [ $i -le $MAX_WAIT ]; do
    
    # Port open?
    if nc -z localhost "${PORT}" 2>/dev/null; then
      echo "✔ Port ${PORT} is UP after ${i} seconds"
      return 0
    fi

    echo "Waiting for port ${PORT}... (${i}/${MAX_WAIT})"
    tail -n 5 "${LOG_FILE}" 2>/dev/null || true

    sleep 1
    i=$((i+1))
  done

  echo "❌ ERROR: Port ${PORT} did not start within ${MAX_WAIT} seconds"
  exit 1
}

check_port "${PORT1}" "${LOG1}"
check_port "${PORT2}" "${LOG2}"

############################

echo ">>> Reloading nginx..."
sudo systemctl reload nginx || sudo nginx -s reload

echo ">>> ✅ Both instances are UP & Nginx reloaded"
