//Final One
pipeline {
    agent any

    parameters {
        choice(name: 'ENVIRONMENT', choices: ['WSL_PROD','LOCAL'], description: "Choose environment")
        choice(name: 'RESTART_APP', choices: ['YES','NO'], description: "Restart application after deployment")
    }

    environment {
        // WSL native paths
        WSL_PROJECT="/home/aashudev/spring-app"        // WSL workspace for build
        WSL_DEPLOY="/home/aashudev/deploy"            // Deploy folder
        JENKINS_SCRIPTS="/home/aashudev/jenkins_scripts"
        ARTIFACT_NAME="spring-app.jar"
        BUILD_SCRIPT="build_${params.ENVIRONMENT}.sh"
        DEPLOY_SCRIPT="deploy_${params.ENVIRONMENT}.sh"
        GIT_URL="https://github.com/ashishkumarsingh296/forinterviewpracticespringbootalltopicimplementaion.git"
        GIT_BRANCH="main"
    }

    stages {

        // stage('Prepare WSL Workspace') {
        //     steps {
        //         bat """
        //         wsl mkdir -p ${WSL_PROJECT} ${JENKINS_SCRIPTS} ${WSL_DEPLOY}
        //         wsl dos2unix ${JENKINS_SCRIPTS}/*.sh || true
        //         wsl chmod +x ${JENKINS_SCRIPTS}/*.sh || true
        //         """
        //     }
        // }

        stage('Prepare WSL Workspace') {
    steps {
        bat """
        :: Create WSL directories if not exist
        wsl mkdir -p /home/aashudev/spring-app /home/aashudev/jenkins_scripts /home/aashudev/deploy

        :: Check if jenkins_scripts is empty in WSL
        wsl bash -c "if [ \$(ls -A /home/aashudev/jenkins_scripts | wc -l) -eq 0 ]; then \
            echo 'Copying Jenkins scripts from Windows workspace to WSL...'; \
            cp /mnt/c/ProgramData/Jenkins/.jenkins/workspace/InterviewAllVersion/jenkins_scripts/*.sh /home/aashudev/jenkins_scripts/; \
        else \
            echo 'jenkins_scripts already exist in WSL'; \
        fi"

        :: Convert line endings & make scripts executable
        wsl bash -c "dos2unix /home/aashudev/jenkins_scripts/*.sh || true"
        wsl bash -c "chmod +x /home/aashudev/jenkins_scripts/*.sh"
        """
    }
}

        stage('Checkout Code in WSL') {
            steps {
                bat """
                wsl bash -c "if [ -d ${WSL_PROJECT}/.git ]; then git -C ${WSL_PROJECT} reset --hard && git -C ${WSL_PROJECT} pull; else git clone -b ${GIT_URL} ${WSL_PROJECT}; fi"
                """
            }
        }

        stage('Build JAR in WSL') {
            steps {
                bat """
                wsl bash -c "cd ${WSL_PROJECT} && ./mvnw clean package -DskipTests"
                """
            }
        }

        stage('Copy Artifact to Deploy Folder') {
            steps {
                bat """
                wsl bash -c "cp ${WSL_PROJECT}/target/*.jar ${WSL_DEPLOY}/${ARTIFACT_NAME}"
                """
            }
        }

        stage('Deploy Application') {
            when {
                expression { params.RESTART_APP == 'YES' }
            }
            steps {
                bat """
                wsl bash -c "${JENKINS_SCRIPTS}/${DEPLOY_SCRIPT}"
                """
            }
        }

        stage('Check Logs') {
            steps {
                bat """
                wsl bash -c "tail -n 200 ${WSL_DEPLOY}/app.log || echo 'No logs found'"
                """
            }
        }

        stage('Health Check') {
            steps {
                bat """
                wsl bash -c "curl -f http://localhost:8080/actuator/health || echo 'Application not reachable!'"
                """
            }
        }
    }

    post {
        success {
            echo "WSL Deployment completed successfully!"
        }
        failure {
            echo "Deployment failed! Check logs."
        }
    }
}








