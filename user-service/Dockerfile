FROM amazoncorretto:11
VOLUME /tmp
COPY build/libs/user-service.jar UserService.jar
ENTRYPOINT ["java", "-jar", "UserService.jar"]