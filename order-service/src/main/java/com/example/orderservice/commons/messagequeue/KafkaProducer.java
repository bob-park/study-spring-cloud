package com.example.orderservice.commons.messagequeue;

import com.example.orderservice.commons.dto.order.OrderDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
  private final Logger log = LoggerFactory.getLogger(getClass());

  private final KafkaTemplate<String, String> kafkaTemplate;

  public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public OrderDto send(String topic, OrderDto orderDto) {
    ObjectMapper mapper = new ObjectMapper();

    String jsonInString = "";

    try {
      jsonInString = mapper.writeValueAsString(orderDto);
    } catch (JsonProcessingException e) {
      log.error("Json Parsing Error : {}", e.getMessage(), e);
    }

    kafkaTemplate.send(topic, jsonInString);

    log.info("Kafka Producer send data from Order microservice: {}", orderDto);

    return orderDto;
  }
}
