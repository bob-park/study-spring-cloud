FROM amazoncorretto:11
VOLUME /tmp
COPY build/resources/main/apiEncryptionKey.jks apiEncryptionKey.jks
COPY build/libs/config-service.jar ConfigService.jar
ENTRYPOINT ["java", "-jar", "ConfigService.jar"]