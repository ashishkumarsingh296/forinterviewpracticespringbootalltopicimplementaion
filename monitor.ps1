# monitor.ps1
$serviceName = "interviewallversion-app"
$maxReplicas = 5
$minReplicas = 1
$scaleCooldown = 15  # seconds between scaling actions
$cpuThresholdUp = 70   # % to scale up
$cpuThresholdDown = 20 # % to scale down

function Get-AverageCPU {
    $stats = docker stats --no-stream --format "{{.Name}} {{.CPUPerc}}" |
        Where-Object { $_ -match $serviceName }

    $cpuValues = @()
    foreach ($line in $stats) {
        $cpuRaw = ($line -split ' ')[1] -replace '%',''
        $cpuValues += [double]::Parse($cpuRaw) / [Environment]::ProcessorCount
    }

    if ($cpuValues.Count -eq 0) { return 0 }
    return ($cpuValues | Measure-Object -Average).Average
}

while ($true) {
    $avgCPU = Get-AverageCPU
    $replicas = (docker ps --filter "name=$serviceName" -q).Count

    Write-Host "Average CPU: $([math]::Round($avgCPU,2))% | Current replicas: $replicas"

    if ($avgCPU -gt $cpuThresholdUp -and $replicas -lt $maxReplicas) {
        $newReplicas = $replicas + 1
        Write-Host "Scaling up to $newReplicas replicas"
        docker-compose up -d --scale $serviceName=$newReplicas
        Start-Sleep -Seconds $scaleCooldown
    }
    elseif ($avgCPU -lt $cpuThresholdDown -and $replicas -gt $minReplicas) {
        $newReplicas = $replicas - 1
        Write-Host "Scaling down to $newReplicas replicas"
        docker-compose up -d --scale $serviceName=$newReplicas
        Start-Sleep -Seconds $scaleCooldown
    }
    else {
        Start-Sleep -Seconds 5
    }
}
