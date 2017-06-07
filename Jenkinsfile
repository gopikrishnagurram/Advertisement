node {
    stage('Configure') {
     echo 'configure started'
     checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '3c2f6f6b-04b5-4da0-87cd-0f3f744d5014', url: 'https://gitlab.com/nbostech/AdvertisementSpringBoot.git']]])
     echo 'configure done'
  }
  
  stage('SonarScanner') {
      if (isUnix()) {
          echo 'its an unix enviranment'
         sh "${tool 'SonarScanner'}/soner-runner.bat"
      } else {
          echo 'its an windows environment'
          //bat "${tool 'Sonar'}\\bin\\StartSonar.bat"
          bat "${tool 'SonarScanner'}\\bin\\sonar-runner.bat -Dsonar.projectKey=GitLab-AdvertisementsSpringBoot -Dsonar.projectName=GitLab-AdvertisementsSpringBoot -Dsonar.projectVersion=1.0 -Dsonar.sources=C:\\Users\\tharunkumarb\\.jenkins\\workspace\\GitLab-AdvertisementsSpringBoot"
      }
  }
  
  stage('JUnit tests') {
     // use the id of the globally configured maven instance
    // execute maven
     // Run the maven build
    if (isUnix()) {
         echo 'its an unix enviranment'
         sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
        }else {
         echo 'its an windows environment'
         bat "${tool 'M3'}/bin/mvn test"
         junit '**\\target\\surefire-reports\\*.xml'
    }
  }

         
    stage('Code coverage') {
     // use the id of the globally configured maven instance
    // execute maven
     // Run the maven build
      if (isUnix()) {
          echo 'its an unix enviranment'
         sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
      } else {
          echo 'its an windows environment'
         bat "${tool 'M3'}/bin/mvn cobertura:cobertura"
         publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, keepAll: false, reportDir: 'target/site/cobertura', reportFiles: 'index.html', reportName: 'Coverage Report', reportTitles: 'Code coverage'])
     }
    }

   stage('Deployemnt stage') {
   echo 'deployment successfull'
  }
    
}