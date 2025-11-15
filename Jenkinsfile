// pipeline {
//     agent any

// parameters {
//         string(name: 'PROFILE', defaultValue: 'wsl', description: 'Spring profile to use')
//         string(name: 'PORT1', defaultValue: '8081', description: 'Port for instance 1')
//         string(name: 'PORT2', defaultValue: '8082', description: 'Port for instance 2')
//     }

//     environment {
//         JAR_NAME = 'forinterviewpracticespringbootalltopicimplementaion-0.0.1-SNAPSHOT.jar'
//         WIN_SHARE = 'C:\\springboot-app'
//         WSL_APP1 = '/home/aashu/Kems/app1.jar'
//         WSL_APP2 = '/home/aashu/Kems/app2.jar'
//         LOG_DIR = '/home/aashu/Kems/logs'
//         BACKUP_DIR = '/home/aashu/Kems/backups'
//         MAX_BACKUPS = 5
//     }

//     stages {
//         stage('Checkout') {
//             steps {
//                 echo "Pulling latest code..."
//                 git branch: 'main', url: 'https://github.com/ashishkumarsingh296/forinterviewpracticespringbootalltopicimplementaion.git'
//             }
//         }

//         stage('Build JAR') {
//             steps {
//                 echo "Building Spring Boot JAR..."
//                 bat 'mvn clean package -DskipTests'
//             }
//         }

//        stage('Copy JAR & Script to Windows Shared Folder') {
//     steps {
//         echo "Copying JAR & deploy script to C:\\springboot-app..."

//         bat """
//               if not exist ${WIN_SHARE} mkdir ${WIN_SHARE}

//                 echo Copying JAR...
//                 copy /Y target\\${JAR_NAME} ${WIN_SHARE}\\

//                  echo Copying deploy-wsl-multi.sh...
//                 copy /Y scripts\\deploy-wsl-multi.sh ${WIN_SHARE}\\

//                 echo Listing files...
//                 dir ${WIN_SHARE}
//                 """
//     }
// }


//         stage('Fix Line Endings in WSL') {
//             steps {
//                 bat """
//                     echo Converting script to Unix format inside WSL...
//                     wsl dos2unix /mnt/c/springboot-app/deploy-wsl-multi.sh
//                 """            
//             }
//         }

//         stage('Pre-Deployment Checks') {
//             steps {
//                 echo "Checking disk space and ports before deployment..."
//                 bat """
//                     wsl df -h
//                     wsl lsof -i :%PORT1%
//                     wsl lsof -i :%PORT2%
//                 """
//             }
//         }


//         stage('Backup Existing JARs') {
//     steps {
//         echo "Backing up existing JARs with timestamp..."
//         bat """
//         wsl bash -c '
//             mkdir -p ${BACKUP_DIR}
//             TIMESTAMP=\\\$(date +%Y%m%d%H%M%S)

//             cp ${WSL_APP1} ${BACKUP_DIR}/app1-\\\$TIMESTAMP.jar || true
//             cp ${WSL_APP2} ${BACKUP_DIR}/app2-\\\$TIMESTAMP.jar || true

//             # Keep only last ${MAX_BACKUPS} backups
//             ls -1t ${BACKUP_DIR}/app1-*.jar | tail -n +\\\$((MAX_BACKUPS + 1)) | xargs -r rm -f
//             ls -1t ${BACKUP_DIR}/app2-*.jar | tail -n +\\\$((MAX_BACKUPS + 1)) | xargs -r rm -f

//             echo "Backups after cleanup:"
//             ls -l ${BACKUP_DIR}
//         '
//         """
//     }
// }


//         stage('Deploy on WSL') {
//             steps {
//                 bat """
//                     wsl chmod +x /mnt/c/springboot-app/deploy-wsl-multi.sh
//                     wsl /mnt/c/springboot-app/deploy-wsl-multi.sh ${JAR_NAME} wsl 8081 8082
//                 """
//             }
//         }


//     stage('Post Deployment Check (from WSL)') {
//     steps {
//         echo "Checking health for both instances (8081 & 8082)..."

//         bat """
// wsl curl -sSf http://127.0.0.1:8081/actuator/health && echo 8081 OK || echo 8081 FAILED
// wsl curl -sSf http://127.0.0.1:8082/actuator/health && echo 8082 OK || echo 8082 FAILED
// """
//     }
// }
//     }

// post {
//     failure {
//         echo "Restoring last backup due to failure..."
//         bat """
//         wsl bash -c '
//             set -e
//             LATEST1=\\\$(ls -1t ${BACKUP_DIR}/app1-*.jar | head -n 1)
//             LATEST2=\\\$(ls -1t ${BACKUP_DIR}/app2-*.jar | head -n 1)

