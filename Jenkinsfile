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



// Final one with war working fine

// pipeline {
//     agent any

//     environment {
//         TOMCAT_HOME="/home/aashudev/tomcat/apache-tomcat-10.1.49"
//         WSL_DEPLOY="${TOMCAT_HOME}/webapps"
//         ARTIFACT_NAME="spring-app.war"
//         START_SCRIPT="${TOMCAT_HOME}/myappstartup.sh"
//         STOP_SCRIPT="${TOMCAT_HOME}/myappstop.sh"
//     }

//     stages {

//         stage('Build WAR') {
//             steps {
//                 bat 'mvnw clean package -DskipTests'
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
//                 bat "wsl bash -c '${STOP_SCRIPT}'"
//             }
//         }

//         stage('Start Tomcat') {
//             steps {
//                 bat "wsl bash -c '${START_SCRIPT}'"
//             }
//         }

//         stage('Tail Logs') {
//             steps {
//                 bat "wsl tail -n 50 ${TOMCAT_HOME}/logs/myapp-demo.out || echo 'No logs found'"
//             }
//         }

//         stage('Health Check') {

            
//              steps {
//             //     bat 'wsl bash /home/aashudev/deploy/jenkins_scripts/health_check.sh || echo "Health check failed"'
//                   catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
//             bat 'wsl bash /home/aashudev/deploy/jenkins_scripts/health_check.sh'
//         }
//             }
//         }
//     }

//     post {
//         success {
//             echo "Deployment Successful ✔️"
//         }
//         failure {
//             echo "❌ Deployment Failed - check logs above"
//         }
//     }
// }


// for mutiple server like dev and qa
pipeline {
    agent any

    parameters {
        choice(name: 'TARGET', choices: ['dev','qa'], description: 'Deploy target environment')
    }

    environment {
        # WSL script base
        WSL_BASE="/home/aashudev/tomcat/bin"
        # TOMCAT base per env (used inside WSL)
        TOMCAT_DEV="/home/aashudev/tomcat/apache-tomcat-10.1.49-dev"
        TOMCAT_QA="/home/aashudev/tomcat/apache-tomcat-10.1.49-qa"
        ARTIFACT_NAME="spring-app.war"
    }

    stages {

        stage('Build WAR') {
            steps {
                bat 'mvnw clean package -DskipTests'
            }
        }

        stage('Copy WAR to WSL') {
            steps {
                script {
                    def targetWebapps = (params.TARGET == 'dev') ? "${TOMCAT_DEV}/webapps" : "${TOMCAT_QA}/webapps"
                    bat """\
                      wsl cp /mnt/c/ProgramData/Jenkins/.jenkins/workspace/${env.JOB_NAME}/target/*.war ${targetWebapps}/${ARTIFACT_NAME}
                    """
                }
            }
        }

        stage('Stop Tomcat') {
            steps {
                bat "wsl bash -lc '${WSL_BASE}/myappstop.sh ${params.TARGET} || true'"
            }
        }

        stage('Start Tomcat') {
            steps {
                bat "wsl bash -lc '${WSL_BASE}/myappstart.sh ${params.TARGET}'"
            }
        }

        stage('Tail Logs') {
            steps {
                script {
                    def tomcatHome = (params.TARGET == 'dev') ? TOMCAT_DEV : TOMCAT_QA
                    bat "wsl tail -n 200 ${tomcatHome}/logs/catalina.out || echo NoLogs"
                }
            }
        }

        stage('Health Check') {
            steps {
                script {
                    def url = (params.TARGET == 'dev') ? 'http://localhost:8080/spring-app/actuator/health' : 'http://localhost:8081/spring-app/actuator/health'
                    bat "wsl bash -lc '/home/aashudev/deploy/jenkins_scripts/health_check.sh ${url} 6 5' || exit 1"
                }
            }
        }
    }

    post {
        failure {
            script {
                // collect last 2 logs into workspace for debugging
                def tomcatHome = (params.TARGET == 'dev') ? TOMCAT_DEV : TOMCAT_QA
                bat """\
                  wsl bash -lc "mkdir -p /home/aashudev/deploy/jenkins_logs || true"
                  wsl bash -lc "cp ${tomcatHome}/logs/catalina.out /home/aashudev/deploy/jenkins_logs/catalina.out_$(date +%Y%m%d_%H%M%S) || true"
                  wsl bash -lc "ls -l /home/aashudev/deploy/jenkins_logs || true"
                """
                // bring the logs back to Windows Jenkins workspace
                bat "wsl cp /home/aashudev/deploy/jenkins_logs/* /mnt/c/ProgramData/Jenkins/.jenkins/workspace/${env.JOB_NAME}/ || true"
                archiveArtifacts artifacts: '**/catalina.out_*', allowEmptyArchive: true
            }
            echo "❌ Deployment failed — logs archived"
        }
        success {
            echo "✅ Deployment successful (${params.TARGET})"
        }
    }
}

