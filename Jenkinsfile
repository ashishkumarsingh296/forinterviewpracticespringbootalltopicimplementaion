
pipeline {
    agent any

    parameters {
        choice(name: 'ENV', choices: ['DEV'], description: 'Choose environment to deploy')
    }

    environment {
        IMAGE_NAME = "java-single-env"
        JAR_FILE = "target/*SNAPSHOT.jar"
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/ashishkumarsingh296/forinterviewpracticespringbootalltopicimplementaion.git'
            }
        }

        stage('Build') {
            steps {
                bat "mvn clean package -DskipTests"
            }
        }

        stage('Build Docker Image') {
            steps {
                bat """
              docker build -t java-single-env:latest .

                """
            }
        }

  


         stage('Deploy DEV') {
           when { expression { params.ENV == 'DEV' || params.ENV == 'BOTH' } }
         steps {
          bat """
          cd %WORKSPACE%
          docker-compose down
          docker-compose up -d --build
         """
          }
        }

        // stage('Deploy QA') {
        //     when { expression { params.ENV == 'QA' || params.ENV == 'BOTH' } }
        //     steps {
        //         bat """
        //         cd %WORKSPACE%
        //         docker-compose down
        //         docker-compose -f docker-compose.qa.yml up -d
        //         """
        //     }
        // }

    }

    post {
        success {
            echo "Deployment Successful!"
        }
        failure {
            echo "Deployment Failed!"
        }
    }
}




////////////////////////multi-server-configuration////////////////


pipeline {
    agent any

    parameters {
        choice(name: 'ENV', choices: ['DEV', 'QA', 'BOTH'], description: 'Choose environment to deploy')
    }

    environment {
        IMAGE_NAME = "java-multi-env"
        VERSION = "1.0.${BUILD_NUMBER}"
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/ashishkumarsingh296/forinterviewpracticespringbootalltopicimplementaion.git'
            }
        }
        

        stage('Build') {
            steps {
                bat "mvn clean package -DskipTests"
            }
        }

        stage('Build Docker Image') {
            steps {
                bat """
                    docker build -t ${IMAGE_NAME}:${VERSION} .
                """
            }
        }
        stage('Debug Workspace') {
    steps {
        bat "dir"
        bat "type docker-compose.yml"
    }
}

        stage('Deploy') {
            steps {
                script {
                    if (params.ENV == 'DEV') {
                        bat "docker-compose up -d --build app-dev db-dev load-balancer"
                    } else if (params.ENV == 'QA') {
                        bat "docker-compose up -d --build app-qa db-qa load-balancer"
                    } else {
                        bat "docker-compose up -d --build"
                    }
                }
            }
        }
    }

    post {
        success {
            echo "Deployment Successful!"
        }
        failure {
            echo "Deployment Failed!"
        }
    }
}


