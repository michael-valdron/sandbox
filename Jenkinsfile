pipeline {
    agent { docker { image 'golang:1.25.7-alpine3.23' } }
    stages {
        stage('build') {
            steps {
                sh 'echo "building backend" && go build -C go -o sandbox ./...'
                timeout(time: 3, unit: 'MINUTES') {
                    retry(5) {
                        sh 'echo "building frontend" && sleep 5'
                    }
                }
            }
        }
        stage('deploy') {
            steps {
                timeout(time: 3, unit: 'MINUTES') {
                    retry(5) {
                        sh 'echo "Deploying.." && sleep 30'
                    }
                }
            }
        }
    }
    post {
        success {
            echo 'build successful'
        }
        failure {
            echo 'build failed'
        }
    }
}