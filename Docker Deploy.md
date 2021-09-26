# Docker 를 이용한 Application 배포



## 1. Create `Dockerfile`

```dockerfile
FROM amazoncorretto:11
VOLUME /tmp
COPY build/libs/user-service.jar UserService.jar
ENTRYPOINT ["java", "-jar", "UserService.jar"]
```



## 2. Application Build



## 3. `Dockerfile` Build

```shell
docker build --tag hwpark11/user-service:1.0 .
```



## 3. push `Docker Hub`

```shell
docker push hwpark11/user-service:1.0
```

