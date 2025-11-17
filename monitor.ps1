# monitor.ps1
$maxIterations = 20          # Number of monitoring cycles
$checkInterval = 5           # Seconds between checks
$cpuThresholdUp = 70         # CPU % to scale up
$cpuThresholdDown = 10       # CPU % to scale down
$replicas = 1                # Starting replicas

for ($i=0; $i -lt $maxIterations; $i++) {
    # Simulate reading average CPU from Docker container
    # Replace this with your actual CPU fetch command
    $cpu = Get-Random -Minimum 0 -Maximum 100

    Write-Output "Average CPU: $cpu% | Current replicas: $replicas"

    if ($cpu -gt $cpuThresholdUp) {
        $replicas++
        Write-Output "Scaling up to $replicas replicas"
        docker-compose up -d --scale app=$replicas
    } elseif ($cpu -lt $cpuThresholdDown -and $replicas -gt 1) {
        $replicas--
        Write-Output "Scaling down to $replicas replicas"
        docker-compose up -d --scale app=$replicas
    }

    Start-Sleep -Seconds $checkInterval
}

Write-Output "Monitoring complete. Final replicas: $replicas"
