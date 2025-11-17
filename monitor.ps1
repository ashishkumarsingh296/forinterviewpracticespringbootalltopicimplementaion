# monitor.ps1 - Windows PowerShell autoscale

$service = "interviewallversion_app"  # adjust if needed
$maxCPU = 70
$minCPU = 20
$scaleStep = 1
$maxReplicas = 5
$minReplicas = 1

while ($true) {

    # Get CPU usage of matching containers
    $cpuUsage = docker stats --no-stream --format "{{.Name}} {{.CPUPerc}}" |
        Where-Object { $_ -match "$service" } |
        ForEach-Object { ($_ -split ' ')[1] -replace '%','' -as [double] }

    if ($cpuUsage.Count -eq 0) { $cpuUsage = @(0) }

    $avgCPU = ($cpuUsage | Measure-Object -Average).Average
    $currentReplicas = (docker ps --filter "name=$service" -q).Count

    Write-Host "Average CPU: $([math]::Round($avgCPU,2))% | Current replicas: $currentReplicas"

    if ($avgCPU -gt $maxCPU -and $currentReplicas -lt $maxReplicas) {
        $newReplicas = $currentReplicas + $scaleStep
        Write-Host "Scaling up to $newReplicas replicas"
        docker-compose -f "$env:WORKSPACE\docker-compose.yml" up -d --scale app=$newReplicas
    }
    elseif ($avgCPU -lt $minCPU -and $currentReplicas -gt $minReplicas) {
        $newReplicas = $currentReplicas - $scaleStep
        Write-Host "Scaling down to $newReplicas replicas"
        docker-compose -f "$env:WORKSPACE\docker-compose.yml" up -d --scale app=$newReplicas
    }

    Start-Sleep -Seconds 10
}
