pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                sh './gradlew build'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t shop:latest .'
            }
        }
    }

    post {
        success {
            echo 'Pipeline erfolgreich!'
        }
        failure {
            echo 'Pipeline fehlgeschlagen!'
        }
    }
}
