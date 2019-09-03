Vaadin Thorncloak Archetype
===========================

This archetype creates a new Vaadin 14 project with prep of Keycloak- and Thorntail-Integration for Bearer-token based multi-tenancy and for running in a docker container.

    mvn archetype:generate \
      -DarchetypeGroupId=com.urosporo \
      -DarchetypeArtifactId=vaadin-thorncloak-archetype \
      -DarchetypeVersion=1.0.0-SNAPSHOT \
      -DgroupId=com.foo.bar \
      -DartifactId=my-vaadin-project \
      -Dversion=1.0.0-SNAPSHOT \
      -DapplicationName='My application' \
      -DorganizationName='My organization' \
      -Dtenant=aTenant \
      -DwebSecurityRole=user \
      -DkeyCloakUrl=http://localhost:8084/auth


An example with all optional possible properties:

    mvn archetype:generate \
      -DarchetypeGroupId=com.urosporo \
      -DarchetypeArtifactId=vaadin-thorncloak-archetype \
      -DarchetypeVersion=1.0.0-SNAPSHOT \
      -DgroupId=com.foo.bar \
      -Dpackage=com.foo.bar.myapplication \
      -DartifactId=my-vaadin-project \
      -Dversion=1.0.0-SNAPSHOT \
      -DapplicationName='My application' \
      -DorganizationName='My organization' \
      -Dtenant=aTenant \
      -DwebSecurityRole=user \
      -DkeyCloakUrl=http://localhost:8084/auth \
      -DinceptionYear=2019 \
      -DapplicationUrl=https://www.myapplication.com \
      -DorganizationUrl=https://www.myorganization.com \
      -DdeveloperEMail=jon.doe@myorganization.com \
      -DdeveloperId=doj \
      -DdeveloperName='John Doe' \
      -DissueMgmtSystem='Atlassian Jira' \
      -DissueMgmtSystemUrl=https://ticket.myorganization.com/myapplication \
      -DciMgmtSystem='Atlassian Bamboo' \
      -DciMgmtSystemUrl=https://build.myorganization.com/myapplication \
      -DsnapshotRepositoryId=myrepository-snapshot \
      -DsnapshotRepositoryUrl=https://repo.myorganization.com/artifactory/myrepository-snapshot \
      -DreleaseRepositoryId=myrepository-release \
      -DreleaseRepositoryUrl=https://repo.myorganization.com/artifactory/myrepository-release \
      -DscmUrl=https://github.com/myorganization/myapplication \
      -DscmConnection=https://github.com:myorganization/myapplication.git \
      -DscmDeveloperConnection=git@github.com:myorganization/myapplication.git
