#!/usr/bin/env bash
set -euo pipefail

ARTIFACT="$1"
PROFILE="$2"
PORT1="$3"
PORT2="$4"

APP_DIR="/mnt/c/springboot-app"
WSL_APP1="/home/aashu/Kems/app1.jar"
WSL_APP2="/home/aashu/Kems/app2.jar"
BACK1="${WSL_APP1}.bak"
BACK2="${WSL_APP2}.bak"
LOG1="/home/aashu/Kems/app1.log"
LOG2="/home/aashu/Kems/app2.log"

cp "${APP_DIR}/${ARTIFACT}" "${WSL_APP1}"
cp "${APP_DIR}/${ARTIFACT}" "${WSL_APP2}"

[ -f "${WSL_APP1}" ] && cp -f "${WSL_APP1}" "${BACK1}" || true
[ -f "${WSL_APP2}" ] && cp -f "${WSL_APP2}" "${BACK2}" || true

pkill -f "${WSL_APP1}" || true
pkill -f "${WSL_APP2}" || true
sleep 1

nohup java -jar "${WSL_APP1}" --spring.profiles.active="${PROFILE}" --server.port="${PORT1}" > "${LOG1}" 2>&1 &
nohup java -jar "${WSL_APP2}" --spring.profiles.active="${PROFILE}" --server.port="${PORT2}" > "${LOG2}" 2>&1 &

sleep 6
curl -sSf "http://127.0.0.1:${PORT1}/actuator/health" | grep -q '"UP"'
curl -sSf "http://127.0.0.1:${PORT2}/actuator/health" | grep -q '"UP"'

sudo systemctl reload nginx || sudo nginx -s reload

echo "Both instances up"
