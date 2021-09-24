package com.example.catalogservice.commons.messagequeue;

import com.example.catalogservice.commons.entity.Catalog;
import com.example.catalogservice.repository.CatalogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaConsumer {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private final CatalogRepository catalogRepository;

  public KafkaConsumer(CatalogRepository catalogRepository) {
    this.catalogRepository = catalogRepository;
  }

  @KafkaListener(topics = "example-catalog-topic")
  public void updateQty(String kafkaMessage) {
    log.info("kafka Message: -> {}", kafkaMessage);

    // Deserialize
    Map<Object, Object> map = new HashMap<>();

    ObjectMapper mapper = new ObjectMapper();

    try {
      map = mapper.readValue(kafkaMessage, new TypeReference<>() {});
    } catch (JsonProcessingException e) {
      log.error("Json Parsing Error - {}", e.getMessage(), e);
    }

    Catalog catalog =
        catalogRepository
            .findByProductId((String) map.get("productId"))
            .orElseThrow(() -> new IllegalStateException("Catalog not found."));

    catalog.setStock(catalog.getStock() - (Integer) map.get("qty"));
  }
}
