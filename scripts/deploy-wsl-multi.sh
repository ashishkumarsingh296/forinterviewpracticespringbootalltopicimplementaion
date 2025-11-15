#!/usr/bin/env bash
set -euo pipefail

ARTIFACT="$1"     # target jar filename (in /mnt/c/springboot-app)
PROFILE="$2"      # wsl
PORT1="$3"        # 8080
PORT2="$4"        # 8081

APP_DIR="/mnt/c/springboot-app"
WSL_APP1="/home/aashu/Kems/app1.jar"    # or keep under /mnt/c/springboot-app
WSL_APP2="/home/aashu/Kems/app2.jar"
BACK1="${WSL_APP1}.bak"
BACK2="${WSL_APP2}.bak"
LOG1="/home/aashu/Kems/app1.log"
LOG2="/home/aashu/Kems/app2.log"

# copy artifact from windows path to WSL path (if needed)
cp "${APP_DIR}/${ARTIFACT}" "${WSL_APP1}"
cp "${APP_DIR}/${ARTIFACT}" "${WSL_APP2}"

# backup
[ -f "${WSL_APP1}" ] && cp -f "${WSL_APP1}" "${BACK1}" || true
[ -f "${WSL_APP2}" ] && cp -f "${WSL_APP2}" "${BACK2}" || true

# stop old processes
pkill -f "${WSL_APP1}" || true
pkill -f "${WSL_APP2}" || true
sleep 1

# start new instances
nohup java -jar "${WSL_APP1}" --spring.profiles.active="${PROFILE}" --server.port="${PORT1}" > "${LOG1}" 2>&1 &
nohup java -jar "${WSL_APP2}" --spring.profiles.active="${PROFILE}" --server.port="${PORT2}" > "${LOG2}" 2>&1 &

# health checks
sleep 6
if ! curl -sSf "http://127.0.0.1:${PORT1}/actuator/health" | grep -q '"UP"'; then
  echo "Instance1 failed health"; exit 1
fi
if ! curl -sSf "http://127.0.0.1:${PORT2}/actuator/health" | grep -q '"UP"'; then
  echo "Instance2 failed health"; exit 1
fi

# reload nginx (in WSL okgit add --renormalize deploy-wsl-multi.sh
                         #git commit -m "Normalize LF line endings"
                         #git push)
sudo systemctl reload nginx || sudo nginx -s reload


echo "Both instances up"
