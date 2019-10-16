pipeline {
    environment {
        registry = 'aabdelhady/venus-backend'
        registryCredential = 'docker-hub-credentials'
        dockerImage = ''
        remoteHost = 'root@172.105.69.29'
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
            steps{
                script {
                   dockerImage = docker.build registry + ":$BUILD_NUMBER"
                }
            }
        }
        stage('Push image') {
            steps{
                script {
                    docker.withRegistry('', registryCredential) {
                        dockerImage.push("${env.BUILD_NUMBER}")
                        dockerImage.push("latest")
                    }
                }
            }
        }
    }
}