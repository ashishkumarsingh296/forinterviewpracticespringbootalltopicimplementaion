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

// pipeline {
//     agent any

//     parameters {
//         choice(name: 'DEPLOY_ENV', choices: ['DEV', 'QA', 'BOTH'], description: 'Which environment to deploy?')
//     }

//     environment {
//         IMAGE_NAME = "myapp:latest"
//         DEV_WAR = "target/forinterviewpracticespringbootalltopicimplementaion-0.0.1-SNAPSHOT.war"
//         QA_WAR  = "target/forinterviewpracticespringbootalltopicimplementaion-0.0.1-SNAPSHOT.war"
//     }

//     stages {

//         /* 1. Checkout */
//         stage('Checkout') {
//             steps {
//                 git branch: 'main',
//                     url: 'https://github.com/ashishkumarsingh296/forinterviewpracticespringbootalltopicimplementaion.git'
//             }
//         }

//         /* 2. Build WAR */
//         stage('Build') {
//             steps {
//                 bat 'mvn clean package -DskipTests'
//             }
//         }

//         /* 3. Build Docker Image */
//         stage('Docker Build') {
//             steps {
//                 script {
//                     def args = ""

//                     if (params.DEPLOY_ENV == 'DEV' || params.DEPLOY_ENV == 'BOTH')
//                         args += "--build-arg DEV_WAR=${DEV_WAR} "

//                     if (params.DEPLOY_ENV == 'QA' || params.DEPLOY_ENV == 'BOTH')
//                         args += "--build-arg QA_WAR=${QA_WAR} "

//                     bat """
//                         docker build ${args} -t ${IMAGE_NAME} .
//                     """
//                 }
//             }
//         }

//         /* 4. Run Docker Containers Separately */
//         stage('Deploy') {
//             steps {
//                 script {

//                     if (params.DEPLOY_ENV == 'DEV' || params.DEPLOY_ENV == 'BOTH') {
//                         bat "docker rm -f dev-container || true"
//                         bat "docker run -d --name dev-container -p 8081:8080 ${IMAGE_NAME}"
//                     }

//                     if (params.DEPLOY_ENV == 'QA' || params.DEPLOY_ENV == 'BOTH') {
//                         bat "docker rm -f qa-container || true"
//                         bat "docker run -d --name qa-container -p 8082:8080 ${IMAGE_NAME}"
//                     }
//                 }
//             }
//         }

//         /* 5. Health Check */
//         stage('Health Check') {
//             steps {
//                 script {
//                     if (params.DEPLOY_ENV == 'DEV' || params.DEPLOY_ENV == 'BOTH') {
//                         bat "curl -sSf http://127.0.0.1:8081/dev/ || echo DEV Failed"
//                     }
//                     if (params.DEPLOY_ENV == 'QA' || params.DEPLOY_ENV == 'BOTH') {
//                         bat "curl -sSf http://127.0.0.1:8082/qa/ || echo QA Failed"
//                     }
//                 }
//             }
//         }
//     }

//     /* 6. Post Notifications (Email + Console) */
//     post {
//         success {
//             mail to: 'ashish@example.com',
//                  subject: "SUCCESS: Deployment to ${params.DEPLOY_ENV}",
//                  body: "Deployment successfully completed."
//             echo "🎉 Deployment Success"
//         }
//         failure {
//             mail to: 'ashish@example.com',
//                  subject: "FAILED: Deployment to ${params.DEPLOY_ENV}",
//                  body: "Deployment failed. Please check Jenkins logs."
//             echo "❌ Deployment failed"
//         }
//     }
// }



pipeline {
    agent any

    parameters {
        choice(name: 'ENV', choices: ['DEV', 'QA'], description: 'Choose Environment')
    }

    environment {
        IMAGE_NAME = "java-multi-env"
        WAR_FILE = "target/forinterviewpracticespringbootalltopicimplementaion-0.0.1-SNAPSHOT.war"
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/ashishkumarsingh296/forinterviewpracticespringbootalltopicimplementaion.git'
            }
        }

        stage('Build WAR') {
            steps {
                bat "mvn clean package -DskipTests"
            }
        }

        stage('Build Docker Image (Windows)') {
            steps {
                bat """
                docker build ^
                  --build-arg APP_WAR=${WAR_FILE} ^
                  -t ${IMAGE_NAME}:${params.ENV} .
                """
            }
        }

        stage('Deploy Container') {
            steps {
                bat """
                docker stop myapp || echo Not running
                docker rm myapp || echo Not found

                docker run -d ^
                  --name myapp ^
                  -p 8081:8080 ^
                  ${IMAGE_NAME}:${params.ENV}
                """
            }
        }

        stage('Health Check') {
            steps {
                bat "curl -s http://localhost:8081 || echo Health Check Failed"
            }
        }
    }

    post {
        success {
            echo "✅ Deployment Successful!"
        }
        failure {
            echo "❌ Deployment Failed. Check the logs."
        }
    }
}
