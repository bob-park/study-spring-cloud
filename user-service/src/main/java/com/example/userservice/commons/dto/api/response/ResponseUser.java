package com.example.userservice.commons.dto.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUser {

  private String email;
  private String name;
  private String userId;

  private List<ResponseOrder> orders;

  public void setEmail(String email) {
    this.email = email;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getEmail() {
    return email;
  }

  public String getName() {
    return name;
  }

  public String getUserId() {
    return userId;
  }

  public List<ResponseOrder> getOrders() {
    return orders;
  }

  public void setOrders(List<ResponseOrder> orders) {
    this.orders = orders;
  }
}
