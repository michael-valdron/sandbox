pipeline {
    agent any
    stages {
        stage('build') {
            when {
                branch 'master'
            }
            failFast true
            parallel {
                stage('build /go') {
                    agent { 
                        docker { 
                            image 'golang:1.25.7-alpine3.23'
                            args '-u root:root'
                        } 
                    }

                    environment {
                        GO111MODULE = 'on'
                        CGO_ENABLED = '0'
                    }

                    steps {
                        timeout(time: 3, unit: 'MINUTES') {
                            retry(5) {
                                sh 'go build -C go -o sandbox ./...'
                            }
                        }

                    }
                }
                stage('build /learn-java') {
                    agent {
                        docker {
                            image 'gradle:8.14-jdk11-alpine'
                            args '-u root:root'
                        }
                    }

                    environment {
                        GRADLE_USER_HOME = '$HOME/.gradle'
                    }

                    steps {
                        timeout(time: 3, unit: 'MINUTES') {
                            retry(5) {
                                dir('learn-java') {
                                    sh 'gradle build'
                                }
                            }
                        }

                    }
                }
                stage('build /learn-haxe') {
                    agent {
                        docker {
                            image 'haxe:5.0.0'
                            args '-u root:root'
                        }
                    }

                    steps {
                        timeout(time: 3, unit: 'MINUTES') {
                            retry(5) {
                                dir('learn-haxe') {
                                    sh 'haxe --main Sandbox --js Sandbox.js'
                                    sh 'haxe --main Sandbox --jvm Sandbox.jar'
                                    sh 'haxe --main Sandbox --interp'
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    post {
        success {
            echo 'builds successful'
        }
        failure {
            echo 'builds failed'
        }
    }
}