pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                echo "Pulling latest code..."
                git branch: 'main', url: 'https://github.com/ashishkumarsingh296/forinterviewpracticespringbootalltopicimplementaion.git'
            }
        }

        stage('Build JAR') {
            steps {
                echo "Building Spring Boot JAR..."
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Copy Files to Windows Shared Folder') {
            steps {
                echo "Copying JAR & Script to C:\\springboot-app..."

                bat '''
                    if not exist C:\\springboot-app mkdir C:\\springboot-app

                    REM Copy JAR
                    copy /Y target\\forinterviewpracticespringbootalltopicimplementaion-0.0.1-SNAPSHOT.jar C:\\springboot-app\\

                    REM Copy Script
                    copy /Y scripts\\deploy-wsl-multi.sh C:\\springboot-app\\
                '''
            }
        }

        stage('Deploy on WSL') {
            steps {
                echo "Running Deployment in WSL..."

                bat '''
                wsl chmod +x /mnt/c/springboot-app/deploy-wsl-multi.sh

                wsl /mnt/c/springboot-app/deploy-wsl-multi.sh \
                    forinterviewpracticespringbootalltopicimplementaion-0.0.1-SNAPSHOT.jar \
                    wsl \
                    8081 \
                    8082
                '''
            }
        }
    }

    post {
        success {
            echo "🚀 Deployment Success: App running on 8081 & 8082 via Nginx Load Balancer"
        }
        failure {
            echo "❌ Deployment Failed"
        }
    }
}
