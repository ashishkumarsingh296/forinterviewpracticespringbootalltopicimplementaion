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
// pipeline {
//     agent any

//     environment {
//         HOME_DIR="/home/aashudev/deploy"
//         TOMCAT_HOME="/home/aashudev/tomcat/apache-tomcat-10.1.49"
//         // WSL_DEPLOY="${TOMCAT_HOME}/webapps"
//         WSL_DEPLOY="${HOME_DIR}"
//         ARTIFACT_NAME="spring-app.war"
//         START_SCRIPT="${TOMCAT_HOME}/myappstartup.sh"
//         STOP_SCRIPT="${TOMCAT_HOME}/myappstop.sh"
//     }

//     stages {
//         stage('Build WAR on Jenkins') {
//             steps {
//                 bat 'mvnw clean package -DskipTests' // Ensure pom.xml has <packaging>war</packaging>
//             }
//         }

//         stage('Copy WAR to WSL') {
//             steps {
//                 bat """
//                 wsl cp /mnt/c/ProgramData/Jenkins/.jenkins/workspace/InterviewAllVersion/target/*.war ${WSL_DEPLOY}/${ARTIFACT_NAME}
//                 """
//             }
//         }

//         stage('Stop Tomcat') {
//             steps {
//                 bat "wsl bash ${STOP_SCRIPT}"
//             }
//         }

//         stage('Deploy Application') {
//             steps {
//                 echo "WAR copied to ${WSL_DEPLOY}/${ARTIFACT_NAME}"
//             }
//         }

//         stage('Start Tomcat') {
//             steps {
//                 bat "wsl bash ${START_SCRIPT}"
//             }
//         }

//         stage('Check Logs') {
//             steps {
//                 bat "wsl tail -n 200 ${TOMCAT_HOME}/logs/catalina.out || echo 'No logs found'"
//             }
//         }

//         stage('Health Check') {
//             steps {
//                 bat 'wsl bash /home/aashudev/deploy/jenkins_scripts/health_check.sh'
//             }
//         }
//     }

//     post {
//         success {
//             echo "WAR Deployment completed successfully!"
//         }
//         failure {
//             echo "Deployment failed! Check logs."
//         }
//     }
// }




pipeline {
    agent any

    environment {
        HOME_DIR="/home/aashudev/deploy"
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
        // Windows CMD me || true ko samajhne ke liye double quotes ke andar bash -c
        bat 'wsl bash -c "/home/aashudev/tomcat/apache-tomcat-10.1.49/myappstop.sh || true"'
    }
}

stage('Start Tomcat') {
    steps {
        bat 'wsl bash -c "/home/aashudev/tomcat/apache-tomcat-10.1.49/myappstartup.sh"'
    }
}


        // stage('Start Tomcat') {
        //     steps {
        //         bat "wsl bash ${START_SCRIPT}"
        //     }
        // }

        stage('Check Logs') {
            steps {
                bat "wsl tail -n 200 ${TOMCAT_HOME}/logs/catalina.out || echo 'No logs found'"
            }
        }

        stage('Health Check') {
            steps {
                 bat 'wsl bash /home/aashudev/deploy/jenkins_scripts/health_check.sh'
            }
        }
    }

    post {
        success {
            echo "Deployment Successful ✔️"
        }
        failure {
            echo "❌ Deployment Failed"
        }
    }
}
