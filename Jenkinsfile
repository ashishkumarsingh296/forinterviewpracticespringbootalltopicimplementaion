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
    choice(name: 'PROD_COLOR', choices: ['blue','green'], description: 'Deploy color (prod only) — pipeline will deploy to this color')
    choice(name: 'DEPLOY_STRATEGY', choices: ['blue-green','rolling'], description: 'Deployment strategy')
  }

  environment {
    WSL_BASE = "/home/aashudev/tomcat/multiple-server-config/bin"
    TOMCAT_DEV = "/home/aashudev/tomcat/multiple-server-config/dev-server/apache-tomcat-10.1.49-dev"
    TOMCAT_QA  = "/home/aashudev/tomcat/multiple-server-config/qa-server/apache-tomcat-10.1.49-qa"
    TOMCAT_PROD_BLUE = "/home/aashudev/tomcat/multiple-server-config/prod-blue/apache-tomcat-10.1.49-prod-blue"
    TOMCAT_PROD_GREEN = "/home/aashudev/tomcat/multiple-server-config/prod-green/apache-tomcat-10.1.49-prod-green"
    ARTIFACT_NAME = "my-new-app.war"
    # path to nginx active upstream symlink
    NGINX_ACTIVE="/etc/nginx/upstreams/active_upstream.conf"
    NGINX_UPSTREAM_DIR="/etc/nginx/upstreams"
  }

  stages {

    stage('Build WAR') {
      steps {
        script {
          def profile = (params.BUILD == 'prod') ? 'wsl-prod' : (params.BUILD == 'qa' ? 'wsl-qa' : 'wsl-dev')
          bat "mvnw clean package -P${profile} -DskipTests"
        }
      }
    }

    stage('Copy WAR to target') {
      steps {
        script {
          if (params.BUILD == 'dev') {
            bat """wsl cp /mnt/c/ProgramData/Jenkins/.jenkins/workspace/${env.JOB_NAME}/target/*.war ${TOMCAT_DEV}/webapps/${ARTIFACT_NAME}"""
          } else if (params.BUILD == 'qa') {
            bat """wsl cp /mnt/c/ProgramData/Jenkins/.jenkins/workspace/${env.JOB_NAME}/target/*.war ${TOMCAT_QA}/webapps/${ARTIFACT_NAME}"""
          } else {
            // prod: copy to the selected color (we deploy to the "inactive" color)
            def color = params.PROD_COLOR
            def tomcatHome = (color == 'blue') ? TOMCAT_PROD_BLUE : TOMCAT_PROD_GREEN
            // name war consistently inside webapps folder
            bat """wsl cp /mnt/c/ProgramData/Jenkins/.jenkins/workspace/${env.JOB_NAME}/target/*.war ${tomcatHome}/webapps/${ARTIFACT_NAME}"""
          }
        }
      }
    }

    stage('Stop target Tomcat (if running)') {
      steps {
        script {
          if (params.BUILD == 'dev') {
            bat "wsl bash -lc '${WSL_BASE}/myappstop.sh dev || true'"
          } else if (params.BUILD == 'qa') {
            bat "wsl bash -lc '${WSL_BASE}/myappstop.sh qa || true'"
          } else {
            def color = params.PROD_COLOR
            bat "wsl bash -lc '${WSL_BASE}/myappstop.sh prod-${color} || true'"
          }
        }
      }
    }

    stage('Start target Tomcat') {
      steps {
        script {
          if (params.BUILD == 'dev') {
            bat "wsl bash -lc '${WSL_BASE}/myappstartup.sh dev'"
          } else if (params.BUILD == 'qa') {
            bat "wsl bash -lc '${WSL_BASE}/myappstartup.sh qa'"
          } else {
            def color = params.PROD_COLOR
            bat "wsl bash -lc '${WSL_BASE}/myappstartup.sh prod-${color}'"
          }
        }
      }
    }

    stage('Health check new instances') {
      steps {
        script {
          if (params.TARGET == 'prod') {
            // check health of upstream for the deployed color
            def color = params.PROD_COLOR
            // list of instance URLs for the color - update these IPs/ports accordingly
            def instances = (color == 'blue') ? ['http://10.0.0.11:8080/actuator/health','http://10.0.0.12:8080/actuator/health'] : ['http://10.0.0.21:8080/actuator/health','http://10.0.0.22:8080/actuator/health']
            // simple loop: curl each
            for (i=0; i<instances.size(); i++) {
              def url = instances[i]
              bat "wsl bash -lc 'for try in {1..10}; do curl -fsS ${url} && exit 0 || sleep 2; done; echo \"Health check failed for ${url}\"; exit 1'"
            }
          } else {
            echo "health checks skipped (non-prod)"
          }
        }
      }
    }

    stage('Switch Nginx to new color (blue-green)') {
      when { expression { params.TARGET == 'prod' } }
      steps {
        script {
          def color = params.PROD_COLOR
          def upstreamFile = (color == 'blue') ? "${NGINX_UPSTREAM_DIR}/prod_blue.conf" : "${NGINX_UPSTREAM_DIR}/prod_green.conf"
          // create symlink active_upstream.conf -> prod_blue.conf or prod_green.conf
          bat "wsl bash -lc 'sudo ln -sf ${upstreamFile} ${NGINX_ACTIVE} && nginx -t && sudo nginx -s reload'"
          echo "Nginx switched to ${color}"
        }
      }
    }

    stage('Post-deploy cleanup (optional)') {
      steps {
        script {
          if (params.// Final One with jar working fine
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
    choice(name: 'PROD_COLOR', choices: ['blue','green'], description: 'Deploy color (only for PROD)')
    choice(name: 'DEPLOY_STRATEGY', choices: ['blue-green','rolling'], description: 'Deployment strategy')
  }

 environment {
  WSL_BASE = "/home/aashudev/tomcat/multiple-server-config/bin"
  TOMCAT_DEV = "/home/aashudev/tomcat/multiple-server-config/dev-server/apache-tomcat-10.1.49-dev"
  TOMCAT_QA  = "/home/aashudev/tomcat/multiple-server-config/qa-server/apache-tomcat-10.1.49-qa"
  TOMCAT_PROD_BLUE = "/home/aashudev/tomcat/multiple-server-config/prod-blue/apache-tomcat-10.1.49-prod-blue"
  TOMCAT_PROD_GREEN = "/home/aashudev/tomcat/multiple-server-config/prod-green/apache-tomcat-10.1.49-prod-green"
  ARTIFACT_NAME = "my-new-app.war"

  // path to nginx active upstream symlink ✅ FIXED
  NGINX_ACTIVE="/etc/nginx/upstreams/active_upstream.conf"
  NGINX_UPSTREAM_DIR="/etc/nginx/upstreams"
}


  stages {

    stage('Build WAR') {
      steps {
        script {
          def profile = (params.BUILD == 'prod') ? 'prod' :
                        (params.BUILD == 'qa')   ? 'wsl-qa' : 'wsl-dev'

          bat "mvn clean package -P${profile} -DskipTests"
        }
      }
    }

    stage('Copy WAR to target') {
      steps {
        script {
          if (params.BUILD == 'dev') {
            bat """wsl cp target/*.war ${TOMCAT_DEV}/webapps/${ARTIFACT_NAME}"""

          } else if (params.BUILD == 'qa') {
            bat """wsl cp target/*.war ${TOMCAT_QA}/webapps/${ARTIFACT_NAME}"""

          } else {
            def tomcatHome = (params.PROD_COLOR == 'blue') ?
                              TOMCAT_PROD_BLUE : TOMCAT_PROD_GREEN

            bat """wsl cp target/*.war ${tomcatHome}/webapps/${ARTIFACT_NAME}"""
          }
        }
      }
    }

    stage('Stop target Tomcat') {
      steps {
        script {
          if (params.BUILD == 'dev') {
            bat "wsl bash -lc '${WSL_BASE}/myappstop.sh dev || true'"

          } else if (params.BUILD == 'qa') {
            bat "wsl bash -lc '${WSL_BASE}/myappstop.sh qa || true'"

          } else {
            bat "wsl bash -lc '${WSL_BASE}/myappstop.sh prod-${params.PROD_COLOR} || true'"
          }
        }
      }
    }

    stage('Start target Tomcat') {
      steps {
        script {
          if (params.BUILD == 'dev') {
            bat "wsl bash -lc '${WSL_BASE}/myappstartup.sh dev'"

          } else if (params.BUILD == 'qa') {
            bat "wsl bash -lc '${WSL_BASE}/myappstartup.sh qa'"

          } else {
            bat "wsl bash -lc '${WSL_BASE}/myappstartup.sh prod-${params.PROD_COLOR}'"
          }
        }
      }
    }

    stage('Health check (PROD only)') {
      when { expression { params.BUILD == 'prod' } }
      steps {
        script {
          def instances = (params.PROD_COLOR == 'blue') ?
            ['http://127.0.0.1:9080/actuator/health'] :
            ['http://127.0.0.1:9081/actuator/health']

          for (url in instances) {
            bat "wsl bash -lc 'for i in {1..10}; do curl -fs ${url} && exit 0 || sleep 3; done; exit 1'"
          }
        }
      }
    }

    stage('Switch Nginx (Blue-Green)') {
      when { expression { params.BUILD == 'prod' } }
      steps {
        script {
          def upstreamFile = (params.PROD_COLOR == 'blue') ?
            "${NGINX_UPSTREAM_DIR}/prod_blue.conf" :
            "${NGINX_UPSTREAM_DIR}/prod_green.conf"

          bat """
          wsl bash -lc '
            sudo ln -sf ${upstreamFile} ${NGINX_ACTIVE} &&
            sudo nginx -t &&
            sudo nginx -s reload
          '
          """
        }
      }
    }

    stage('Stop OLD prod color (optional)') {
      when { expression { params.BUILD == 'prod' } }
      steps {
        script {
          def oldColor = (params.PROD_COLOR == 'blue') ? 'green' : 'blue'
          echo "Old color = ${oldColor} (manual stop optional)"
          // bat "wsl bash -lc '${WSL_BASE}/myappstop.sh prod-${oldColor} || true'"
        }
      }
    }

  } // stages

  post {
    success {
      echo "✅ Deployment successful: ${params.BUILD} ${params.BUILD == 'prod' ? params.PROD_COLOR : ''}"
    }

    failure {
      script {
        bat "wsl bash -lc 'mkdir -p /home/aashudev/deploy/jenkins_logs || true'"
        bat "wsl bash -lc 'cp /home/aashudev/tomcat/*/logs/*.log /home/aashudev/deploy/jenkins_logs/ || true'"
        bat "wsl cp /home/aashudev/deploy/jenkins_logs/* /mnt/c/ProgramData/Jenkins/.jenkins/workspace/${env.JOB_NAME}/ || true"

        archiveArtifacts artifacts: '**/*.log', allowEmptyArchive: true
      }
      echo "❌ Deployment failed — logs archived"
    }
  }
}
