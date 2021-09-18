package com.example.orderservice.service;

import com.example.orderservice.commons.dto.order.OrderDto;
import com.example.orderservice.commons.entity.Order;

import java.util.List;

public interface OrderService {

  OrderDto createOrder(OrderDto orderDto);

  OrderDto getOrderByOrderId(String orderId);

  List<Order> getOrdersByUserId(String userId);
}
