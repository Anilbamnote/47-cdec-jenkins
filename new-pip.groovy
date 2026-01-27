pipeline {
    agent { label 'slave' }
    stages {
        stage('git-pull-stage') {
            steps {
                git branch: 'main', url: 'https://github.com/Anilbamnote/student-ui-app.git'
            }
        }
         stage('build-stage') {
            steps {
                sh '/opt/maven/bin/mvn clean package'
            }
        }
         stage('tesr-stage') {
            steps {
                 withSonarQubeEnv(installationName: 'sonar',credentialsId: 'sonar_cred1') {
                      sh '/opt/maven/bin/mvn sonar:sonar'
                  }


                sh '''mvn sonar:sonar \\
                      -Dsonar.projectKey=Studentapp \\
                      -Dsonar.host.url=http://98.92.190.131:9000 \\
                    -Dsonar.login=5d84033aca2f16cb47b430984fae6aeb668c6542'''
            }
        }
        //  stage('Quality_Gate') {
        //     steps {
        //         timeout(10) {
   
        //     }
        //         waitForQualityGate true
        //     }
        // }

         stage('deploy-stage') {
            steps {
                echo 'code deploy sucessfully'
            }
        }
    }
}
