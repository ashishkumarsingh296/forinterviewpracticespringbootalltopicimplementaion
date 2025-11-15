// pipeline {
//     agent any

//     parameters {
//         choice(name: 'ENVIRONMENT', choices: ['DEV','QA'], description: "Choose environment for deployment")
//     }

//     environment {
//         # WSL Ubuntu DEV
//         DEV_TOMCAT_HOME = "/home/aashu/tomcat_dev"
//         # WSL Debian QA
//         QA_TOMCAT_HOME  = "/home/ashu/tomcat_qa"
//         BACKUP_DIR = "/home/aashu/backups"
//         WAR_NAME = "forinterviewpracticespringbootalltopicimplementaion.war"
//         PROJECT_DIR = "C:\\springboot-app"
//     }

//     stages {

//         stage('Checkout') {
//             steps {
//                 echo "Pulling latest code..."
//                 git branch: 'main', url: 'https://github.com/ashishkumarsingh296/forinterviewpracticespringbootalltopicimplementaion.git'
//             }
//         }

//         stage('Build WAR') {
//             steps {
//                 echo "Building WAR for ${params.ENVIRONMENT}..."
//                 bat "cd ${PROJECT_DIR} && mvn clean package -DskipTests"
//             }
//         }

//         stage('Deploy WAR to WSL') {
//             steps {
//                 script {
//                     if (params.ENVIRONMENT == 'DEV') {
//                         echo "Deploying WAR to Ubuntu WSL (DEV)..."
//                         bat """
//                         wsl cp /mnt/c/springboot-app/target/${WAR_NAME} ${DEV_TOMCAT_HOME}/webapps/
//                         wsl ${DEV_TOMCAT_HOME}/bin/shutdown.sh || true
//                         wsl ${DEV_TOMCAT_HOME}/bin/startup.sh
//                         """
//                     } else if (params.ENVIRONMENT == 'QA') {
//                         echo "Deploying WAR to Debian WSL (QA)..."
//                         bat """
//                         wsl -d Debian cp /mnt/c/springboot-app/target/${WAR_NAME} ${QA_TOMCAT_HOME}/webapps/
//                         wsl -d Debian ${QA_TOMCAT_HOME}/bin/shutdown.sh || true
//                         wsl -d Debian ${QA_TOMCAT_HOME}/bin/startup.sh
//                         """
//                     }
//                 }
//             }
//         }

//         stage('Post Deployment Health Check') {
//             steps {
//                 script {
//                     if (params.ENVIRONMENT == 'DEV') {
//                         bat """
//                         wsl curl -sSf http://127.0.0.1:8081/actuator/health && echo DEV OK || echo DEV FAILED
//                         """
//                     } else if (params.ENVIRONMENT == 'QA') {
//                         bat """
//                         wsl -d Debian curl -sSf http://127.0.0.1:8082/actuator/health && echo QA OK || echo QA FAILED
//                         """
//                     }
//                 }
//             }
//         }
//     }

//     post {
//         success {
//             echo "✅ Deployment to ${params.ENVIRONMENT} completed successfully!"
//         }
//         failure {
//             echo "❌ Deployment to ${params.ENVIRONMENT} failed. Check logs."
//         }
//     }
// }

pipeline {
    agent any

    parameters {
        choice(name: 'DEPLOY_ENV', choices: ['DEV', 'QA', 'BOTH'], description: 'Which environment to deploy')
    }

    environment {
        IMAGE_NAME = "myapp:latest"
        DEV_WAR = "target/forinterviewpracticespringbootalltopicimplementaion-0.0.1-SNAPSHOT.war"
        QA_WAR = "target/forinterviewpracticespringbootalltopicimplementaion-0.0.1-SNAPSHOT.war"
        PORT = "8080"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/ashishkumarsingh296/forinterviewpracticespringbootalltopicimplementaion.git'
            }
        }

        stage('Build WAR') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Prepare WARs for Docker') {
            steps {
                script {
                    if (params.DEPLOY_ENV == 'DEV') {
                        env.WARS_TO_COPY = "${DEV_WAR}:dev.war"
                    } else if (params.DEPLOY_ENV == 'QA') {
                        env.WARS_TO_COPY = "${QA_WAR}:qa.war"
                    } else {
                        env.WARS_TO_COPY = "${DEV_WAR}:dev.war ${QA_WAR}:qa.war"
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh """
                docker build \\
                  ${WARS_TO_COPY.split().collect { "--build-arg ${it}" }.join(' ')} \\
                  -t ${IMAGE_NAME} .
                """
            }
        }

        stage('Deploy Docker Container') {
            steps {
                sh """
                docker rm -f multi-tomcat || true
                docker run -d --name multi-tomcat -p ${PORT}:8080 ${IMAGE_NAME}
                """
            }
        }

        stage('Health Check') {
            steps {
                script {
                    if (params.DEPLOY_ENV == 'DEV' || params.DEPLOY_ENV == 'BOTH') {
                        sh "curl -sSf http://127.0.0.1:${PORT}/dev/ || echo 'DEV failed'"
                    }
                    if (params.DEPLOY_ENV == 'QA' || params.DEPLOY_ENV == 'BOTH') {
                        sh "curl -sSf http://127.0.0.1:${PORT}/qa/ || echo 'QA failed'"
                    }
                }
            }
        }
    }

    post {
        success { echo "✅ Deployment successful for ${params.DEPLOY_ENV}!" }
        failure { echo "❌ Deployment failed. Check logs." }
    }
}



