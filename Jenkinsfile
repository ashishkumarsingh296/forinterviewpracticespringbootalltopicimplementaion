pipeline {
    agent any

    environment {
        JAR_NAME = 'forinterviewpracticespringbootalltopicimplementaion-0.0.1-SNAPSHOT.jar'
        WIN_SHARE = 'C:\\springboot-app'
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

                    dir ${WIN_SHARE}
                """
            }
        }

stage('Fix Line Endings in WSL') {
    steps {
        bat '''
        echo Converting script to Unix format inside WSL...
        wsl dos2unix /mnt/c/springboot-app/deploy-wsl-multi.sh
        '''
    }
}
    
        
        stage('Deploy on WSL') {
            steps {
                echo "Deploying on WSL using deploy-wsl-multi.sh..."

                bat """
                    echo Listing /mnt/c/springboot-app from WSL...
                    wsl ls -l /mnt/c/springboot-app

                    echo Making script executable...
                    wsl chmod +x /mnt/c/springboot-app/deploy-wsl-multi.sh

                    echo Running script in WSL...
                    wsl /mnt/c/springboot-app/deploy-wsl-multi.sh ${JAR_NAME} wsl 8081 8082
                """
            }
        }

        stage('Post Deployment Check (from WSL)') {
            steps {
                echo "Checking health of both instances from WSL..."

                bat """
                    echo Checking 8081...
                    wsl curl -sSf http://127.0.0.1:8081/actuator/health || echo FAILED_8081

                    echo Checking 8082...
                    wsl curl -sSf http://127.0.0.1:8082/actuator/health || echo FAILED_8082
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
