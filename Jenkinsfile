pipeline {
    agent any

    environment {
        DOCKER_HOST="tcp://localhost:2375"
    }

    stages {
        stage('Build') {
            steps {
                bat './mvnw clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t myapp:latest .'
            }
        }

        stage('Deploy to WSL') {
            steps {
                bat 'docker-compose -f docker-compose.yml up -d'
            }
        }

        stage('Trigger Auto-Scaling') {
            steps {
                bat 'bash scripts/deploy.sh'
            }
        }

        stage('Health Check') {
            steps {
                bat 'curl -f http://localhost || echo "App not reachable!"'
            }
        }
    }

    post {
        failure {
            mail to: 'ashishkumarsingh296@gmail.com',
                 subject: "Deployment Failed",
                 body: "Check Jenkins logs"
        }
    }
}
