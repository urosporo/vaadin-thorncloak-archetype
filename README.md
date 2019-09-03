Vaadin Thorncloak Archetype
===========================

This archetype creates a new Vaadin 14 project with prep of Keycloak- and Thorntail-Integration for Bearer-token based multi-tenancy and for running in a docker container.

    mvn archetype:generate \
      -DarchetypeGroupId=com.urosporo \
      -DarchetypeArtifactId=vaadin-thorncloak-archetype \
      -DarchetypeVersion=1.0.0-SNAPSHOT \
      -DgroupId=com.foo.bar \
      -Dpackage=com.foo.bar.product \
      -DartifactId=my-vaadin-project \
      -Dversion=1.0.0-SNAPSHOT \
      -DapplicationName='My product' \
      -DapplicationUrl='https://www.myproduct.com' \
      -Dtenant='sygnum' \
      -DinceptionYear=2019 \
      -DorganizationName='My company' \
      -DorganizationUrl='https://www.mycompany.com' \
      -DdeveloperEMail='jon.doe@mycompany.com' \
      -DdeveloperId='doj' \
      -DdeveloperName='Jon Doe'