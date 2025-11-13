pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/ashishkumarsingh296/forinterviewpracticespringbootalltopicimplementaion.git'
            }
        }

        stage('Build') {
            steps {
                // For Windows Jenkins
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Deploy to WSL') {
            steps {
                echo 'Deploying to WSL...'
                // Optional: WSL-specific deployment command (example below)
                bat 'copy target\\forinterviewpracticespringbootalltopicimplementaion-0.0.1-SNAPSHOT.jar C:\\springboot-app\\'

            }
        }
    }

    post {
        failure {
            echo 'Deployment failed.'
        }
    }
}
