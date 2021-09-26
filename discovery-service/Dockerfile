FROM amazoncorretto:11
VOLUME /tmp
COPY build/libs/discovery-service.jar DiscoveryService.jar
ENTRYPOINT ["java", "-jar", "DiscoveryService.jar"]