//             [ -f "\\\$LATEST1" ] && cp "\\\$LATEST1" ${WSL_APP1} && echo "Restored \\\$LATEST1"
//             [ -f "\\\$LATEST2" ] && cp "\\\$LATEST2" ${WSL_APP2} && echo "Restored \\\$LATEST2"
//         '
//         """
//     }
// }
    
// }



pipeline {
    agent any

    parameters {
        string(name: 'PROFILE', defaultValue: 'wsl', description: 'Spring profile to use')
        string(name: 'PORT1', defaultValue: '8081', description: 'Port for instance 1')
        string(name: 'PORT2', defaultValue: '8082', description: 'Port for instance 2')
    }

    environment {
        JAR_NAME = 'forinterviewpracticespringbootalltopicimplementaion-0.0.1-SNAPSHOT.jar'
        WIN_SHARE = 'C:\\springboot-app'
        WSL_APP1 = '/home/aashu/Kems/app1.jar'
        WSL_APP2 = '/home/aashu/Kems/app2.jar'
        LOG_DIR = '/home/aashu/Kems/logs'
        BACKUP_DIR = '/home/aashu/Kems/backups'
        MAX_BACKUPS = 5
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

        stage('Copy JAR & Script to Windows Folder') {
            steps {
                echo "Copying JAR & deploy script to C:\\springboot-app..."
                bat """
                    if not exist ${WIN_SHARE} mkdir ${WIN_SHARE}
                    copy /Y target\\${JAR_NAME} ${WIN_SHARE}\\
                    copy /Y scripts\\deploy-wsl-multi.sh ${WIN_SHARE}\\
                    dir ${WIN_SHARE}
                """
            }
        }

        stage('Fix Line Endings in WSL') {
            steps {
                echo "Converting script to Unix format..."
                bat """
                    wsl dos2unix /mnt/c/springboot-app/deploy-wsl-multi.sh
                """
            }
        }

        stage('Pre-Deployment Checks') {
            steps {
                echo "Checking disk space and ports..."
                bat """
                    wsl df -h
                    wsl lsof -i :%PORT1%
                    wsl lsof -i :%PORT2%
                """
            }
        }

        stage('Backup Existing JARs') {
            steps {
                echo "Backing up existing JARs..."
                bat """
                wsl bash -c '
                    set -e
                    mkdir -p ${BACKUP_DIR}
                    TIMESTAMP=\\\$(date +%Y%m%d%H%M%S)

                    cp ${WSL_APP1} ${BACKUP_DIR}/app1-\\\$TIMESTAMP.jar || true
                    cp ${WSL_APP2} ${BACKUP_DIR}/app2-\\\$TIMESTAMP.jar || true

                    # Keep only last ${MAX_BACKUPS} backups
                    ls -1t ${BACKUP_DIR}/app1-*.jar | tail -n +\\\$((MAX_BACKUPS + 1)) | xargs -r rm -f
                    ls -1t ${BACKUP_DIR}/app2-*.jar | tail -n +\\\$((MAX_BACKUPS + 1)) | xargs -r rm -f

                    ls -l ${BACKUP_DIR}
                '
                """
            }
        }

        stage('Deploy on WSL') {
            steps {
                echo "Deploying on WSL..."
                bat """
                    wsl chmod +x /mnt/c/springboot-app/deploy-wsl-multi.sh
                    wsl /mnt/c/springboot-app/deploy-wsl-multi.sh ${JAR_NAME} wsl %PORT1% %PORT2%
                """
            }
        }

        stage('Post Deployment Health Check') {
            steps {
                echo "Checking health of both instances..."
                bat """
                    wsl curl -sSf http://127.0.0.1:%PORT1%/actuator/health && echo %PORT1% OK || echo %PORT1% FAILED
                    wsl curl -sSf http://127.0.0.1:%PORT2%/actuator/health && echo %PORT2% OK || echo %PORT2% FAILED
                """
            }
        }
    }

    post {
        success {
            echo "🚀 Deployment Success: App running on %PORT1% & %PORT2% via Nginx Load Balancer"
        }

        failure {
            echo "❌ Deployment failed. Restoring last backups..."
            bat """
            wsl bash -c '
                set -e

                # Restore latest backup if exists
                LATEST1=\\\$(ls -1t ${BACKUP_DIR}/app1-*.jar | head -n 1)
                LATEST2=\\\$(ls -1t ${BACKUP_DIR}/app2-*.jar | head -n 1)

                if [ -f "\\\$LATEST1" ]; then
                    cp "\\\$LATEST1" ${WSL_APP1}
                    echo "Restored \\\$LATEST1 -> ${WSL_APP1}"
                fi

                if [ -f "\\\$LATEST2" ]; then
                    cp "\\\$LATEST2" ${WSL_APP2}
                    echo "Restored \\\$LATEST2 -> ${WSL_APP2}"
                fi
            '
            """
        }
    }
}

