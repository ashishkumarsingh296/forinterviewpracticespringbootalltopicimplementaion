pipeline {
    agent any

    environment {
        ARTIFACT = "forinterviewpracticespringbootalltopicimplementaion-0.0.1-SNAPSHOT.jar"
        // SLACK_HOOK = credentials('SLACK_WEBHOOK')   // Store Slack webhook in Jenkins Credentials
    }

    stages {

        stage('Checkout') {
            steps {
                echo "Pulling latest code from GitHub..."
                git branch: 'main', url: 'https://github.com/your-github-repo-url.git'
            }
        }

        stage('Build JAR') {
            steps {
                echo 'Building Spring Boot project...'
                bat "cd forinterviewallversion/forinterviewpracticespringbootalltopicimplementaion && mvn clean package -DskipTests"
            }
        }

        stage('Deploy to Windows (Optional)') {
            steps {
                echo 'Deploying JAR to Windows local folder...'
                bat '''
                if not exist C:\\springboot-app mkdir C:\\springboot-app
                copy forinterviewallversion\\forinterviewpracticespringbootalltopicimplementaion\\target\\%ARTIFACT% C:\\springboot-app\\ /Y
                echo Killing any process running on port 8080...
                for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8080') do taskkill /PID %%a /F
                echo Starting application on Windows port 8080...
                start cmd /c "cd C:\\springboot-app && java -jar %ARTIFACT%"
                '''
            }
        }

        stage('Deploy to WSL (Load Balancing)') {
            steps {
                echo 'Deploying JAR to WSL Instances (8080 & 8081)...'
                bat '''
                echo Copying artifact to WSL shared directory...
                copy forinterviewallversion\\forinterviewpracticespringbootalltopicimplementaion\\target\\%ARTIFACT% C:\\springboot-app\\ /Y

                wsl bash -lc "chmod +x /mnt/c/springboot-app/%ARTIFACT%"
                wsl bash -lc "mkdir -p /home/aashu/Kems"

                # Copy to WSL app directories
                wsl bash -lc "cp /mnt/c/springboot-app/%ARTIFACT% /home/aashu/Kems/app1.jar"
                wsl bash -lc "cp /mnt/c/springboot-app/%ARTIFACT% /home/aashu/Kems/app2.jar"

                # Kill old apps
                wsl bash -lc "pkill -f app1.jar || true"
                wsl bash -lc "pkill -f app2.jar || true"

                # Start new instances
                wsl bash -lc "nohup java -jar /home/aashu/Kems/app1.jar --server.port=8081 --spring.profiles.active=wsl > /home/aashu/Kems/app1.log 2>&1 &"
                wsl bash -lc "nohup java -jar /home/aashu/Kems/app2.jar --server.port=8082 --spring.profiles.active=wsl > /home/aashu/Kems/app2.log 2>&1 &"

                # Wait for startup
                timeout /t 7
                '''
            }
        }

        stage('Health Check') {
            steps {
                echo 'Checking health for both WSL instances...'

                script {
                    def health1 = bat(script: 'wsl curl -s http://localhost:8080/actuator/health', returnStdout: true)
                    def health2 = bat(script: 'wsl curl -s http://localhost:8081/actuator/health', returnStdout: true)

                    if (!health1.contains('"status":"UP"')) {
                        error("❌ Instance 1 (8081) is DOWN")
                    }
                    if (!health2.contains('"status":"UP"')) {
                        error("❌ Instance 2 (8082) is DOWN")
                    }
                }

                echo "Both instances are UP ✔️"
            }
        }

        stage('Reload Nginx') {
            steps {
                echo 'Reloading Nginx in WSL...'
                // Your WSL user MUST have passwordless sudo for nginx reload
                bat 'wsl sudo nginx -s reload'
            }
        }
    }

    post {
        success {
            echo '🎉 SUCCESS: Build + Deploy Completed!'

            mail to: 'ashishkk.sngh4@gmail.com',
                 subject: "SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "Deployment Successful.\nNginx Reloaded.\nInstances running on 8081 and 8082."

            sh """
            curl -X POST -H 'Content-type: application/json' \
                 --data '{"text":"✅ SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER} Deployed Successfully!"}' \
                 $SLACK_HOOK
            """
        }

        failure {
            echo '❌ DEPLOYMENT FAILED'

            mail to: 'ashishkk.sngh4@gmail.com',
                 subject: "FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "Deployment Failed. Check logs: ${env.BUILD_URL}console"

            sh """
            curl -X POST -H 'Content-type: application/json' \
                 --data '{"text":"❌ FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER} Deployment Failed!"}' \
                 $SLACK_HOOK
            """
        }
    }
}
