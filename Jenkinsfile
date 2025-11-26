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


// For Mutiple server like dev and qa working fine

// pipeline {
//     agent any

//     parameters {
//         choice(
//             name: 'TARGET',
//             choices: ['dev','qa'],
//             description: 'Deploy target environment'
//         )
//     }

// environment {
//     WSL_BASE="/home/aashudev/tomcat/multiple-server-config/bin"
//     TOMCAT_DEV="/home/aashudev/tomcat/multiple-server-config/dev-server/apache-tomcat-10.1.49-dev"
//     TOMCAT_QA="/home/aashudev/tomcat/multiple-server-config/qa-server/apache-tomcat-10.1.49-qa"
//     ARTIFACT_NAME="my-new-app"
//     START_SCRIPT="${WSL_BASE}/myappstartup.sh"
//     STOP_SCRIPT="${WSL_BASE}/myappstop.sh"
// }

// stages {
//     stage('Build WAR') {
//         steps {
//             script {
//                 def profile = (params.TARGET == 'dev') ? 'wsl-dev' : 'wsl-qa'
//                 bat "mvnw clean package -P${profile} -DskipTests"
//             }
//         }
//     }


// stage('Copy WAR to WSL') {
//     steps {
//         script {
//             // Determine target Tomcat directory and WAR name
//             def targetWebapps = (params.TARGET == 'dev') ? "${TOMCAT_DEV}/webapps" : "${TOMCAT_QA}/webapps"
//             def warFileName = (params.TARGET == 'dev') ? "${ARTIFACT_NAME}-dev.war" : "${ARTIFACT_NAME}-qa.war"

//             // Copy WAR to WSL with dev/qa suffix
//             bat """wsl cp /mnt/c/ProgramData/Jenkins/.jenkins/workspace/${env.JOB_NAME}/target/*.war ${targetWebapps}/${warFileName}"""
//         }
//     }
// }



//      stage('Stop Tomcat') {
//             steps {
//                 echo "Stopping Tomcat for ${params.TARGET}"
//                 bat "wsl /home/aashudev/tomcat/multiple-server-config/bin/myappstop.sh ${params.TARGET} || true"
//             }
//         }

//         stage('Start Tomcat') {
//             steps {
//                 echo "Starting Tomcat for ${params.TARGET}"
//                 bat "wsl /home/aashudev/tomcat/multiple-server-config/bin/myappstartup.sh ${params.TARGET}"
//             }
//         }       



//     stage('Tail Logs') {
//         steps {
//             script {
//                 def tomcatHome = (params.TARGET == 'dev') ? TOMCAT_DEV : TOMCAT_QA
//                 bat """wsl bash -c "tail -n 200 ${tomcatHome}/logs/myapplog.out || echo NoLogs" """
//             }
//         }
//     }
// }

// post {
//     failure {
//         script {
//             def tomcatHome = (params.TARGET == 'dev') ? TOMCAT_DEV : TOMCAT_QA
//             bat """wsl bash -c "
//                 mkdir -p /home/aashudev/deploy/jenkins_logs &&
//                 cp ${tomcatHome}/logs/myapplog.out /home/aashudev/deploy/jenkins_logs/myapplog.out_\$(date +%Y%m%d_%H%M%S) &&
//                 ls -l /home/aashudev/deploy/jenkins_logs
//             " """
//             bat """wsl cp /home/aashudev/deploy/jenkins_logs/* /mnt/c/ProgramData/Jenkins/.jenkins/workspace/${env.JOB_NAME}/ || true"""
//             archiveArtifacts artifacts: '**/myapplog.out_*', allowEmptyArchive: true
//         }
//         echo "❌ Deployment failed — logs archived"
//     }

//     success {
//         echo "✅ Deployment successful (${params.TARGET})"
//     }
//    }
// }

//For Mulitiple Server with prod architecher

// pipeline {
//   agent any

//   parameters {
//     choice(name: 'BUILD', choices: ['dev','qa','prod'], description: 'Deploy target environment')
//   }

