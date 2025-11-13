// pipeline {
//     agent any
//     stages {
//         stage('Checkout') {
//             steps {
//                 git branch: 'main', url: 'https://github.com/ashishkumarsingh296/forinterviewpracticespringbootalltopicimplementaion.git'
//             }
//         }

//         stage('Build') {
//             steps {
//                 // For Windows Jenkins
//                 bat 'mvn clean package -DskipTests'
//             }
//         }

//         stage('Deploy to WSL') {
//             steps {
//                 echo 'Deploying to WSL...'
//                 // Optional: WSL-specific deployment command (example below)

//                 bat 'if not exist C:\\springboot-app mkdir C:\\springbootallversion-app'
//                 bat 'copy target\\forinterviewpracticespringbootalltopicimplementaion-0.0.1-SNAPSHOT.jar C:\\springbootallversion-app\\'
//             }
//         }
//     }

//     post {
//         failure {
//             echo 'Deployment failed.'
//         }
//     }
// }


pipeline {
    agent any

    stages {

        stage('Build') {
            steps {
                echo 'Building Spring Boot JAR...'
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Deploy on Windows') {
            steps {
                echo 'Deploying JAR to C:\\springboot-app...'
                bat '''
                if not exist C:\\springboot-app mkdir C:\\springboot-app
                copy target\\forinterviewpracticespringbootalltopicimplementaion-0.0.1-SNAPSHOT.jar C:\\springboot-app\\
                echo Killing any process on port 8080...
                for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8080') do taskkill /PID %%a /F
                echo Starting app on port 8080...
                start cmd /c "cd C:\\springboot-app && java -jar forinterviewpracticespringbootalltopicimplementaion-0.0.1-SNAPSHOT.jar"
                '''
            }
        }

        stage('Deploy on WSL') {
            steps {
                echo 'Deploying and restarting apps in WSL on ports 8081 and 8082...'
                bat '''
                REM Copy the JAR into WSL shared folder (assuming /mnt/c/springboot-app exists)
                if not exist C:\\springboot-app mkdir C:\\springboot-app
                copy target\\forinterviewpracticespringbootalltopicimplementaion-0.0.1-SNAPSHOT.jar C:\\springboot-app\\

                REM Restart Spring Boot on port 8081
                wsl bash -c "sudo fuser -k 8081/tcp || true"
                wsl bash -c "nohup java -jar /mnt/c/springboot-app/forinterviewpracticespringbootalltopicimplementaion-0.0.1-SNAPSHOT.jar --spring.profiles.active=wsl --server.port=8081 > /mnt/c/springboot-app/log-8081.log 2>&1 &"

                REM Restart Spring Boot on port 8082
                wsl bash -c "sudo fuser -k 8082/tcp || true"
                wsl bash -c "nohup java -jar /mnt/c/springboot-app/forinterviewpracticespringbootalltopicimplementaion-0.0.1-SNAPSHOT.jar --spring.profiles.active=wsl --server.port=8082 > /mnt/c/springboot-app/log-8082.log 2>&1 &"
                '''
            }
        }
    }

    post {
        success {
            echo '✅ Deployment successful on Windows (8080) and WSL (8081, 8082).'
        }
        failure {
            echo '❌ Deployment failed.'
        }
    }
}

