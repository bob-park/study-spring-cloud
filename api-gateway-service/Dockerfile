FROM amazoncorretto:11
VOLUME /tmp
COPY build/libs/api-gateway-service.jar api-gateway-service.jar
ENTRYPOINT ["java", "-jar", "api-gateway-service.jar"]