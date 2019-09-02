FROM openjdk:jre-alpine
LABEL authors="${organizationName}"

ADD target/application-hollow-thorntail.jar /opt/hollow-thorntail.jar
ADD target/application.war /opt/application.war

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/hollow-thorntail.jar", "-Djava.net.preferIPv4Stack=true", "/opt/application.war"]