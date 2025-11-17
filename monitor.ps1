# monitor.ps1
$serviceName = "app"
$maxReplicas = 4
$minReplicas = 1
$cpuThresholdUp = 50   # scale up if average CPU > 50%
$cpuThresholdDown = 20 # scale down if average CPU < 20%

# Get current replicas
$currentReplicas = (docker ps --filter "name=$serviceName" --format "{{.Names}}" | Measure-Object).Count

# Simulate getting average CPU usage (replace with your real monitoring logic)
$averageCPU = (Get-Random -Minimum 0 -Maximum 100) # for demo, replace with actual CPU calculation

Write-Host "Average CPU: $averageCPU% | Current replicas: $currentReplicas"

# Scaling logic
if ($averageCPU -gt $cpuThresholdUp -and $currentReplicas -lt $maxReplicas) {
    $newReplicas = $currentReplicas + 1
    if ($newReplicas -gt $maxReplicas) { $newReplicas = $maxReplicas }
    Write-Host "Scaling UP to $newReplicas replicas..."
    docker-compose up -d --scale $serviceName=$newReplicas
}
elseif ($averageCPU -lt $cpuThresholdDown -and $currentReplicas -gt $minReplicas) {
    $newReplicas = $currentReplicas - 1
    if ($newReplicas -lt $minReplicas) { $newReplicas = $minReplicas }
    Write-Host "Scaling DOWN to $newReplicas replicas..."
    docker-compose up -d --scale $serviceName=$newReplicas
}
else {
    Write-Host "No scaling needed."
}
