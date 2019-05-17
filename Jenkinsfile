pipeline {
  agent any
  stages {
    stage('test') {
      steps {
        echo 'test step'
      }
    }
    stage('error') {
      steps {
        recordIssues()
      }
    }
  }
}