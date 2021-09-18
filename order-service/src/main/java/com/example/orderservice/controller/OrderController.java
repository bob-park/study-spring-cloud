package com.example.orderservice.controller;

import com.example.orderservice.commons.dto.api.request.RequestOrder;
import com.example.orderservice.commons.dto.api.response.ResponseOrder;
import com.example.orderservice.commons.dto.order.OrderDto;
import com.example.orderservice.commons.entity.Order;
import com.example.orderservice.service.OrderService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("order-service")
public class OrderController {

  private final Environment env;

  private final OrderService orderService;

  public OrderController(Environment env, OrderService orderService) {
    this.env = env;
    this.orderService = orderService;
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

    OrderDto createdOrder = orderService.createOrder(orderDto);

    return mapper.map(createdOrder, ResponseOrder.class);
  }

  @GetMapping(path = "{userId}/orders")
  public List<ResponseOrder> getOrder(@PathVariable String userId) {

    List<Order> orders = orderService.getOrdersByUserId(userId);

    return orders.stream()
        .map(order -> new ModelMapper().map(order, ResponseOrder.class))
        .collect(Collectors.toList());
  }
}
