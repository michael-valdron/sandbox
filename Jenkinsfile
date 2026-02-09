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
                            image 'docker.io/library/golang:1.25.7-alpine3.23@sha256:f6751d823c26342f9506c03797d2527668d095b0a15f1862cddb4d927a7a4ced'
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
                            image 'docker.io/library/gradle:8.14-jdk11-alpine@sha256:44918264f5a5a765d1b90e7513450e204c14ce2576c9df38ef0850b772b190c1'
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
                            image 'docker.io/library/haxe:5.0.0@sha256:06ffb4b1e0fb50b064e1117c963b408099a211f2068091499e5d993b1c539fea'
                            args '-u root:root'
                        }
                    }

                    steps {
                        timeout(time: 3, unit: 'MINUTES') {
                            retry(5) {
                                dir('learn-haxe') {
                                    sh 'haxe --main Sandbox --js Sandbox.js'
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