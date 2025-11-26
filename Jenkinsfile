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
    WSL_BASE = "/home/aashudev/tomcat/multiple-server-config/bin"
    ARTIFACT_NAME = "my-new-app.war"

    TOMCAT_PROD_1 = "/home/aashudev/tomcat/multiple-server-config/prod1-server/apache-tomcat-10.1.49-prod-1"
    TOMCAT_PROD_2 = "/home/aashudev/tomcat/multiple-server-config/prod2-server/apache-tomcat-10.1.49-prod-2"
    TOMCAT_PROD_3 = "/home/aashudev/tomcat/multiple-server-config/prod3-server/apache-tomcat-10.1.49-prod-3"

    TOMCAT_DEV="/home/aashudev/tomcat/multiple-server-config/dev-server/apache-tomcat-10.1.49-dev"
    TOMCAT_QA="/home/aashudev/tomcat/multiple-server-config/qa-server/apache-tomcat-10.1.49-qa"
                
    NGINX_CONF = "/etc/nginx/conf.d/nginx.conf"

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
          // Call helper script inside WSL, pass args (safe - no complicated quoting)
          bat "wsl ${WSL_BASE}/prepare_backups.sh \"${TOMCAT_PROD_1}\" \"${TOMCAT_PROD_2}\" \"${TOMCAT_PROD_3}\" \"${BACKUP_DIR}\" \"${ARTIFACT_NAME}\""
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
            // call a helper to copy to all prod nodes
            bat "wsl ${WSL_BASE}/copy_to_all_prod.sh \"${TOMCAT_PROD_1}\" \"${TOMCAT_PROD_2}\" \"${TOMCAT_PROD_3}\" \"${ARTIFACT_NAME}\""
          }
        }
      }
    }

 stage('Restart Application(s)') {
  steps {
    script {
      if (params.BUILD == 'dev') {
        bat "wsl ${WSL_BASE}/myappstop.sh dev"
        bat "wsl ${WSL_BASE}/myappstartup.sh dev"
      } 
      else if (params.BUILD == 'qa') {
        bat "wsl ${WSL_BASE}/myappstop.sh qa"
        bat "wsl ${WSL_BASE}/myappstartup.sh qa"
      } 
      else {
        bat "wsl ${WSL_BASE}/myappstop.sh prod-1"
        bat "wsl ${WSL_BASE}/myappstop.sh prod-2"
        bat "wsl ${WSL_BASE}/myappstop.sh prod-3"

        bat "wsl ${WSL_BASE}/myappstartup.sh prod-1"
        bat "wsl ${WSL_BASE}/myappstartup.sh prod-2"
        bat "wsl ${WSL_BASE}/myappstartup.sh prod-3"
      }
    }
  }
}
              stage('Tail Logs') {
        steps {
            script {
                def tomcatHome = (params.BUILD == 'dev') ? TOMCAT_DEV : TOMCAT_QA
                bat """wsl bash -c "tail -n 200 ${tomcatHome}/logs/myapplog.out || echo NoLogs" """
            }
        }
    }
}


    stage('Health Check') {
      steps {
        script {
          if (params.BUILD == 'prod') {
            // health-check helper returns non-zero and lists failed indices if failure
            try {
              bat "wsl ${WSL_BASE}/health_check_all.sh \"9080\" \"9081\" \"9082\""
            } catch (err) {
              echo "Health check reported failure(s). Attempting per-node rollback(s)."

              // call rollback helper which scans backups and restores failing nodes.
              // rollback helper accepts: nodePath backupDir artifactName wslBase
              bat "wsl ${WSL_BASE}/rollback_unhealthy_nodes.sh \"${TOMCAT_PROD_1}\" \"${TOMCAT_PROD_2}\" \"${TOMCAT_PROD_3}\" \"${BACKUP_DIR}\" \"${ARTIFACT_NAME}\" \"${WSL_BASE}\""

              error "One or more PROD nodes failed health checks — rollback attempted. See archived logs."
            }
          } else {
            echo "Skipping PROD health-check for non-PROD deployment"
          }
        }
      }
    }

    stage('Post-deploy: Nginx Verification (optional)') {
      when { expression { params.BUILD == 'prod' } }
      steps {
        script {
          bat "wsl curl -I http://127.0.0.1:90 || true"
        }
      }
    }
  }

  post {
    success {
      echo "✅ Deployment successful for ${params.BUILD}"
    }
    failure {
      script {
        // collect logs via helper then copy to Windows workspace for archiving
        bat "wsl ${WSL_BASE}/collect_logs.sh \"${LOGS_DIR}\" \"${TOMCAT_PROD_1}\" \"${TOMCAT_PROD_2}\" \"${TOMCAT_PROD_3}\" || true"
        // copy logs to Windows Jenkins workspace for artifact archiving
        bat "wsl cp -r ${LOGS_DIR} /mnt/c/ProgramData/Jenkins/.jenkins/workspace/${env.JOB_NAME}/ || true"
        archiveArtifacts artifacts: '**/jenkins_logs/**', allowEmptyArchive: true
      }
      echo "❌ Deployment failed — logs archived"
    }
  }
}

