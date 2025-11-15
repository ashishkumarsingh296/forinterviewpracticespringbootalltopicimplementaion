pipeline {
    agent any

  parameters {
        string(name: 'PROFILE', defaultValue: 'wsl', description: 'Spring profile to use')
        string(name: 'PORT1', defaultValue: '8081', description: 'Port for instance 1')
        string(name: 'PORT2', defaultValue: '8082', description: 'Port for instance 2')
    }

    environment {
        JAR_NAME = 'forinterviewpracticespringbootalltopicimplementaion-0.0.1-SNAPSHOT.jar'
        WIN_SHARE = 'C:\\springboot-app'
        WSL_APP1 = '/home/aashu/Kems/app1.jar'
        WSL_APP2 = '/home/aashu/Kems/app2.jar'
        LOG_DIR = '/home/aashu/Kems/logs'
        BACKUP_DIR = '/home/aashu/Kems/backups'
        MAX_BACKUPS = 5
    }

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

       stage('Copy JAR & Script to Windows Shared Folder') {
    steps {
        echo "Copying JAR & deploy script to C:\\springboot-app..."

        bat """
if not exist ${WIN_SHARE} mkdir ${WIN_SHARE}

echo Copying JAR...
copy /Y target\\${JAR_NAME} ${WIN_SHARE}\\

echo Copying deploy-wsl-multi.sh...
copy /Y scripts\\deploy-wsl-multi.sh ${WIN_SHARE}\\

echo Listing files...
dir ${WIN_SHARE}
"""
    }
}


        stage('Fix Line Endings in WSL') {
            steps {
                bat 'wsl dos2unix /mnt/c/springboot-app/deploy-wsl-multi.sh'
            }
        }

          stage('Pre-Deployment Checks') {
            steps {
                echo "Checking disk space and ports before deployment..."
                bat """
                    wsl df -h
                    wsl lsof -i :%PORT1%
                    wsl lsof -i :%PORT2%
                """
            }
        }

        stage('Deploy on WSL') {
            steps {
                bat """
                    wsl chmod +x /mnt/c/springboot-app/deploy-wsl-multi.sh
                    wsl /mnt/c/springboot-app/deploy-wsl-multi.sh ${JAR_NAME} wsl 8081 8082
                """
            }
        }


    stage('Post Deployment Check (from WSL)') {
    steps {
        echo "Checking health for both instances (8081 & 8082)..."

        bat """
wsl curl -sSf http://127.0.0.1:8081/actuator/health && echo 8081 OK || echo 8081 FAILED
wsl curl -sSf http://127.0.0.1:8082/actuator/health && echo 8082 OK || echo 8082 FAILED
"""
    }
}
    }

    post {
        success {
            echo "🚀 Deployment Success: App running on 8081 & 8082 via Nginx Load Balancer"
        }
        failure {
            echo "❌ Deployment Failed – check stages (especially Deploy on WSL)."
        }
    }
}

