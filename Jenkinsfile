pipeline {
    agent any

    environment {
        WSL_PROJECT="/home/username/project"  // WSL workspace
        WINDOWS_PROJECT="C:\\Users\\jenkins\\workspace\\forinterviewpracticespringbootalltopicimplementaion" // adjust if needed
        DOCKER_IMAGE="myapp"
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/ashishkumarsingh296/forinterviewpracticespringbootalltopicimplementaion.git'
            }
        }

        stage('Copy to WSL Workspace') {
            steps {
                sh """
                wsl cp -r /mnt/c/Users/jenkins/workspace/forinterviewpracticespringbootalltopicimplementaion/* $WSL_PROJECT/
                """
            }
        }

        stage('Build JAR & Docker Image in WSL') {
            steps {
                sh """
                wsl bash -c "cd $WSL_PROJECT && ./mvnw clean package -DskipTests"
                wsl bash -c "cd $WSL_PROJECT && docker-compose build --no-cache"
                """
            }
        }

        stage('Deploy Application') {
            steps {
                sh """
                wsl bash -c "cd $WSL_PROJECT && docker-compose up -d"
                """
            }
        }

        stage('Auto-Scaling') {
            steps {
                sh """
                wsl bash -c "cd $WSL_PROJECT/scripts && ./deploy.sh"
                """
            }
        }

        stage('Health Check') {
            steps {
                sh """
                wsl bash -c "curl -f http://localhost || echo 'App not reachable!'"
                """
            }
        }
    }

    post {
        success {
            echo "Deployment completed successfully!"
        }
        failure {
            mail to: 'devops@company.com',
                 subject: "Deployment Failed",
                 body: "Check Jenkins logs for details."
        }
    }
}
