/* Requires the Docker Pipeline plugin */
pipeline {
    agent { docker { image 'golang:1.25.7-alpine3.23' } }
    stages {
        stage('build') {
            steps {
                sh 'go version'
            }
        }
    }
}