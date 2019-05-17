pipeline {
  agent any
  stages {
    stage('test') {
      parallel {
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
    stage('') {
      steps {
        recordIssues()
      }
    }
  }
}