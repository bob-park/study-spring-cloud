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


## 번외. Docker Network 생성
### Network 를 생성하는 이유
* 환경이 항상 IP 가 같을 수 없기 때문
* container 간에 통신할때 IP 가 변경되더라도 container name 으로 접근이 가능함

```shell
docker network create --gateway 172.18.0.1 --subnet 172.18.0.0/16 ecommerce-network
```
