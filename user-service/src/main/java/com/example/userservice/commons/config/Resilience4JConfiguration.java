package com.example.userservice.commons.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4JConfiguration {

  @Bean
  public Customizer<Resilience4JCircuitBreakerFactory> globalCustomCircuitBreaker() {

    CircuitBreakerConfig circuitBreakerConfig =
        CircuitBreakerConfig.custom()
            // CircuitBreaker 를 open-close 를 결정하는 failure rate threshold percentage - default: 50
            .failureRateThreshold(4)
            // CircuitBreaker 를 open 한 상태를 유지하는 지속 기간 - 기간 이후 half-open 상태 - default: 60 seconds
            .waitDurationInOpenState(Duration.ofMillis(1000))
            // CircuitBreaker 가 close 시 결과를 기록하는데 사용되는 유형
            .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
            // CircuitBreaker 가 close 시 호출 결과를 기록하는데 사용되는 slid 의 크기
            .slidingWindowSize(2)
            .build();

    // 어느정도 시간이 지났을 때 CircuitBreaker 를 open 할 건지에 대한 설정
    TimeLimiterConfig timeLimiterConfig =
        TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build();

    return factory ->
        factory.configureDefault(
            id ->
                new Resilience4JConfigBuilder(id)
                    .timeLimiterConfig(timeLimiterConfig)
                    .circuitBreakerConfig(circuitBreakerConfig)
                    .build());
  }
}
