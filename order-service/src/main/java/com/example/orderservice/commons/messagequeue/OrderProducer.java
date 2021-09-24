package com.example.orderservice.commons.messagequeue;

import com.example.orderservice.commons.dto.common.kafka.Field;
import com.example.orderservice.commons.dto.common.kafka.Payload;
import com.example.orderservice.commons.dto.common.kafka.Schema;
import com.example.orderservice.commons.dto.order.KafkaOrderDto;
import com.example.orderservice.commons.dto.order.OrderDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class OrderProducer {
  private final Logger log = LoggerFactory.getLogger(getClass());

  private final KafkaTemplate<String, String> kafkaTemplate;

  private final Schema schema;

  public OrderProducer(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;

    List<Field> fields =
        Arrays.asList(
            new Field("string", false, "order_id"),
            new Field("string", false, "user_id"),
            new Field("string", false, "product_id"),
            new Field("int32", true, "qty"),
            new Field("int32", true, "unit_price"),
            new Field("int32", true, "total_price"));

    this.schema =
        Schema.builder()
            .setType("struct")
            .setFields(fields)
            .setOptional(false)
            .setName("orders")
            .build();
  }

  public OrderDto send(String topic, OrderDto orderDto) {
    Payload payload =
        Payload.builder()
            .setOrder_id(orderDto.getOrderId())
            .setUser_id(orderDto.getUserId())
            .setProduct_id(orderDto.getProductId())
            .setQty(orderDto.getQty())
            .setUnit_price(orderDto.getUnitPrice())
            .setTotal_price(orderDto.getTotalPrice())
            .build();

    KafkaOrderDto kafkaOrderDto = new KafkaOrderDto(schema, payload);

    ObjectMapper mapper = new ObjectMapper();

    String jsonInString = "";

    try {
      jsonInString = mapper.writeValueAsString(kafkaOrderDto);
    } catch (JsonProcessingException e) {
      log.error("Json Parsing Error : {}", e.getMessage(), e);
    }

    kafkaTemplate.send(topic, jsonInString);

    log.info("Order Producer send data from Order microservice: {}", kafkaOrderDto);

    return orderDto;
  }
}
