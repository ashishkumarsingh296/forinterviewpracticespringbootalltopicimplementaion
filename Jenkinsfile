// pipeline {
//     agent any

//     parameters {
//         choice(name: 'ENV', choices: ['DEV', 'QA', 'BOTH'], description: 'Choose environment to deploy')
//     }

//     environment {
//         IMAGE_NAME = "java-multi-env"
//         VERSION = "${env.BUILD_NUMBER}"  // Jenkins build number
//         JAR_FILE = "target/*SNAPSHOT.jar"
//     }

//     stages {

//         stage('Checkout Code') {
//             steps {
//                 git branch: 'main', url: 'https://github.com/ashishkumarsingh296/forinterviewpracticespringbootalltopicimplementaion.git'
//             }
//         }

//         stage('Build') {
//             steps {
//                 bat "mvn clean package -DskipTests"
//             }
//         }

//         stage('Build Docker Image') {
//             steps {
//                 bat """
//                 // docker build -t java-multi-env:latest .
//                 docker build -t ${IMAGE_NAME}:${VERSION} .
//                 """
//             }
//         }

//    stage('Deploy') {
//     steps {
//         script {
//             def envChoice = params.ENV
//             def version = env.BUILD_NUMBER

//             if(envChoice == 'DEV') {
//                 bat """
//                 docker-compose down
//                 docker-compose up -d --build --no-deps --force-recreate app-dev
//                 docker tag java-multi-env:${version} myworkspace/java-multi-env:dev-${version}
//                 """
//             } else if(envChoice == 'QA') {
//                 bat """
//                 docker-compose down
//                 docker-compose up -d --build --no-deps --force-recreate app-qa
//                 docker tag java-multi-env:${version} myworkspace/java-multi-env:qa-${version}
//                 """
//             } else if(envChoice == 'BOTH') {
//                 bat """
//                 docker-compose down
//                 docker-compose up -d --build --force-recreate
//                 docker tag java-multi-env:${version} myworkspace/java-multi-env:dev-${version}
//                 docker tag java-multi-env:${version} myworkspace/java-multi-env:qa-${version}
//                 """
//             }
//         }
//     }
//   }
// }

//     post {
//         success {
//             echo "Deployment Successful!"
//         }
//         failure {
//             echo "Deployment Failed!"
//         }
//     }
// }
////////////////////////aaa////////////////


// pipeline {
//     agent any

//     parameters {
//         choice(name: 'ENV', choices: ['DEV', 'QA', 'BOTH'], description: 'Choose environment to deploy')
//     }

//     environment {
//         IMAGE_NAME = "java-multi-env"
//         VERSION = "1.0.${BUILD_NUMBER}"
//     }

//     stages {
//         stage('Checkout Code') {
//             steps {
//                 git branch: 'main', url: 'https://github.com/ashishkumarsingh296/forinterviewpracticespringbootalltopicimplementaion.git'
//             }
//         }
        

//         stage('Build') {
//             steps {
//                 bat "mvn clean package -DskipTests"
//             }
//         }

//         stage('Build Docker Image') {
//             steps {
//                 bat """
//                     docker build -t ${IMAGE_NAME}:${VERSION} .
//                 """
//             }
//         }
//         stage('Debug Workspace') {
//     steps {
//         bat "dir"
//         bat "type docker-compose.yml"
//     }
// }

//         stage('Deploy') {
//             steps {
//                 script {
//                     if (params.ENV == 'DEV') {
//                         bat "docker-compose up -d --build app-dev db-dev load-balancer"
//                     } else if (params.ENV == 'QA') {
//                         bat "docker-compose up -d --build app-qa db-qa load-balancer"
//                     } else {
//                         bat "docker-compose up -d --build"
//                     }
//                 }
//             }
//         }
//     }

//     post {
//         success {
//             echo "Deployment Successful!"
//         }
//         failure {
//             echo "Deployment Failed!"
//         }
//     }
// }


pipeline {
    agent any

    parameters {
        choice(name: 'ENV', choices: ['DEV', 'QA', 'BOTH'], description: 'Choose environment to deploy')
    }

    environment {
        IMAGE_NAME = "java-multi-env"
        VERSION = "${BUILD_NUMBER}"
        WORK_DIR = "${WORKSPACE}"
    }

    stages {

        stage('Checkout Source Code') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/ashishkumarsingh296/forinterviewpracticespringbootalltopicimplementaion.git'
            }
        }

        stage('Build JAR') {
            steps {
                bat "mvn clean package -DskipTests"
            }
        }

        stage('Build Versioned Docker Image') {
            steps {
                bat """
                    docker build -t ${IMAGE_NAME}:${VERSION} .
                    docker tag ${IMAGE_NAME}:${VERSION} ${IMAGE_NAME}:latest
                """
            }
        }

        stage('Copy Compose Files') {
            steps {
                bat """
                    copy ${WORKSPACE}\\docker-compose.yml .
                    copy ${WORKSPACE}\\nginx.conf .
                """
            }
        }

        stage('Deploy Using Docker Compose') {
            steps {
                script {

                    if (params.ENV == "DEV") {
                        echo "🚀 Deploying DEV only..."

                        bat """
                            docker-compose down -v
                            set VERSION=${VERSION}
                            docker-compose up -d --build db-dev app-dev load-balancer
                        """
                    }

                    if (params.ENV == "QA") {
                        echo "🚀 Deploying QA only..."

                        bat """
                            docker-compose down -v
                            set VERSION=${VERSION}
                            docker-compose up -d --build db-qa app-qa load-balancer
                        """
                    }

                    if (params.ENV == "BOTH") {
                        echo "🚀 Deploying BOTH DEV + QA..."

                        bat """
                            docker-compose down -v
                            set VERSION=${VERSION}
                            docker-compose up -d --build
                        """
                    }
                }
            }
        }
    }

    post {
        success {
            echo "🎉 Deployment Successful!"
        }
        failure {
            echo "❌ Deployment Failed!"
        }
    }
}
