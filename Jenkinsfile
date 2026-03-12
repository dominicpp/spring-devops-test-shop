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

        stage('Deploy to Minikube') {
            steps {
                sh '''
                    cp /root/.kube/config /tmp/kubeconfig
                    sed -i 's|server: https://.*|server: https://192.168.49.2:8443|' /tmp/kubeconfig
                    sed -i 's|/Users/dominic.brien/.minikube/|/root/.minikube/|g' /tmp/kubeconfig
                    export KUBECONFIG=/tmp/kubeconfig
                    docker save shop:latest | docker exec -i minikube docker load
                    kubectl apply -f k8s/
                    kubectl rollout restart deployment/shop
                    kubectl rollout status deployment/shop --timeout=120s
                '''
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
