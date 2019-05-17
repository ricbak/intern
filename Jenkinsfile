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
        stage('') {
          steps {
            recordIssues()
          }
        }
      }
    }
  }
}