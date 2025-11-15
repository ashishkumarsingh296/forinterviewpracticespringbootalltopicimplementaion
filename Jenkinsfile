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
                    copy /Y target\\${JAR_NAME} ${WIN_SHARE}\
                    copy /Y scripts\\deploy-wsl-multi.sh ${WIN_SHARE}\
                    dir ${WIN_SHARE}
                """
            }
        }

        stage('Fix Line Endings in WSL') {
            steps {
                bat 'wsl dos2unix /mnt/c/springboot-app/deploy-wsl-multi.sh'
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
                bat """
wsl bash -c '
check_health() {
  local PORT=\$1
  echo -n "Checking port \${PORT}... "
  if curl -sSf "http://127.0.0.1:\${PORT}/actuator/health" > /dev/null 2>&1; then
      echo "OK"
  else
      echo "FAILED"
      return 1
  fi
}
check_health 8081 || echo "❌ 8081 FAILED"
check_health 8082 || echo "❌ 8082 FAILED"
'
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
