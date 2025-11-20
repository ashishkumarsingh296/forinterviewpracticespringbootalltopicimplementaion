// pipeline {
//     agent any

//     environment {
//         WSL_PROJECT="/home/ashishdev/project"
//         DOCKER_IMAGE="myapp"
//     }

//     stages {

//         stage('Checkout Code') {
//             steps {
//                 git branch: 'main', url: 'https://github.com/ashishkumarsingh296/forinterviewpracticespringbootalltopicimplementaion.git'
//             }
//         }

//       stage('Copy to WSL Workspace') {
//     steps {
//         bat """
//         wsl cp -r /mnt/c/ProgramData/Jenkins/.jenkins/workspace/InterviewAllVersion/* $WSL_PROJECT/
//         """
//     }
// }


//         stage('Build JAR & Docker Image in WSL') {
//             steps {
//                 bat """
//                 wsl bash -c "cd $WSL_PROJECT && ./mvnw clean package -DskipTests"
//                 wsl bash -c "cd $WSL_PROJECT && docker-compose build --no-cache"
//                 """
//             }
//         }

//         stage('Deploy Application') {
//             steps {
//                 bat """
//                 wsl bash -c "cd $WSL_PROJECT && docker-compose up -d"
//                 """
//             }
//         }

//         stage('Auto-Scaling') {
//             steps {
//                 bat """
//                 wsl bash -c "cd $WSL_PROJECT/scripts && ./deploy.sh"
//                 """
//             }
//         }

//         stage('Health Check') {
//             steps {
//                 bat """
//                 wsl bash -c "curl -f http://localhost || echo 'App not reachable!'"
//                 """
//             }
//         }
//     }

//     post {
//         success {
//             echo "Deployment completed successfully!"
//         }
//         failure {
//             echo "Deployment failed. Check logs for details."
//             // Optional: configure SMTP if you want mail notifications
//         }
//     }
// }



pipeline {
    agent any

    environment {
        WSL_PROJECT="/home/aashudev/spring-app"
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/ashishkumarsingh296/forinterviewpracticespringbootalltopicimplementaion.git'
            }
        }
// cp "/mnt/e/javaallversion/forinterviewpracticespringbootalltopicimplementaion/target/myapp-3.5.7.jar" ./app.jar

        stage('Copy Files to WSL') {
            steps {
                bat """
                wsl mkdir -p $WSL_PROJECT
                wsl rsync -av /mnt/c/ProgramData/Jenkins/.jenkins/workspace/InterviewAllVersion/ $WSL_PROJECT/
                wsl chmod +x $WSL_PROJECT/mvnw
                """
            }
        }

        stage('Build JAR & Docker Image') {
            steps {
                bat """
                wsl bash -c "cd $WSL_PROJECT && ./mvnw clean package -DskipTests"
                """
            }
        }

//         stage('Deploy Application') {
//             steps {
//                 bat """
//                 wsl bash -c "cd $WSL_PROJECT && docker compose down"
//                 wsl bash -c "cd $WSL_PROJECT && docker compose up -d"
//                 """
//             }
//         }

        stage('Health Check') {
            steps {
                bat """
                wsl bash -c "curl -f http://localhost:8080/actuator/health || echo 'App not reachable!'"
                """
            }
        }
    }

    post {
        success {
            echo "Deployment completed successfully!"
        }
        failure {
            echo "Deployment failed. Check logs for details."
        }
    }
}

