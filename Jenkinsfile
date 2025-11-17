// one WSL instance

// pipeline {
//     agent any

//     environment {
//         IMAGE_NAME = "java-single-env"
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
//                 docker build -t ${IMAGE_NAME}:latest .
//                 """
//             }
//         }

//       stage('Deploy Stack') {
//     steps {
//         bat """
//         cd %WORKSPACE%
//         docker-compose down
//         docker-compose up -d --build
//         """
//     }
// }

// stage('Auto-Scaling Check') {
//     steps {
//         bat "powershell -ExecutionPolicy Bypass -File %WORKSPACE%\\monitor.ps1"
//     }
// }
//     }

//     post {
//         success {
//             echo "Deployment and Auto-Scaling Started Successfully!"
//         }
//         failure {
//             echo "Deployment Failed!"
//         }
//     }
// }


pipeline {
    agent any

    environment {
        IMAGE_NAME = "java-single-env"
        JAR_FILE = "target/*SNAPSHOT.jar"
        DOCKER_COMPOSE_PATH = "\"C:\\Users\\ASHISH SINGH\\workspace\\InterviewAllVersion\\docker-compose.yml\""
        SCRIPT_PATH = "\"C:\\Users\\ASHISH SINGH\\workspace\\InterviewAllVersion\\monitor-and-scale.ps1\""
    }

pipeline {
    agent any

    environment {
        IMAGE_NAME = "java-single-env"
        JAR_FILE = "target/*SNAPSHOT.jar"
        DOCKER_COMPOSE_PATH = "\"C:\\ProgramData\\Jenkins\\.jenkins\\workspace\\InterviewAllVersion\\docker-compose.yml\""
        SCRIPT_PATH = "\"C:\\ProgramData\\Jenkins\\.jenkins\\workspace\\InterviewAllVersion\\monitor-and-scale.ps1\""
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/ashishkumarsingh296/forinterviewpracticespringbootalltopicimplementaion.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Build & Deploy') {
            steps {
                echo "Building Docker images..."
                bat "docker compose -f %DOCKER_COMPOSE_PATH% build"

                echo "Starting containers..."
                bat "docker compose -f %DOCKER_COMPOSE_PATH% up -d"
            }
        }

        stage('Monitor & Auto-Scale') {
            steps {
                echo "Running auto-scaling script..."
                bat "powershell -ExecutionPolicy Bypass -File %SCRIPT_PATH%"
            }
        }
    }

    post {
        success {
            echo 'Deployment and scaling completed successfully!'
        }
        failure {
            echo 'Deployment failed. Check logs.'
        }
    }
}







// pipeline {
//     agent any

//     parameters {
//         choice(name: 'ENV', choices: ['DEV', 'QA', 'BOTH'], description: 'Choose environment to deploy')
//     }

//     environment {
//         SPRING_PROFILE = "${params.ENV.toLowerCase()}" // dev, qa, etc.
//         IMAGE_NAME = "java-single-env"
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
//                 bat "docker build -t ${IMAGE_NAME}:latest ."
//             }
//         }



//         stage('Deploy DEV') {
//         //     when { expression { params.ENV == 'DEV' || params.ENV == 'BOTH' } }
//         //     steps {
//         //         bat """
//         //         cd %WORKSPACE%
//         //         docker-compose down

//         //         docker-compose up -d --build --scale app=2
//         //         docker-compose exec nginx nginx -s reload || echo "Nginx reload failed"
//         //         """
//         //     }

//         steps {
//         bat """
//         cd %WORKSPACE%
//         docker-compose down
//         docker-compose up -d --build
//         """
//     }
//          }

//         // stage('Deploy QA') {
//         //     when { expression { params.ENV == 'QA' || params.ENV == 'BOTH' } }
//         //     steps {
//         //         bat """
//         //         cd %WORKSPACE%
//         //         docker-compose down
//         //         docker-compose up -d --build --scale app=2
//         //         docker-compose exec nginx nginx -s reload || echo "Nginx reload failed"
//         //         """
//         //     }
//         // }

//     }

//     post {
//         success { echo "Deployment Successful!" }
//         failure { echo "Deployment Failed!" }
//     }
// }




// pipeline {
//     agent any

//     parameters {
//         choice(name: 'ENV', choices: ['DEV', 'QA', 'BOTH'], description: 'Choose environment to deploy')
//     }

//     environment {
//         IMAGE_NAME = "java-single-env"
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
//               docker build -t java-single-env:latest .

//                 """
//             }
//         }

//         // stage('Deploy DEV') {
//         //     when { expression { params.ENV == 'DEV' || params.ENV == 'BOTH' } }
//         //     steps {
//         //         bat """
//         //         // docker stop myapp-dev || echo Not running
//         //         // docker rm myapp-dev || echo Not found

//         //         // docker run -d ^
//         //         //   --name myapp-dev ^
//         //         //   -p 8081:8080 ^
//         //         //   -e SPRING_PROFILES_ACTIVE=wsl ^
//         //         //   --add-host redis:172.21.37.255 ^
//         //         //   ${IMAGE_NAME}:latest
//         //         docker stop myapp-dev || echo Not running
//         //         docker rm myapp-dev || echo Not found
//         //         docker run -d ^
//         //         --name myapp-dev ^
//         //         -p 8081:8080 ^
//         //         -e SPRING_PROFILES_ACTIVE=wsl ^
//         //          ${IMAGE_NAME}:latest
//         //         """
//         //     }
//         // }

//         // stage('Deploy QA') {
//         //     when { expression { params.ENV == 'QA' || params.ENV == 'BOTH' } }
//         //     steps {
//         //         bat """
//         //         docker stop myapp-qa || echo Not running
//         //         docker rm myapp-qa || echo Not found
//         //         docker run -d ^
//         //         --name myapp-qa ^
//         //         -p 8082:8080 ^
//         //         -e SPRING_PROFILES_ACTIVE=wsl ^
//         //          ${IMAGE_NAME}:latest
//         //         """
//         //     }
//         // }


//          stage('Deploy DEV') {
//            when { expression { params.ENV == 'DEV' || params.ENV == 'BOTH' } }
//          steps {
//           bat """
//           cd %WORKSPACE%
//           docker-compose down
//           docker-compose up -d --build
//          """
//       }
//         }

//         stage('Deploy QA') {
//             when { expression { params.ENV == 'QA' || params.ENV == 'BOTH' } }
//             steps {
//                 bat """
//                 cd %WORKSPACE%
//                 docker-compose down
//                 """
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





////////////////////////multi-server-configuration////////////////


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

