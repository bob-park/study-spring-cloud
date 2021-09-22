package com.example.userservice.commons.feign.client;

import com.example.userservice.commons.dto.api.response.ResponseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "order-service")
public interface OrderServiceClient {

//  @GetMapping(path = "/order-service/{userId}/orders")
  @GetMapping(path = "/order-service/{userId}/orders_ng") // 잘못된 주소 - 예외처리
  List<ResponseOrder> getOrders(@PathVariable String userId);
}
