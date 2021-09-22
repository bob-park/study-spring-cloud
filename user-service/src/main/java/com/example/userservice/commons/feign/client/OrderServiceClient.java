package com.example.userservice.commons.feign.client;

import com.example.userservice.commons.dto.api.response.ResponseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "order-service")
public interface OrderServiceClient {

  @GetMapping(path = "/order-service/{userId}/orders")
  List<ResponseOrder> getOrders(@PathVariable String userId);
}