//   environment {
//     WSL_BASE = "/home/aashudev/tomcat/multiple-server-config/bin"
//     ARTIFACT_NAME = "my-new-app.war"

//     TOMCAT_DEV = "/home/aashudev/tomcat/multiple-server-config/dev-server/apache-tomcat-10.1.49-dev"
//     TOMCAT_QA  = "/home/aashudev/tomcat/multiple-server-config/qa-server/apache-tomcat-10.1.49-qa"

//     TOMCAT_PROD_1 = "/home/aashudev/tomcat/multiple-server-config/prod1-server/apache-tomcat-10.1.49-prod-1"
//     TOMCAT_PROD_2 = "/home/aashudev/tomcat/multiple-server-config/prod2-server/apache-tomcat-10.1.49-prod-2"
//     TOMCAT_PROD_3 = "/home/aashudev/tomcat/multiple-server-config/prod3-server/apache-tomcat-10.1.49-prod-3"

//     LOGS_DIR = "/home/aashudev/deploy/jenkins_logs"
//   }

//   stages {

//     stage('Build WAR') {
//       steps {
//         script {
//           def profile = (params.BUILD == 'qa') ? 'wsl-qa' :
//                         (params.BUILD == 'prod') ? 'prod' : 'wsl-dev'
//           bat "mvn clean package -P${profile} -DskipTests"
//         }
//       }
//     }

//     stage('Stop Application') {
//       steps {
//         script {
//           if (params.BUILD == 'dev') {
//             bat 'wsl -d Ubuntu -- bash -lc "/home/aashudev/tomcat/multiple-server-config/bin/myappstop.sh dev || true"'
//           } 
//           else if (params.BUILD == 'qa') {
//             bat 'wsl -d Ubuntu -- bash -lc "/home/aashudev/tomcat/multiple-server-config/bin/myappstop.sh qa || true"'
//           } 
//           else {
//             bat 'wsl -d Ubuntu -- bash -lc "/home/aashudev/tomcat/multiple-server-config/bin/myappstop.sh prod1 || true"'
//             bat 'wsl -d Ubuntu -- bash -lc "/home/aashudev/tomcat/multiple-server-config/bin/myappstop.sh prod2 || true"'
//             bat 'wsl -d Ubuntu -- bash -lc "/home/aashudev/tomcat/multiple-server-config/bin/myappstop.sh prod3 || true"'
//           }
//         }
//       }
//     }

//     stage('Copy WAR') {
//       steps {
//         script {
//           if (params.BUILD == 'dev') {
//             bat 'wsl -d Ubuntu -- bash -lc "cp /mnt/c/ProgramData/Jenkins/.jenkins/workspace/%JOB_NAME%/target/*.war /home/aashudev/tomcat/multiple-server-config/dev-server/apache-tomcat-10.1.49-dev/webapps/my-new-app.war"'
//           } 
//           else if (params.BUILD == 'qa') {
//             bat 'wsl -d Ubuntu -- bash -lc "cp /mnt/c/ProgramData/Jenkins/.jenkins/workspace/%JOB_NAME%/target/*.war /home/aashudev/tomcat/multiple-server-config/qa-server/apache-tomcat-10.1.49-qa/webapps/my-new-app.war"'
//           } 
//           else {
//             bat 'wsl -d Ubuntu -- bash -lc "cp /mnt/c/ProgramData/Jenkins/.jenkins/workspace/%JOB_NAME%/target/*.war /home/aashudev/tomcat/multiple-server-config/prod1-server/apache-tomcat-10.1.49-prod-1/webapps/my-new-app.war"'
//             bat 'wsl -d Ubuntu -- bash -lc "cp /mnt/c/ProgramData/Jenkins/.jenkins/workspace/%JOB_NAME%/target/*.war /home/aashudev/tomcat/multiple-server-config/prod2-server/apache-tomcat-10.1.49-prod-2/webapps/my-new-app.war"'
//             bat 'wsl -d Ubuntu -- bash -lc "cp /mnt/c/ProgramData/Jenkins/.jenkins/workspace/%JOB_NAME%/target/*.war /home/aashudev/tomcat/multiple-server-config/prod3-server/apache-tomcat-10.1.49-prod-3/webapps/my-new-app.war"'
//           }
//         }
//       }
//     }

