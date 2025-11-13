pipeline {
    agent any

    environment {
        APP_DIR = '/home/aashu/Kems'
        PROFILE = 'wsl'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/ashishkumarsingh296/forinterviewpracticespringbootalltopicimplementaion.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Deploy to WSL') {
            steps {
                sh './scripts/deploy.sh'
            }
        }
    }

    post {
        success {
            echo 'Deployment to WSL completed successfully!'
        }
        failure {
            echo 'Deployment failed.'
        }
    }
}
