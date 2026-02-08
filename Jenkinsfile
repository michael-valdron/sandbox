pipeline {
    agent any
    stages {
        stage('build') {
            steps {
                echo 'building backend'
                timeout(time: 3, unit: 'MINUTES') {
                    retry(5) {
                        sh 'echo "building frontend" && sleep 5'
                    }
                }
            }
        }
        stage('deploy') {
            steps {
                echo 'Deploying..'
                timeout(time: 3, unit: 'MINUTES') {
                    retry(5) {
                        sh 'sleep 30 && exit $(($RANDOM % 2))'
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