#!/usr/bin/env groovy
import groovy.text.StreamingTemplateEngine

def call(sharedLibrary, svcName, buildCommands, pod, slackChannel) {
    if (pod.contains("<%")) {
        pod = new StreamingTemplateEngine().createTemplate(pod).make(['' : '']).toString()
    }
    def podLabel = "${svcName}-${UUID.randomUUID().toString().substring(0,8)}"

    pipeline {
        agent {
            kubernetes {
                label podLabel
                defaultContainer 'jnlp'
                yaml pod
            }
        }
        stages {
            stage('Initialization') {
                steps {
                    script {
                        echo "Starting Initialization stage"
                    }
                }
            }
            stage('Build and Upload Artifact') {
                steps {
                    script {
                        echo "Starting Build and Upload Artifact stage"
                    }
                }
            }
            stage('Deployment') {
                steps {
                    script {
                        echo "Starting Deployment stage"
                    }
                }
            }
        }
    }
}
