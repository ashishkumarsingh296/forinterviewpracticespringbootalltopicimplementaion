// Final One with jar working fine
// pipeline {
//     agent any

//     environment {
//         WSL_DEPLOY="/home/aashudev/deploy"
//         ARTIFACT_NAME="spring-app.jar"
//         DEPLOY_SCRIPT="/home/aashudev/deploy/jenkins_scripts/deploy_WSL_PROD.sh"
//     }

//     stages {
//         stage('Build JAR on Jenkins') {
//             steps {
//                 bat 'mvnw clean package -DskipTests'
//             }
//         }

//         stage('Copy JAR to WSL') {
//             steps {
//                 bat """
//                 wsl cp /mnt/c/ProgramData/Jenkins/.jenkins/workspace/InterviewAllVersion/target/*.jar ${WSL_DEPLOY}/${ARTIFACT_NAME}
//                 """
//             }
//         }

//         stage('Deploy Application') {
//             steps {
//                 // bat "wsl bash ${DEPLOY_SCRIPT}"
//                         bat 'wsl bash /home/aashudev/deploy/jenkins_scripts/deploy_WSL_PROD.sh'

//             }
//         }

//         stage('Check Logs') {
//             steps {
//                 bat "wsl tail -n 200 ${WSL_DEPLOY}/app.log || echo 'No logs found'"
//             }
//         }

//      stage('Health Check') {
//     steps {
//         bat 'wsl bash /home/aashudev/deploy/jenkins_scripts/health_check.sh'
//     }
// }

//     }

//     post {
//         success {
//             echo "WSL Deployment completed successfully!"
//         }
//         failure {
//             echo "Deployment failed! Check logs."
//         }
//     }
// }



// Final one with war

pipeline {
    agent any

    environment {
        TOMCAT_HOME="/home/aashudev/tomcat/apache-tomcat-10.1.49"
        WSL_DEPLOY="${TOMCAT_HOME}/webapps"
        ARTIFACT_NAME="spring-app.war"
        START_SCRIPT="${TOMCAT_HOME}/myappstartup.sh"
        STOP_SCRIPT="${TOMCAT_HOME}/myappstop.sh"
    }

    stages {

        stage('Build WAR') {
            steps {
                bat 'mvnw clean package -DskipTests'
            }
        }

        stage('Copy WAR to WSL') {
            steps {
                bat """
                wsl cp /mnt/c/ProgramData/Jenkins/.jenkins/workspace/InterviewAllVersion/target/*.war ${WSL_DEPLOY}/${ARTIFACT_NAME}
                """
            }
        }

        stage('Stop Tomcat') {
            steps {
                bat "wsl bash -c '${STOP_SCRIPT}'"
            }
        }

        stage('Start Tomcat') {
            steps {
                bat "wsl bash -c '${START_SCRIPT}'"
            }
        }

        stage('Tail Logs') {
            steps {
                bat "wsl tail -n 50 ${TOMCAT_HOME}/logs/myapp-demo.out || echo 'No logs found'"
            }
        }

        stage('Health Check') {

            
             steps {
            //     bat 'wsl bash /home/aashudev/deploy/jenkins_scripts/health_check.sh || echo "Health check failed"'
                  catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
            bat 'wsl bash /home/aashudev/deploy/jenkins_scripts/health_check.sh'
        }
            }
        }
    }

    post {
        success {
            echo "Deployment Successful ✔️"
        }
        failure {
            echo "❌ Deployment Failed - check logs above"
        }
    }
}
