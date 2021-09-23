# Kafka 를 사용한 데이터 동기화



  `Kafka` + `Kafka Connect` + `Kafka Connect JDBC` 를 이용하여 Microservice 간 데이터 동기화 구성을 다음과 같이 한다.



### Kafka 를 사용하여 데이터를 동기화하면 좋은 점

* `MSA` 이 데이터가 저장되는 시스템(DB, Storage, APP 등) 과 상관없이 kafka 를 통해서 받아오거나 보낼 수 있음
* 즉, 단일 포맷을 사용할 수 있음



## 사전 필요 사항

### Kafka Cluster 구성

* Zookeeper
* Kafka - Broker





## Kafka Connect  구성



### 1. Kafka Connect 설치

#### 실행

```sh
${CONNECT_HOME}/bin/connect-distributed ${CONNECT_HOME}/etc/kafka/connect-distributed.properties
```



#### 환경 설정 파일 수정 필요

* bootstrap.servers
* plugin.path



### 2. Kafka Connect JDBC 설치

* 적당한 위치에 설치
* Kafka Connect 의 환경설정 변경
  * plugin.path=${CONNECT_JDBC_HOME}/lib
* 동기화할 JDBC jar 파일 복사
  * 위치 : ${CONNECT_HOME}/share/jara/kafka



### 3. Kafka Connector

#### 역할

* DB 에 CRUD 된 데이터를 Consumer 에 전달 할 수 있다. (Kafka Source Connector)
* 다른 종류의 DB 에서 CRUD 된 데이터를 동기화 할 수 있다. (Kafka Sink Connector)


#### Kafka Connector 확인 

* 간단하게 확인

  ```sh
  curl --request GET \
    --url http://{CONNECT_IP}:8083/connectors
  ```

  

* 자세하게 확인

  ```sh
  curl --request GET \
    --url http://{CONNECT_IP}:8083/connectors/{CONNECTOR_NAME}/status
  ```

  



#### Kafka Source Connector 추가

```sh
curl --request POST \
  --url http://{CONNECT_IP}:8083/connectors \
  --header 'Content-Type: application/json' \
  --data '{
  "name": "my-source-connect",
  "config": {
    "connector.class": "io.confluent.connect.jdbc.JdbcSourceConnector",
    "connection.url": "jdbc:mysql://localhost:13306/mydb",
    "connection.user": "root",
    "connection.password": "12345",
    "mode": "incrementing",
    "incrementing.column.name": "id",
    "table.whitelist": "users",
    "topic.prefix": "my_topic_",
    "tasks.max": "1"
  }
}'
```



#### Kafka Sink Connector 추가

```sh
curl --request POST \
  --url http://{CONNECT_IP}:8083/connectors \
  --header 'Content-Type: application/json' \
  --data '{
  "name": "my-sink-connect",
  "config": {
    "connector.class": "io.confluent.connect.jdbc.JdbcSinkConnector",
    "connection.url": "jdbc:mysql://localhost:13306/mydb",
    "connection.user": "root",
    "connection.password": "12345",
    "auto.create": "true",
    "auto.evolve": "true",
    "delete.enabled": "false",
    "tasks.max": "1",
    "topics": "my_topic_users"
  }
}'
```



