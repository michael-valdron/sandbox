pipeline {
    agent any
    stages {
        stage('build') {
            steps {
                echo 'building backend'
                timeout(time: 3, unit: 'MINUTES') {
                    retry(5) {
                        sh 'echo "building frontend" && sleep 5 && exit 1'
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