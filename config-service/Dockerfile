FROM amazoncorretto:11
VOLUME /tmp
COPY apiEncryptionKey.jks apiEncryptionKey.jks
COPY build/libs/config-service.jar ConfigService.jar
ENTRYPOINT ["java", "-jar", "ConfigService.jar"]