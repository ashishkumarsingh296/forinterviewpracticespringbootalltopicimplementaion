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

  parameters {
    choice(name: 'BUILD', choices: ['dev','qa','prod'], description: 'Deploy target environment')
    choice(name: 'PROD_COLOR', choices: ['blue','green'], description: 'NOT USED - kept for backward compatibility')
    choice(name: 'DEPLOY_STRATEGY', choices: ['round-robin','rolling'], description: 'Deployment strategy (round-robin uses all nodes)')
  }

  environment {
    // WSL paths (adjust if needed)
    WSL_BASE = "/home/aashudev/tomcat/bin"
    ARTIFACT_NAME = "my-new-app.war"

    // Prod nodes
    TOMCAT_PROD_1 = "/home/aashudev/tomcat/prod-1/apache-tomcat-10.1.49-1"
    TOMCAT_PROD_2 = "/home/aashudev/tomcat/prod-2/apache-tomcat-10.1.49-2"
    TOMCAT_PROD_3 = "/home/aashudev/tomcat/prod-3/apache-tomcat-10.1.49-3"

    // Dev/QA (example)
    TOMCAT_DEV = "/home/aashudev/tomcat/dev/apache-tomcat-10.1.49-dev"
    TOMCAT_QA  = "/home/aashudev/tomcat/qa/apache-tomcat-10.1.49-qa"

    // nginx listen port (example)
    NGINX_CONF = "/etc/nginx/conf.d/myapp.conf"

    // backup directory (inside WSL)
    BACKUP_DIR = "/home/aashudev/deploy/war_backups"
    LOGS_DIR = "/home/aashudev/deploy/jenkins_logs"
  }

  stages {

    stage('Build WAR') {
      steps {
        script {
          def profile = (params.BUILD == 'prod') ? 'prod' :
                        (params.BUILD == 'qa')   ? 'wsl-qa' : 'wsl-dev'
          echo "Building with Maven profile: ${profile}"
          bat "mvn clean package -P${profile} -DskipTests"
        }
      }
    }

    stage('Prepare Backups (PROD only)') {
      when { expression { params.BUILD == 'prod' } }
      steps {
        script {
          bat """
          wsl bash -lc '
            mkdir -p ${BACKUP_DIR} || true
            # backup current WARs (if exist)
            for d in ${TOMCAT_PROD_1} ${TOMCAT_PROD_2} ${TOMCAT_PROD_3}; do
              if [ -f "$d/webapps/${ARTIFACT_NAME}" ]; then
                cp "$d/webapps/${ARTIFACT_NAME}" "${BACKUP_DIR}/$(basename $d)-${ARTIFACT_NAME}-$(date +%s)" || true
              fi
            done
          '
          """
        }
      }
    }

    stage('Copy WARs') {
      steps {
        script {
          if (params.BUILD == 'dev') {
            bat "wsl cp target/*.war ${TOMCAT_DEV}/webapps/${ARTIFACT_NAME}"
          } else if (params.BUILD == 'qa') {
            bat "wsl cp target/*.war ${TOMCAT_QA}/webapps/${ARTIFACT_NAME}"
          } else {
            // PROD -> copy to all 3 nodes
            bat """
            wsl bash -lc '
              cp target/*.war ${TOMCAT_PROD_1}/webapps/${ARTIFACT_NAME} &&
              cp target/*.war ${TOMCAT_PROD_2}/webapps/${ARTIFACT_NAME} &&
              cp target/*.war ${TOMCAT_PROD_3}/webapps/${ARTIFACT_NAME}
            '
            """
          }
        }
      }
    }

    stage('Restart Application(s)') {
      steps {
        script {
          if (params.BUILD == 'dev') {
            bat "wsl bash -lc '${WSL_BASE}/myappstop.sh dev || true; ${WSL_BASE}/myappstartup.sh dev'"
          } else if (params.BUILD == 'qa') {
            bat "wsl bash -lc '${WSL_BASE}/myappstop.sh qa || true; ${WSL_BASE}/myappstartup.sh qa'"
          } else {
            // PROD -> restart all 3
            bat """
            wsl bash -lc '
              ${WSL_BASE}/myappstop.sh prod-1 || true
              ${WSL_BASE}/myappstop.sh prod-2 || true
              ${WSL_BASE}/myappstop.sh prod-3 || true

              ${WSL_BASE}/myappstartup.sh prod-1
              ${WSL_BASE}/myappstartup.sh prod-2
              ${WSL_BASE}/myappstartup.sh prod-3
            '
            """
          }
        }
      }
    }

    stage('Health Check') {
      steps {
        script {
          if (params.BUILD == 'prod') {
            def urls = [
              "http://127.0.0.1:9080/actuator/health",
              "http://127.0.0.1:9081/actuator/health",
              "http://127.0.0.1:9082/actuator/health"
            ]

            // run health checks, collect failures
            def failures = []
            for (i = 0; i < urls.size(); i++) {
              def u = urls[i]
              echo "Checking ${u}"
              def cmd = "wsl bash -lc 'for j in {1..10}; do curl -sf ${u} && exit 0 || sleep 3; done; exit 1'"
              try {
                bat cmd
              } catch (err) {
                echo "Health check failed for ${u}"
                failures.add(i) // store index 0-based (9080 -> 0, 9081 -> 1, 9082 -> 2)
              }
            }

            // If any failures -> attempt rollback for failed nodes
            if (failures.size() > 0) {
              echo "Detected unhealthy nodes: ${failures}"
              // restore backups for each failed node
              for (idx in failures) {
                def nodePath = (idx == 0) ? TOMCAT_PROD_1 : (idx == 1) ? TOMCAT_PROD_2 : TOMCAT_PROD_3
                echo "Attempting restore on ${nodePath}"
                // find most recent backup for that node (simple approach: pick last matching file)
                bat """
                wsl bash -lc '
                  set -e
                  backup=$(ls -1t ${BACKUP_DIR}/$(basename ${nodePath})-${ARTIFACT_NAME}-* 2>/dev/null | head -n1 || true)
                  if [ -n "$backup" ]; then
                    echo "Restoring $backup -> ${nodePath}/webapps/${ARTIFACT_NAME}"
                    cp "$backup" ${nodePath}/webapps/${ARTIFACT_NAME} || true
                    ${WSL_BASE}/myappstop.sh $(basename ${nodePath}) || true
                    ${WSL_BASE}/myappstartup.sh $(basename ${nodePath})
                  else
                    echo "No backup found for ${nodePath}, will try to remove faulty WAR and leave node stopped"
                    rm -f ${nodePath}/webapps/${ARTIFACT_NAME} || true
                    ${WSL_BASE}/myappstop.sh $(basename ${nodePath}) || true
                  fi
                '
                """
              }

              // collect logs and fail pipeline
              error "One or more PROD nodes failed health checks — rollback attempted. See archived logs."
            } else {
              echo "All PROD nodes passed health check."
            }

          } else {
            // For dev/qa, simple optional health check (non-blocking)
            if (params.BUILD == 'dev') {
              echo "Skipping health check for DEV (optional)"
            } else {
              echo "Skipping health check for QA (optional)"
            }
          }
        }
      }
    }

    stage('Post-deploy: Nginx Verification (optional)') {
      when { expression { params.BUILD == 'prod' } }
      steps {
        script {
          // optional: test nginx proxies
          bat "wsl bash -lc 'curl -I http://127.0.0.1:90 || true'"
        }
      }
    }

  } // stages

  post {
    success {
      echo "✅ Deployment successful for ${params.BUILD}"
    }
    failure {
      script {
        // collect tomcat logs to Jenkins workspace for debugging
        bat "wsl bash -lc 'mkdir -p ${LOGS_DIR} || true'"
        bat "wsl bash -lc 'cp -v ${TOMCAT_PROD_1}/logs/* ${LOGS_DIR}/ 2>/dev/null || true'"
        bat "wsl bash -lc 'cp -v ${TOMCAT_PROD_2}/logs/* ${LOGS_DIR}/ 2>/dev/null || true'"
        bat "wsl bash -lc 'cp -v ${TOMCAT_PROD_3}/logs/* ${LOGS_DIR}/ 2>/dev/null || true'"
        // copy to Windows Jenkins workspace so Jenkins can archive
        bat "wsl cp -r ${LOGS_DIR} /mnt/c/ProgramData/Jenkins/.jenkins/workspace/${env.JOB_NAME}/ || true"

        archiveArtifacts artifacts: '**/jenkins_logs/**', allowEmptyArchive: true
      }
      echo "❌ Deployment failed — logs archived"
    }
  }
}

