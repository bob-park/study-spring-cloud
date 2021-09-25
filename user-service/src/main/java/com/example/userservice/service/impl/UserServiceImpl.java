package com.example.userservice.service.impl;

import com.example.userservice.commons.dto.api.response.ResponseOrder;
import com.example.userservice.commons.dto.user.UserDto;
import com.example.userservice.commons.entity.UserEntity;
import com.example.userservice.commons.feign.client.OrderServiceClient;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private final PasswordEncoder passwordEncoder;

  private final UserRepository userRepository;

  private final Environment env;
  private final RestTemplate restTemplate;

  private final OrderServiceClient orderServiceClient;

  private final CircuitBreakerFactory circuitBreakerFactory;

  public UserServiceImpl(
      PasswordEncoder passwordEncoder,
      UserRepository userRepository,
      Environment env,
      RestTemplate restTemplate,
      OrderServiceClient orderServiceClient,
      CircuitBreakerFactory circuitBreakerFactory) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.env = env;
    this.restTemplate = restTemplate;
    this.orderServiceClient = orderServiceClient;
    this.circuitBreakerFactory = circuitBreakerFactory;
  }

  @Override
  public UserDto createUser(UserDto userDto) {

    userDto.setUserId(UUID.randomUUID().toString());

    ModelMapper mapper = new ModelMapper();

    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // 정확히 맞아야 실행됨

    UserEntity userEntity = mapper.map(userDto, UserEntity.class);

    userEntity.setEncryptPwd(passwordEncoder.encode(userDto.getPwd()));

    userRepository.save(userEntity);

    return mapper.map(userEntity, UserDto.class);
  }

  @Override
  public UserDto getUserByUserId(String userId) {

    UserEntity userEntity =
        userRepository
            .findByUserId(userId)
            .orElseThrow(() -> new UsernameNotFoundException("User not found."));

    UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

    //    List<ResponseOrder> orders = new ArrayList<>();

    /* Using as rest template */
    //    String orderUrl = String.format(env.getProperty("order-service.url"), userId);
    //
    //    ResponseEntity<List<ResponseOrder>> responseEntity =
    //        restTemplate.exchange(
    //            orderUrl, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
    //
    //    List<ResponseOrder> ordersList = responseEntity.getBody();

    /* Using feign client */
    /* Feign exception handling */
    //    List<ResponseOrder> ordersList = null;
    //
    //    try {
    //      ordersList = orderServiceClient.getOrders(userId);
    //    } catch (FeignException e) {
    //      log.error(e.getMessage());
    //    }

    /* Error Decoder */
    //    List<ResponseOrder> ordersList = orderServiceClient.getOrders(userId);

    /* Using Circuit Breaker */
    log.info("Before call orders microservice");
    CircuitBreaker circuitbreaker = circuitBreakerFactory.create("circuitbreaker");

    // list 값이 존재하지 않을 경우 empty list 를 반환
    List<ResponseOrder> ordersList =
        circuitbreaker.run(
            () -> orderServiceClient.getOrders(userId), throwable -> Collections.emptyList());
    log.info("After call orders microservice");

    userDto.setOrders(ordersList);

    return userDto;
  }

  @Override
  public List<UserEntity> getUserByAll() {
    return userRepository.findAll();
  }

  @Override
  public UserDto getUserDetailByEmail(String username) {

    UserEntity userEntity =
        userRepository
            .findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));

    UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

    List<ResponseOrder> orders = new ArrayList<>();

    userDto.setOrders(orders);

    return userDto;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    UserEntity userEntity =
        userRepository
            .findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));

    return new User(
        userEntity.getEmail(),
        userEntity.getEncryptPwd(),
        true,
        true,
        true,
        true,
        new ArrayList<>());
  }
}
