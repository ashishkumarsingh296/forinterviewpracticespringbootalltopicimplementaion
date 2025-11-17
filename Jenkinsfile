pipeline {
    agent any

    environment {
        WSL_PROJECT="/home/ashishdev/project"
        WINDOWS_PROJECT="C:\\ProgramData\\Jenkins\\.jenkins\\workspace" // adjust if needed
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
                bat"""
                wsl cp -r /mnt/c/Users/jenkins/workspace/forinterviewpracticespringbootalltopicimplementaion/* $WSL_PROJECT/
                """
            }
        }

        stage('Build JAR & Docker Image in WSL') {
            steps {
                bat """
                wsl bash -c "cd $WSL_PROJECT && ./mvnw clean package -DskipTests"
                wsl bash -c "cd $WSL_PROJECT && docker-compose build --no-cache"
                """
            }
        }

        stage('Deploy Application') {
            steps {
                bat """
                wsl bash -c "cd $WSL_PROJECT && docker-compose up -d"
                """
            }
        }

        stage('Auto-Scaling') {
            steps {
                bat """
                wsl bash -c "cd $WSL_PROJECT/scripts && ./deploy.sh"
                """
            }
        }

        stage('Health Check') {
            steps {
                bat """
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
            mail to: 'ashishkumarsingh296@gmail.com',
                 subject: "Deployment Failed",
                 body: "Check Jenkins logs for details."
        }
    }
}
