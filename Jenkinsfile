pipeline {
    agent any

    parameters {
        choice(name: 'ENVIRONMENT', choices: ['DEV','QA','DQA','ORACLE'], description: "Choose environment for local WSL deployment")
        choice(name: 'RESTART_SERVER', choices: ['NO','YES'], description: "Restart Tomcat required")
        string(name: 'PROFILE', defaultValue: 'wsl', description: 'Spring profile to use')
    }

    environment {
        WORKSPACE_WSL = '/home/aashu/Kems'
        DEV_TOMCAT_HOME = "${WORKSPACE_WSL}/tomcat_dev"
        QA_TOMCAT_HOME = "${WORKSPACE_WSL}/tomcat_qa"
        DQA_TOMCAT_HOME = "${WORKSPACE_WSL}/tomcat_dqa"
        ORACLE_TOMCAT_HOME = "${WORKSPACE_WSL}/tomcat_oracle"
        BACKUP_DIR = "${WORKSPACE_WSL}/backups"
        MAX_BACKUPS = 5
        JAR_NAME = 'forinterviewpracticespringbootalltopicimplementaion-0.0.1-SNAPSHOT.jar'
        LOG_DIR = "${WORKSPACE_WSL}/logs"
    }

    stages {

        stage('Checkout') {
            steps {
                echo "Pulling latest code..."
                git branch: 'main', url: 'https://github.com/ashishkumarsingh296/forinterviewpracticespringbootalltopicimplementaion.git'
            }
        }

        stage('Build JAR') {
            steps {
                echo "Building Spring Boot JAR..."
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Select Target Environment') {
            steps {
                script {
                    if (params.ENVIRONMENT == 'DEV') {
                        env.TARGET_DIR = env.DEV_TOMCAT_HOME
                        env.TARGET_PORT = '8081'
                    } else if (params.ENVIRONMENT == 'QA') {
                        env.TARGET_DIR = env.QA_TOMCAT_HOME
                        env.TARGET_PORT = '8082'
                    } else if (params.ENVIRONMENT == 'DQA') {
                        env.TARGET_DIR = env.DQA_TOMCAT_HOME
                        env.TARGET_PORT = '8083'
                    } else if (params.ENVIRONMENT == 'ORACLE') {
                        env.TARGET_DIR = env.ORACLE_TOMCAT_HOME
                        env.TARGET_PORT = '8084'
                    }
                    echo "Deploying to ${env.TARGET_DIR} on port ${env.TARGET_PORT}"
                }
            }
        }

        stage('Backup Existing JAR') {
            steps {
                echo "Backing up existing JARs for ${params.ENVIRONMENT}..."
                bat """
wsl bash -c '
mkdir -p ${BACKUP_DIR}
TIMESTAMP=\\\$(date +%Y%m%d%H%M%S)
if [ -f ${TARGET_DIR}/webapps/app.war ]; then
    cp ${TARGET_DIR}/webapps/app.war ${BACKUP_DIR}/app-${params.ENVIRONMENT}-\$TIMESTAMP.war
fi
ls -1t ${BACKUP_DIR}/app-${params.ENVIRONMENT}-*.war | tail -n +\$((MAX_BACKUPS + 1)) | xargs -r rm -f
'
"""
            }
        }

        stage('Deploy Application') {
            steps {
                echo "Deploying JAR to ${params.ENVIRONMENT}..."
                bat """
wsl bash -c '
mkdir -p ${TARGET_DIR}/webapps
cp ${WORKSPACE}/target/${JAR_NAME} ${TARGET_DIR}/webapps/app.war
'
"""
            }
        }

        stage('Restart Server') {
            when {
                expression { params.RESTART_SERVER == 'YES' }
            }
            steps {
                echo "Restarting server for ${params.ENVIRONMENT}..."
                bat """
wsl bash -c '
cd ${TARGET_DIR}/bin
if [ -f shutdown.sh ]; then ./shutdown.sh; fi
sleep 5
if [ -f startup.sh ]; then ./startup.sh; fi
'
"""
            }
        }

        stage('Health Check') {
            steps {
                echo "Checking health for ${params.ENVIRONMENT}..."
                bat """
wsl curl -sSf http://127.0.0.1:${TARGET_PORT}/actuator/health && echo OK || echo FAILED
"""
            }
        }
    }

    post {
        success {
            echo "🚀 Deployment Success: ${params.ENVIRONMENT} is running on port ${TARGET_PORT}"
        }
        failure {
            echo "❌ Deployment failed. Restoring last backup for ${params.ENVIRONMENT}..."
            bat """
wsl bash -c '
LATEST=\\\$(ls -1t ${BACKUP_DIR}/app-${params.ENVIRONMENT}-*.war | head -n 1)
if [ -f "\\\$LATEST" ]; then
    cp "\\\$LATEST" ${TARGET_DIR}/webapps/app.war
fi
'
"""
        }
    }
}