// pipeline {
//     agent any

//     environment {
//         WSL_PROJECT="/home/ashishdev/project"
//         DOCKER_IMAGE="myapp"
//     }

//     stages {

//         stage('Checkout Code') {
//             steps {
//                 git branch: 'main', url: 'https://github.com/ashishkumarsingh296/forinterviewpracticespringbootalltopicimplementaion.git'
//             }
//         }

//       stage('Copy to WSL Workspace') {
//     steps {
//         bat """
//         wsl cp -r /mnt/c/ProgramData/Jenkins/.jenkins/workspace/InterviewAllVersion/* $WSL_PROJECT/
//         """
//     }
// }


//         stage('Build JAR & Docker Image in WSL') {
//             steps {
//                 bat """
//                 wsl bash -c "cd $WSL_PROJECT && ./mvnw clean package -DskipTests"
//                 wsl bash -c "cd $WSL_PROJECT && docker-compose build --no-cache"
//                 """
//             }
//         }

//         stage('Deploy Application') {
//             steps {
//                 bat """
//                 wsl bash -c "cd $WSL_PROJECT && docker-compose up -d"
//                 """
//             }
//         }

//         stage('Auto-Scaling') {
//             steps {
//                 bat """
//                 wsl bash -c "cd $WSL_PROJECT/scripts && ./deploy.sh"
//                 """
//             }
//         }

//         stage('Health Check') {
//             steps {
//                 bat """
//                 wsl bash -c "curl -f http://localhost || echo 'App not reachable!'"
//                 """
//             }
//         }
//     }

//     post {
//         success {
//             echo "Deployment completed successfully!"
//         }
//         failure {
//             echo "Deployment failed. Check logs for details."
//             // Optional: configure SMTP if you want mail notifications
//         }
//     }
// }



// pipeline {
//     agent any
//
//     environment {
//         // WSL paths
//         WSL_PROJECT="/home/aashudev/spring-app"
//         WSL_PROJECT_RESTART="/home/aashudev/deploy"
//     }
//
//     stages {
//         stage('Checkout Code') {
//             steps {
//                 git branch: 'main', url: 'https://github.com/ashishkumarsingh296/forinterviewpracticespringbootalltopicimplementaion.git'
//             }
//         }
//
//         stage('Copy Code to WSL') {
//             steps {
//                 bat """
//                 wsl mkdir -p $WSL_PROJECT
//                 wsl rsync -av /mnt/c/ProgramData/Jenkins/.jenkins/workspace/InterviewAllVersion/ $WSL_PROJECT/
//                 wsl chmod +x $WSL_PROJECT/mvnw
//                 """
//             }
//         }
//
//         stage('Build JAR') {
//             steps {
//                 bat """
//                 wsl bash -c "cd $WSL_PROJECT && ./mvnw clean package -DskipTests"
//                 """
//             }
//         }
//
//         stage('Copy JAR to Deploy Folder') {
//             steps {
//                 bat """
//                 wsl bash -c "cp $WSL_PROJECT/target/*.jar $WSL_PROJECT_RESTART/"
//                 """
//             }
//         }
//
//         stage('Deploy Application in WSL') {
//             steps {
//                 bat """
//                 wsl bash -c "cd $WSL_PROJECT_RESTART && ./restart.sh wsl"
//                 """
//             }
//         }
//
//         stage('Check Logs') {
//             steps {
//                 bat """
//                 wsl bash -c "ls -l $WSL_PROJECT_RESTART && tail -n 200 $WSL_PROJECT_RESTART/app.log"
//                 """
//             }
//         }
//
//         stage('Health Check') {
//             steps {
//                 bat """
//                 wsl bash -c "curl -f http://localhost:8080/actuator/health || echo 'Application not reachable!'"
//                 """
//             }
//         }
//     }
//
//     post {
//         success {
//             echo "Deployment completed successfully!"
//         }
//         failure {
//             echo "Deployment failed. Check logs for details."
//         }
//     }
// }
