package com.example.userservice.commons.dto.api.response;

public class ResponseUser {

  private String email;
  private String name;
  private String userId;

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
}
