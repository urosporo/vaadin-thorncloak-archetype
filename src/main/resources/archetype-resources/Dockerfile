#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
FROM openjdk:jre-alpine
LABEL authors="Urosporo"

ADD target/${artifactId}-${version}-hollow-thorntail.jar /opt/hollow-thorntail.jar
ADD target/${artifactId}-${version}.war /opt/application.war

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/hollow-thorntail.jar", "-Djava.net.preferIPv4Stack=true", "/opt/application.war"]