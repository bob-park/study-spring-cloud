# Monitoring

* Prometheus
* Grafana

## Prometheus
```shell
docker run -d -p 19090:9090 --network ecommerce-network --name ecommerce-prometheus -v /Users/hwpark/Documents/study/spring-cloud-workspace/monitoring/prometheus/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus 
```


## Grafana
```shell
docker run -d -p 13000:3000 --network ecommerce-network --name ecommerce-grafana grafana/grafana 
```