pipeline {
    agent any

    parameters {
        choice(name: 'ENV', choices: ['DEV', 'QA', 'BOTH'], description: 'Choose environment to deploy')
    }

    environment {
        IMAGE_NAME = "java-multi-env"
        VERSION = "${env.BUILD_NUMBER}"  // Jenkins build number
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
                // docker build -t java-multi-env:latest .
                docker build -t ${IMAGE_NAME}:${VERSION} .
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

        stage('Deploy QA') {
            when { expression { params.ENV == 'QA' || params.ENV == 'BOTH' } }
            steps {
                bat """
                cd %WORKSPACE%
                docker-compose down
                docker-compose -f docker-compose.qa.yml up -d
                """
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
