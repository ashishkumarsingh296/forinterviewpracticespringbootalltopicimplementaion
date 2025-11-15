pipeline {
    agent any

    environment {
        APP_NAME = "forinterviewpracticespringbootalltopicimplementaion-0.0.1-SNAPSHOT.jar"
        WINDOWS_DEPLOY_DIR = "C:\\springboot-app"
        WSL_SCRIPT = "/mnt/c/Jenkins/workspace/${JOB_NAME}/scripts/deploy_wsl.sh"
    }

    stages {

        stage('Checkout') {
            steps {
                echo "Pulling latest code..."
                git branch: 'main', url: 'https://github.com/your-repo/your-project.git'
            }
        }

        stage('Build JAR') {
            steps {
                echo "Building Spring Boot JAR"
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Copy JAR to Windows Shared Folder') {
            steps {
                echo "Copying JAR to C:\\springboot-app..."
                bat """
                    if not exist ${WINDOWS_DEPLOY_DIR} mkdir ${WINDOWS_DEPLOY_DIR}
                    copy target\\${APP_NAME} ${WINDOWS_DEPLOY_DIR}\\ /Y
                """
            }
        }

        stage('Deploy on WSL') {
            steps {
                echo "Triggering WSL Deploy Script"
                bat """
                    wsl chmod +x ${WSL_SCRIPT}
                    wsl ${WSL_SCRIPT} ${APP_NAME} wsl 8081 8082
                """
            }
        }

        stage('Post Deployment Check') {
            steps {
                echo "Checking health on both instances..."
                bat """
                    wsl curl -sSf http://127.0.0.1:8081/actuator/health
                    wsl curl -sSf http://127.0.0.1:8082/actuator/health
                """
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
