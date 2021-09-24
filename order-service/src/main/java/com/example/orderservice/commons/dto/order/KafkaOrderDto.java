package com.example.orderservice.commons.dto.order;

import com.example.orderservice.commons.dto.common.kafka.Payload;
import com.example.orderservice.commons.dto.common.kafka.Schema;

import java.io.Serializable;

public class KafkaOrderDto implements Serializable {

  private final Schema schema;
  private final Payload payload;

  public KafkaOrderDto(Schema schema, Payload payload) {
    this.schema = schema;
    this.payload = payload;
  }

  public Schema getSchema() {
    return schema;
  }

  public Payload getPayload() {
    return payload;
  }

  @Override
  public String toString() {
    return "KafkaOrderDto{" + "schema=" + schema + ", payload=" + payload + '}';
  }
}
