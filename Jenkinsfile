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

pipeline {
  agent any

  options {
    disableConcurrentBuilds()
    timeout(time: 30, unit: 'MINUTES')
  }

  parameters {
    choice(name: 'BUILD', choices: ['dev','qa','prod'], description: 'Deploy target environment')
    booleanParam(name: 'SHRINK_WSL', defaultValue: false, description: 'Run WSL ext4.vhdx compact')
  }

  environment {
    WSL_BASE = "/home/aashudev/tomcat/multiple-server-config/bin"
    TOMCAT_DEV = "/home/aashudev/tomcat/multiple-server-config/dev-server/apache-tomcat-10.1.49-dev"
    TOMCAT_QA  = "/home/aashudev/tomcat/multiple-server-config/qa-server/apache-tomcat-10.1.49-qa"
    TOMCAT_PROD_1 = "/home/aashudev/tomcat/multiple-server-config/prod1-server/apache-tomcat-10.1.49-prod-1"
    TOMCAT_PROD_2 = "/home/aashudev/tomcat/multiple-server-config/prod2-server/apache-tomcat-10.1.49-prod-2"
    TOMCAT_PROD_3 = "/home/aashudev/tomcat/multiple-server-config/prod3-server/apache-tomcat-10.1.49-prod-3"

    ARTIFACT_NAME = "my-new-app"
    START_SCRIPT = "${WSL_BASE}/myappstartup.sh"
    STOP_SCRIPT  = "${WSL_BASE}/myappstop.sh"

    MAVEN_LOCAL = "C:\\\\.m2-jenkins-cache"
    BACKUP_DIR = "/home/aashudev/deploy/war_backups"
    LOGS_DIR   = "/home/aashudev/deploy/jenkins_logs"
  }

  stages {

    stage('Prepare') {
      steps {
        bat """
          wsl -- bash -c "mkdir -p ${BACKUP_DIR} ${LOGS_DIR}"
        """
      }
    }

    stage('Build WAR') {
      steps {
        script {
          def profile = (params.BUILD == 'prod') ? 'wsl-prod' :
                        (params.BUILD == 'qa')   ? 'wsl-qa'  : 'wsl-dev'

          bat """
            if exist mvnw (
              mvnw clean package -P${profile} -DskipTests -Dmaven.repo.local=${MAVEN_LOCAL}
            ) else (
              mvn clean package -P${profile} -DskipTests -Dmaven.repo.local=${MAVEN_LOCAL}
            )
          """
        }
      }
    }

    stage('Stop Tomcat') {
      steps {
        script {
          def envName = (params.BUILD == 'prod') ? "prod1" : params.BUILD
          bat "wsl -- bash -c \"${STOP_SCRIPT} ${envName} || true\""
        }
      }
    }

    stage('Clean & Backup WAR') {
      steps {
        script {
          def tomcatTarget =
            (params.BUILD == 'dev') ? TOMCAT_DEV :
            (params.BUILD == 'qa')  ? TOMCAT_QA  : TOMCAT_PROD_1

          bat '''
            wsl -- bash -lc '
              set -e

              if [ -f "%s/webapps/%s.war" ]; then
                mkdir -p %s
                cp -v "%s/webapps/%s.war" "%s/%s_$(date +%%Y%%m%%d_%%H%%M%%S).war"
              fi

              rm -rf "%s/work/"* || true
              rm -rf "%s/temp/"* || true
              rm -rf "%s/webapps/%s" || true
            '
          '''.sprintf(tomcatTarget, ARTIFACT_NAME, BACKUP_DIR,
                      tomcatTarget, ARTIFACT_NAME, BACKUP_DIR, ARTIFACT_NAME,
                      tomcatTarget, tomcatTarget, tomcatTarget, ARTIFACT_NAME)
        }
      }
    }

    stage('Copy WAR') {
      steps {
        script {
          def src = "/mnt/c/ProgramData/Jenkins/.jenkins/workspace/${env.JOB_NAME}/target/${ARTIFACT_NAME}.war"

          if (params.BUILD == 'dev') {
            bat "wsl -- bash -c 'cp -v ${src} ${TOMCAT_DEV}/webapps/${ARTIFACT_NAME}.war'"
          } else if (params.BUILD == 'qa') {
            bat "wsl -- bash -c 'cp -v ${src} ${TOMCAT_QA}/webapps/${ARTIFACT_NAME}.war'"
          } else {
            bat """
              wsl -- bash -c 'cp -v ${src} ${TOMCAT_PROD_1}/webapps/${ARTIFACT_NAME}.war'
              wsl -- bash -c 'cp -v ${src} ${TOMCAT_PROD_2}/webapps/${ARTIFACT_NAME}.war'
              wsl -- bash -c 'cp -v ${src} ${TOMCAT_PROD_3}/webapps/${ARTIFACT_NAME}.war'
            """
          }
        }
      }
    }

    stage('Start Tomcat') {
      steps {
        script {
          def envName = (params.BUILD == 'prod') ? "prod1" : params.BUILD
          bat "wsl -- bash -c '${START_SCRIPT} ${envName}'"
        }
      }
    }
  }
post {
 success {
  script {
    echo "✅ Deployment Successful for ${params.BUILD}"

    bat '''
      wsl -- bash -c "
        cp -v /home/aashudev/tomcat/multiple-server-config/prod1-server/apache-tomcat-10.1.49-prod-1/logs/myapplog.out \
              /home/aashudev/deploy/jenkins_logs/success_log_$(date +%Y%m%d_%H%M%S).log || true
      "
    '''
  }
}
   failure {
  script {
    echo "❌ Deployment FAILED for ${params.BUILD}"

    bat '''
      wsl -- bash -c "
        mkdir -p /home/aashudev/deploy/jenkins_logs
        cp -v /home/aashudev/tomcat/multiple-server-config/prod1-server/apache-tomcat-10.1.49-prod-1/logs/myapplog.out \
              /home/aashudev/deploy/jenkins_logs/failed_log_$(date +%Y%m%d_%H%M%S).log || true
      "
    '''
  }

  archiveArtifacts artifacts: 'failed_log_*.log', allowEmptyArchive: true
}


    always {
      script {
        cleanWs()
      }
    }
  }
}
