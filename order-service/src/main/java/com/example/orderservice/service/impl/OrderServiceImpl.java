package com.example.orderservice.service.impl;

import com.example.orderservice.commons.dto.order.OrderDto;
import com.example.orderservice.commons.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.OrderService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;

  public OrderServiceImpl(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Override
  public OrderDto createOrder(OrderDto orderDto) {
    orderDto.setOrderId(UUID.randomUUID().toString());
    orderDto.setTotalPrice(orderDto.getUnitPrice() * orderDto.getQty());

    ModelMapper mapper = new ModelMapper();

    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // 정확히 맞아야 실행됨

    Order order = mapper.map(orderDto, Order.class);

    orderRepository.save(order);

    return mapper.map(order, OrderDto.class);
  }

  @Override
  public OrderDto getOrderByOrderId(String orderId) {

    Order order =
        orderRepository
            .findByOrderId(orderId)
            .orElseThrow(() -> new IllegalStateException("Order not found"));

    return new ModelMapper().map(order, OrderDto.class);
  }

  @Override
  public List<Order> getOrdersByUserId(String userId) {
    return orderRepository.findByUserId(userId);
  }
}
