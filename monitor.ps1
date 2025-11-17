# # Number of replicas to maintain
# $replicas = 1
# $maxRequestsPerReplica = 20
# $minRequestsPerReplica = 5
# $maxReplicas = 5

# # Placeholder: Replace with actual logic to get requests per replica
# # For example, parse Nginx access logs or call app metrics endpoint
# $requestsPerReplica = 25  # Example value

# if ($requestsPerReplica -gt $maxRequestsPerReplica -and $replicas -lt $maxReplicas) {
#     $replicas += 1
#     Write-Host "Scaling UP: New replicas = $replicas"
#     docker-compose up -d --scale app=$replicas
# } elseif ($requestsPerReplica -lt $minRequestsPerReplica -and $replicas -gt 1) {
#     $replicas -= 1
#     Write-Host "Scaling DOWN: New replicas = $replicas"
#     docker-compose up -d --scale app=$replicas
# } else {
#     Write-Host "No scaling needed. Current replicas = $replicas"
# }



$nginxContainer = "interviewallversion2-nginx-1"   # <- FIX THIS

$replicas = 1
$maxRequestsPerReplica = 20
$minRequestsPerReplica = 5
$maxReplicas = 5

while ($true) {
    $time = (Get-Date).AddSeconds(-10).ToString("dd/MMM/yyyy:HH:mm:ss")
    $cmd = "awk '\$4 >= ""[$time"" {print}' /var/log/nginx/access.log | wc -l"

    $requests = docker exec $nginxContainer sh -c $cmd
    $requests = [int]$requests

    Write-Host "Requests in last 10 seconds: $requests"

    $requestsPerReplica = $requests / $replicas
    Write-Host "Requests per replica: $requestsPerReplica"

    if ($requestsPerReplica -gt $maxRequestsPerReplica -and $replicas -lt $maxReplicas) {
        $replicas += 1
        Write-Host "Scaling UP: New replicas = $replicas"
        docker-compose up -d --scale app=$replicas
    }
    elseif ($requestsPerReplica -lt $minRequestsPerReplica -and $replicas > 1) {
        $replicas -= 1
        Write-Host "Scaling DOWN: New replicas = $replicas"
        docker-compose up -d --scale app=$replicas
    }
    else {
        Write-Host "No scaling needed."
    }

    Start-Sleep -Seconds 10
}
