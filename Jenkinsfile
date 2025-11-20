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
        // WSL paths
        WSL_PROJECT="/home/aashudev/spring-app"
        WSL_PROJECT_RESTART="/home/aashudev/deploy"
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/ashishkumarsingh296/forinterviewpracticespringbootalltopicimplementaion.git'
            }
        }

        stage('Copy Code to WSL') {
            steps {
                bat """
                wsl mkdir -p $WSL_PROJECT
                wsl rsync -av /mnt/c/ProgramData/Jenkins/.jenkins/workspace/InterviewAllVersion/ $WSL_PROJECT/
                wsl chmod +x $WSL_PROJECT/mvnw
                """
            }
        }

        stage('Build JAR') {
            steps {
                bat """
                wsl bash -c "cd $WSL_PROJECT && ./mvnw clean package -DskipTests"
                """
            }
        }

        stage('Copy JAR to Deploy Folder') {
            steps {
                bat """
                wsl bash -c "cp $WSL_PROJECT/target/*.jar $WSL_PROJECT_RESTART/"
                """
            }
        }

        stage('Deploy Application in WSL') {
            steps {
                bat """
                wsl bash -c "cd $WSL_PROJECT_RESTART && ./restart.sh wsl"
                """
            }
        }

        stage('Check Logs') {
            steps {
                bat """
                wsl bash -c "ls -l $WSL_PROJECT_RESTART && tail -n 200 $WSL_PROJECT_RESTART/app.log"
                """
            }
        }

        stage('Health Check') {
            steps {
                bat """
                wsl bash -c "curl -f http://localhost:8080/actuator/health || echo 'Application not reachable!'"
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
