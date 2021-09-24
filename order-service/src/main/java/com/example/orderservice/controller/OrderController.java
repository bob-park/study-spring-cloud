package com.example.orderservice.controller;

import com.example.orderservice.commons.dto.api.request.RequestOrder;
import com.example.orderservice.commons.dto.api.response.ResponseOrder;
import com.example.orderservice.commons.dto.order.OrderDto;
import com.example.orderservice.commons.entity.Order;
import com.example.orderservice.commons.messagequeue.KafkaProducer;
import com.example.orderservice.commons.messagequeue.OrderProducer;
import com.example.orderservice.service.OrderService;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("order-service")
public class OrderController {

  private final Environment env;

  private final OrderService orderService;

  private final KafkaProducer kafkaProducer;
  private final OrderProducer orderProducer;

  public OrderController(
      Environment env,
      OrderService orderService,
      KafkaProducer kafkaProducer,
      OrderProducer orderProducer) {
    this.env = env;
    this.orderService = orderService;
    this.kafkaProducer = kafkaProducer;
    this.orderProducer = orderProducer;
  }

  @GetMapping(path = "health_check")
  public String status() {
    return String.format(
        "It's Working in Order Service on PORT %s", env.getProperty("local.server.port"));
  }

  @PostMapping(path = "{userId}/orders")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseOrder createOrder(@PathVariable String userId, @RequestBody RequestOrder order) {

    ModelMapper mapper = new ModelMapper();
    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // 정확히 맞아야 실행됨

    OrderDto orderDto = mapper.map(order, OrderDto.class);

    orderDto.setUserId(userId);

    /* JPA */
    //    OrderDto createdOrder = orderService.createOrder(orderDto);
    //    ResponseOrder responseOrder = mapper.map(createdOrder, ResponseOrder.class);

    /* kafka */
    orderDto.setOrderId(UUID.randomUUID().toString());
    orderDto.setTotalPrice(order.getUnitPrice() * order.getQty());

    /* send this order to the kafka */
    kafkaProducer.send("example-catalog-topic", orderDto);
    orderProducer.send("orders", orderDto);

    ResponseOrder responseOrder = mapper.map(orderDto, ResponseOrder.class);

    return responseOrder;
  }

  @GetMapping(path = "{userId}/orders")
  public List<ResponseOrder> getOrder(@PathVariable String userId) {

    List<Order> orders = orderService.getOrdersByUserId(userId);

    return orders.stream()
        .map(order -> new ModelMapper().map(order, ResponseOrder.class))
        .collect(Collectors.toList());
  }
}