//     stage('Start Application') {
//       steps {
//         script {
//           if (params.BUILD == 'dev') {
//             bat 'wsl -d Ubuntu -- bash -lc "/home/aashudev/tomcat/multiple-server-config/bin/myappstartup.sh dev"'
//           } 
//           else if (params.BUILD == 'qa') {
//             bat 'wsl -d Ubuntu -- bash -lc "/home/aashudev/tomcat/multiple-server-config/bin/myappstartup.sh qa"'
//           } 
//           else {
//             bat 'wsl -d Ubuntu -- bash -lc "/home/aashudev/tomcat/multiple-server-config/bin/myappstartup.sh prod1"'
//             bat 'wsl -d Ubuntu -- bash -lc "/home/aashudev/tomcat/multiple-server-config/bin/myappstartup.sh prod2"'
//             bat 'wsl -d Ubuntu -- bash -lc "/home/aashudev/tomcat/multiple-server-config/bin/myappstartup.sh prod3"'
//           }
//         }
//       }
//     }

//     stage('Tail Logs') {
//       when { expression { params.BUILD != 'prod' } }
//       steps {
//         script {
//           if (params.BUILD == 'dev') {
//             bat 'wsl -d Ubuntu -- bash -lc "tail -n 200 /home/aashudev/tomcat/multiple-server-config/dev-server/apache-tomcat-10.1.49-dev/logs/myapplog.out || echo NoLogs"'
//           } else {
//             bat 'wsl -d Ubuntu -- bash -lc "tail -n 200 /home/aashudev/tomcat/multiple-server-config/qa-server/apache-tomcat-10.1.49-qa/logs/myapplog.out || echo NoLogs"'
//           }
//         }
//       }
//     }
//   }

//   post {
//     success {
//       echo "✅ Deployment successful"
//     }

//     failure {
//       bat 'wsl -d Ubuntu -- bash -lc "mkdir -p /home/aashudev/deploy/jenkins_logs"'

//       bat 'wsl -d Ubuntu -- bash -lc "cp /home/aashudev/tomcat/multiple-server-config/*/logs/myapplog.out /home/aashudev/deploy/jenkins_logs/ || true"'

//       bat 'wsl -d Ubuntu -- bash -lc "cp -r /home/aashudev/deploy/jenkins_logs /mnt/c/ProgramData/Jenkins/.jenkins/workspace/%JOB_NAME%/ || true"'

//       archiveArtifacts artifacts: '**/jenkins_logs/**', allowEmptyArchive: true

//       echo "❌ Deployment failed — logs archived"
//     }
//   }
// }   


pipeline {
    agent any

    parameters {
        choice(
            name: 'ENV',
            choices: ['qa', 'dev', 'prod'],
            description: 'Select Environment'
        )
    }

    environment {
        WSL_BASH = 'C:\\Windows\\System32\\wsl.exe'
        START_SCRIPT = '/home/aashudev/tomcat/multiple-server-config/bin/myappstartup.sh'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                url: 'https://github.com/ashishkumarsingh296/forinterviewpracticespringbootalltopicimplementaion.git'
            }
        }

        stage('Start Tomcat') {
            steps {
                bat """
                "%WSL_BASH%" bash -c "chmod +x ${START_SCRIPT}"
                "%WSL_BASH%" bash -c "${START_SCRIPT} ${param.ENV}"
                """
            }
        }

        stage('Verify Running') {
            steps {
                bat """
                "%WSL_BASH%" bash -c "ps -ef | grep tomcat | grep -v grep"
                """
            }
        }
    }

    post {
        success {
            echo "✅ Deployment successful & Tomcat Started"
        }

        failure {
            echo "❌ Deployment failed"
        }
    }
}

