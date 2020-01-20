pipeline {
    environment {
        registry = 'aabdelhady/venus-backend'
        registryCredential = 'docker-hub-credentials'
        dockerImage = ''
        remoteHost = 'root@172.105.69.29'
        composeFileLocation = '/home/docker-compose.yml'
    }

    agent any

    stages {
        stage('Build dist') {
            steps {
                sh 'mvn clean install'
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }
        stage('Build image') {
            steps {
                script {
                   dockerImage = docker.build registry + ":$BUILD_NUMBER"
                }
            }
        }
        stage('Push image') {
            steps {
                script {
                    docker.withRegistry('', registryCredential) {
                        dockerImage.push("${env.BUILD_NUMBER}")
                        dockerImage.push("latest")
                    }
                }
            }
        }
        stage('Docker Compose Down') {
            steps {
                sh 'ssh -t ${remoteHost} "docker-compose -f ${composeFileLocation} down"'
             }
        }
        stage('Clean old local images') {
            steps {
                sh 'docker rmi -f $(docker images aabdelhady/venus-backend -q)'
                sh 'docker rmi -f $(docker images aabdelhady/venus-webapp -q)'
            }
        }
        stage('Copy Scripts') {
            steps {
                sh 'scp docker/docker-compose.yml ${remoteHost}:${composeFileLocation}'
                sh 'scp docker/nginx/nginx.conf ${remoteHost}:/home/nginx/nginx.conf'
             }
        }
        stage('Docker Compose Up') {
            steps {
                sh 'ssh -t ${remoteHost} "docker-compose -f ${composeFileLocation} up -d"'
             }
        }
    }
}