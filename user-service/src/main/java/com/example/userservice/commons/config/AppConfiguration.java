package com.example.userservice.commons.config;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfiguration {

  @Bean
  public BCryptPasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * actuator 의 httptrace 를 사용하기 위해 bean 에 등록해주어야 한다.
   *
   * @return
   */
  @Bean
  public HttpTraceRepository httpTraceRepository() {
    return new InMemoryHttpTraceRepository();
  }

  @Bean
  @LoadBalanced
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }
}
