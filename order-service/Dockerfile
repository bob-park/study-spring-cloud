FROM amazoncorretto:11
VOLUME /tmp
COPY build/libs/order-service.jar order-service.jar
ENTRYPOINT ["java", "-jar", "order-service.jar"]