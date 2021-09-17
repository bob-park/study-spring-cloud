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
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

  private final Logger log = LoggerFactory.getLogger(getClass());

  public GlobalFilter() {
    super(Config.class);
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      // Custom Pre filter
      ServerHttpRequest request = exchange.getRequest();
      ServerHttpResponse response = exchange.getResponse();

      log.debug("Global Filter baseMessage: {}", config.getBaseMessage());

      if (config.isPreLogger()) {
        log.debug("Global Filter start: request id -> {}", request.getId());
      }

      // Custom Post filter
      return chain
          .filter(exchange)
          .then(
              Mono.fromRunnable(
                  () -> {
                    if (config.isPreLogger()) {
                      log.debug("Global Filter end: response code -> {}", response.getStatusCode());
                    }
                  }));
    };
  }

  public static class Config {

    private String baseMessage;
    private boolean preLogger;
    private boolean postLogger;

    public String getBaseMessage() {
      return baseMessage;
    }

    public void setBaseMessage(String baseMessage) {
      this.baseMessage = baseMessage;
    }

    public boolean isPreLogger() {
      return preLogger;
    }

    public void setPreLogger(boolean preLogger) {
      this.preLogger = preLogger;
    }

    public boolean isPostLogger() {
      return postLogger;
    }

    public void setPostLogger(boolean postLogger) {
      this.postLogger = postLogger;
    }
  }
}
