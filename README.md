Vaadin Thorncloak Archetype
===========================

This archetype creates a new Vaadin 14 project with prep of Keycloak- and Thorntail-Integration for Bearer-token based multi-tenancy and for running in a docker container.

    mvn archetype:generate \
      -DarchetypeArtifactId=vaadin-thorncloak-archetype \
      -DarchetypeGroupId=com.urosporo \
      -DarchetypeVersion=1.0.0-SNAPSHOT \
      -DgroupId=com.foo.bar \
      -DartifactId=my-vaadin-project \
      -Dversion=1.0.0-SNAPSHOT \
      -DapplicationName='My Vaadin Application Title' \
      -DinceptionYear=2019 \
      -DorganizationName='Foo Bar AG' \
      -Dtenant='aTenantKey'