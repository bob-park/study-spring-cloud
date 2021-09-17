package com.example.apigatewayservice.commons.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

  private final Logger log = LoggerFactory.getLogger(getClass());

  public CustomFilter() {
    super(Config.class);
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      // Custom Pre filter
      ServerHttpRequest request = exchange.getRequest();
      ServerHttpResponse response = exchange.getResponse();

      log.info("Custom PRE filter : {}", request.getId());

      // Custom Post filter
      return chain
          .filter(exchange)
          .then(
              Mono.fromRunnable(
                  () -> log.info("Custom POST filter : {}", response.getStatusCode())));
    };
  }

  public static class Config {
    // Put the configuration properties
  }
}
