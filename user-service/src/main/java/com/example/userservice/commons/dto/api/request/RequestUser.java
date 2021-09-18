package com.example.userservice.commons.dto.api.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RequestUser {

  @NotNull(message = "Email cannot be null")
  @Size(min = 2, message = "Email not be loss then two characters")
  @Email
  private String email;

  @NotNull(message = "Name cannot be null")
  @Size(min = 2, message = "Name not be loss then two characters")
  private String name;

  @NotNull(message = "Password cannot be null")
  @Size(min = 8, message = "Password must be equal or grater then 8 characters")
  private String pwd;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "RequestUser{"
        + "email='"
        + email
        + '\''
        + ", name='"
        + name
        + '\''
        + ", pwd='"
        + pwd
        + '\''
        + '}';
  }
}
