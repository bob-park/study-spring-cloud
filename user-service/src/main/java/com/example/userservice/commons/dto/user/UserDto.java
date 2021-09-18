package com.example.userservice.commons.dto.user;

import com.example.userservice.commons.dto.api.response.ResponseOrder;

import java.time.LocalDateTime;
import java.util.List;

public class UserDto {

  private String email;
  private String name;
  private String pwd;

  private String userId;
  private LocalDateTime createAt;

  private String encryptPWd;

  private List<ResponseOrder> orders;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public LocalDateTime getCreateAt() {
    return createAt;
  }

  public void setCreateAt(LocalDateTime createAt) {
    this.createAt = createAt;
  }

  public String getEncryptPWd() {
    return encryptPWd;
  }

  public void setEncryptPWd(String encryptPWd) {
    this.encryptPWd = encryptPWd;
  }

  public List<ResponseOrder> getOrders() {
    return orders;
  }

  public void setOrders(List<ResponseOrder> orders) {
    this.orders = orders;
  }
}
