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
    // Prevent concurrent builds of the same job (avoid race on same tomcat dirs)
    disableConcurrentBuilds()
    // Timeout as a safety (optional)
    timeout(time: 30, unit: 'MINUTES')
  }

  parameters {
    choice(name: 'BUILD', choices: ['dev','qa','prod'], description: 'Deploy target environment')
    booleanParam(name: 'SHRINK_WSL', defaultValue: false, description: 'Run WSL ext4.vhdx compact (requires admin & diskpart script)')
  }

  environment {
    // WSL tomcat base paths (adjust if needed)
    WSL_BASE = "/home/aashudev/tomcat/multiple-server-config/bin"
    TOMCAT_DEV = "/home/aashudev/tomcat/multiple-server-config/dev-server/apache-tomcat-10.1.49-dev"
    TOMCAT_QA  = "/home/aashudev/tomcat/multiple-server-config/qa-server/apache-tomcat-10.1.49-qa"
    TOMCAT_PROD_1 = "/home/aashudev/tomcat/multiple-server-config/prod1-server/apache-tomcat-10.1.49-prod-1"
    TOMCAT_PROD_2 = "/home/aashudev/tomcat/multiple-server-config/prod2-server/apache-tomcat-10.1.49-prod-2"
    TOMCAT_PROD_3 = "/home/aashudev/tomcat/multiple-server-config/prod3-server/apache-tomcat-10.1.49-prod-3"

    // artifact
    ARTIFACT_NAME = "my-new-app"         // final WAR name (without .war)
    START_SCRIPT = "${WSL_BASE}/myappstartup.sh"
    STOP_SCRIPT  = "${WSL_BASE}/myappstop.sh"

    // Controlled maven local on Windows so WSL's ext4 isn't flooded by downloads
    // NOTE: Use double backslashes in Windows path for bat commands below.
    MAVEN_LOCAL = "C:\\\\.m2-jenkins-cache"

    BACKUP_DIR = "/home/aashudev/deploy/war_backups"
    LOGS_DIR   = "/home/aashudev/deploy/jenkins_logs"
  }

  stages {

    stage('Prepare') {
      steps {
        script {
          echo "Preparing environment and ensuring directories"
          // Create backup/log dirs inside WSL
          bat """
            wsl -- bash -c "mkdir -p ${BACKUP_DIR} ${LOGS_DIR}"
          """
        }
      }
    }

    stage('Build WAR') {
      steps {
        script {
          def profile = (params.BUILD == 'prod') ? 'wsl-prod' :
                        (params.BUILD == 'qa')   ? 'wsl-qa'  : 'wsl-dev'
          echo "Building with Maven profile: ${profile} using local repo at ${env.MAVEN_LOCAL}"

          // Use mvnw if available. On Windows, prefer running native mvnw which will call WSL java if required; using -Dmaven.repo.local to store cache on C:\
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

    stage('Stop Tomcat (target)') {
      steps {
        script {
          if (params.BUILD == 'dev' || params.BUILD == 'qa') {
            bat "wsl -- bash -c \"${STOP_SCRIPT} ${params.BUILD} || true\""
          } else {
            // Only stop prod1 for zero-downtime on other prod nodes
            bat "wsl -- bash -c \"${STOP_SCRIPT} prod1 || true\""
          }
        }
      }
    }

    stage('Clean Tomcat caches & backup previous WAR') {
      steps {
        script {
          // choose what tomcatHome to target for logs/backup
          def tomcatTarget = (params.BUILD == 'dev') ? TOMCAT_DEV : (params.BUILD == 'qa') ? TOMCAT_QA : TOMCAT_PROD_1

          // Clean work/temp, move existing WAR to backup (if exists)
          bat """
            wsl -- bash -lc '
              set -e
              # backup if war exists
              if [ -f "${tomcatTarget}/webapps/${ARTIFACT_NAME}.war" ]; then
                mkdir -p ${BACKUP_DIR}
                cp -v "${tomcatTarget}/webapps/${ARTIFACT_NAME}.war" "${BACKUP_DIR}/${ARTIFACT_NAME}.war.$(date +%Y%m%d_%H%M%S)" || true
              fi

              # cleanup tomcat caches to release disk and avoid old classloader issues
              rm -rf "${tomcatTarget}/work/*"  || true
              rm -rf "${tomcatTarget}/temp/*"  || true

              # remove exploded webapp folder if exists (safe because we backup war)
              rm -rf "${tomcatTarget}/webapps/${ARTIFACT_NAME}" || true
            '
          """
        }
      }
    }

    stage('Copy WAR to target(s)') {
      steps {
        script {
          // Copy WAR from Jenkins workspace into WSL tomcat webapps
          // Source path from Windows-mounted workspace; adjust JOB_NAME if folder differs
          def src = "/mnt/c/ProgramData/Jenkins/.jenkins/workspace/${env.JOB_NAME}/target/*.war"

          if (params.BUILD == 'dev') {
            bat "wsl -- bash -c 'cp -v ${src} ${TOMCAT_DEV}/webapps/${ARTIFACT_NAME}.war'"
          } else if (params.BUILD == 'qa') {
            bat "wsl -- bash -c 'cp -v ${src} ${TOMCAT_QA}/webapps/${ARTIFACT_NAME}.war'"
          } else {
            // Copy to all prod nodes, but start only prod1 to minimize downtime
            bat """
              wsl -- bash -c 'cp -v ${src} ${TOMCAT_PROD_1}/webapps/${ARTIFACT_NAME}.war'
              wsl -- bash -c 'cp -v ${src} ${TOMCAT_PROD_2}/webapps/${ARTIFACT_NAME}.war'
              wsl -- bash -c 'cp -v ${src} ${TOMCAT_PROD_3}/webapps/${ARTIFACT_NAME}.war'
            """
          }
        }
      }
    }

    stage('Start Tomcat (target)') {
      steps {
        script {
          if (params.BUILD == 'dev' || params.BUILD == 'qa') {
            bat "wsl -- bash -c '${START_SCRIPT} ${params.BUILD}'"
          } else {
            bat "wsl -- bash -c '${START_SCRIPT} prod1'"
          }
        }
      }
    }

    stage('Health Check') {
      steps {
        script {
          echo "Waiting for service to come up and running..."
          // adjustable retries and sleep
          def url = "http://localhost:8080/actuator/health"
          retry(6) {
            bat """
              powershell -Command "
                try {
                  \$resp = Invoke-WebRequest -Uri '${url}' -UseBasicParsing -TimeoutSec 5
                  if (\$resp.StatusCode -ne 200) { throw 'not 200' }
                } catch {
                  exit 1
                }
              "
            """
            sleep 5
          }
          echo "Healthcheck OK"
        }
      }
    }

    stage('Tail Logs (short)') {
      steps {
        script {
          def tomcatHome = (params.BUILD == 'dev') ? TOMCAT_DEV : (params.BUILD == 'qa') ? TOMCAT_QA : TOMCAT_PROD_1
          // show last 200 lines to console for quick troubleshooting
          bat "wsl -- bash -c \"tail -n 200 ${tomcatHome}/logs/myapplog.out || echo 'No log found'\""
        }
      }
    }
  } // stages

  post {
    success {
      script {
        echo "✅ Deployment successful for ${params.BUILD}"
      }
    }

    failure {
      script {
        echo "❌ Deployment failed — collecting logs and archiving"

        def tomcatHome = (params.BUILD == 'dev') ? TOMCAT_DEV : (params.BUILD == 'qa') ? TOMCAT_QA : TOMCAT_PROD_1

        // Copy log out and list backup dir
        bat """
          wsl -- bash -lc '
            mkdir -p ${LOGS_DIR}
            cp -v ${tomcatHome}/logs/myapplog.out ${LOGS_DIR}/myapplog.out_$(date +%Y%m%d_%H%M%S) || true
            ls -l ${LOGS_DIR} || true
          '
        """

        // copy to workspace and archive
        bat "wsl -- bash -c 'cp -r ${LOGS_DIR} /mnt/c/ProgramData/Jenkins/.jenkins/workspace/${env.JOB_NAME}/ || true'"

        // make sure the artifacts are available to Jenkins (pattern)
        archiveArtifacts artifacts: '**/myapplog.out_*', allowEmptyArchive: true
      }
    }

    always {
      script {
        // Clean workspace to prevent disk bloat on C: (and remove old target files)
        echo "Cleaning workspace"
        cleanWs()

        // Optional: compact WSL ext4 (admin required). This uses diskpart with shrink script on Windows.
        if (params.SHRINK_WSL.toBoolean()) {
          echo "Attempting WSL ext4.vhdx compact (requires diskpart script at C:\\shrink-wsl.txt and admin permissions)."
          // run diskpart script if present
          bat """
            if exist C:\\shrink-wsl.txt (
              powershell -Command "Start-Process -FilePath diskpart -ArgumentList '/s C:\\shrink-wsl.txt' -Verb runAs -Wait"
            ) else (
              echo 'C:\\shrink-wsl.txt not found. Skipping.'
            )
          """
        } else {
          echo "WSL shrink disabled for this run (SHRINK_WSL=false)."
        }
      }
    }
  }
}
