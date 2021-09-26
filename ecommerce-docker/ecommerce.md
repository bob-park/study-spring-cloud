## RabbitMQ

```shell
docker run -d --name ecommerce-rabbitmq --network ecommerce-network -p 5672:5672 -p 28080:15672 -e RABBITMQ_DEFAULT_USER=guest -e RABBITMQ_DEFAULT_PASS=guest rabbitmq:management
```

## config-servie

```shell
docker run -d -p 8888:8888 --network ecommerce-network -e "spring.rabbitmq.host=ecommerce-rabbitmq" -e "spring.profiles.active=default" --name ecommerce-config-service hwpark11/config-service:1.0
```

## discovery-service

```shell
docker run -d -p 8761:8761 --network ecommerce-network --name ecommerce-discovery-service hwpark11/discovery-service:1.0
```

## api-gateway-service

```shell
docker run -d -p 8000:8000 --network ecommerce-network -e "spring.cloud.config.uri=http://ecommerce-config-service:8888" -e "spring.rabbitmq.host=ecommerce-rabbitmq" -e "eureka.client.serviceUrl.defaultZone=http://ecommerce-discovery-service:8761/eureka/" --name ecommerce-api-gateway-service hwpark11/api-gateway-service:1.0
```

## marialdb

```shell
docker run -d -p 13306:3306  --network ecommerce-network --name ecommerce-mariadb hwpark11/my-mariadb:1.0
```

## user-service

```shell
docker run -d --network ecommerce-network \
--name ecommerce-user-service \
-e "spring.cloud.config.uri=http://ecommerce-config-service:8888" \
-e "spring.rabbitmq.host=ecommerce-rabbitmq" \
-e "spring.zipkin.base-url=http://ecommerce-zipkin:9411" \
-e "eureka.client.serviceUrl.defaultZone=http://ecommerce-discovery-service:8761/eureka/" \
-e "logging.file=/api-logs/users-ws.log" \
hwpark11/user-service:1.0
```

## order-service
```shell
docker run -d --network ecommerce-network \
--name ecommerce-order-service \
-e "spring.cloud.config.uri=http://ecommerce-config-service:8888" \
-e "spring.rabbitmq.host=ecommerce-rabbitmq" \
-e "spring.zipkin.base-url=http://ecommerce-zipkin:9411" \
-e "eureka.client.serviceUrl.defaultZone=http://ecommerce-discovery-service:8761/eureka/" \
-e "spring.datasource.url=jdbc:mariadb://ecommerce-mariadb:3306/mydb" \
-e "logging.file=/api-logs/orders-ws.log" \
hwpark11/order-service:1.0

```

## catalog-service
```shell
docker run -d --network ecommerce-network \
--name ecommerce-catalog-service \
-e "spring.cloud.config.uri=http://ecommerce-config-service:8888" \
-e "spring.rabbitmq.host=ecommerce-rabbitmq" \
-e "eureka.client.serviceUrl.defaultZone=http://ecommerce-discovery-service:8761/eureka/" \
-e "spring.zipkin.base-url=http://ecommerce-zipkin:9411" \
-e "spring.datasource.url=jdbc:mariadb://ecommerce-mariadb:3306/mydb" \
-e "logging.file=/api-logs/orders-ws.log" \
hwpark11/catalog-service:1.0

```
