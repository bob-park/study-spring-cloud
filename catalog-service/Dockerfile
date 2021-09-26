FROM amazoncorretto:11
VOLUME /tmp
COPY build/libs/catalog-service.jar catalog-service.jar
ENTRYPOINT ["java", "-jar", "catalog-service.jar"]