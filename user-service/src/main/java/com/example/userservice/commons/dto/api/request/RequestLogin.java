package com.example.userservice.commons.dto.api.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RequestLogin {

  @NotNull(message = "Email cannot be null")
  @Size(min = 2, message = "Email not be less then two characters")
  @Email
  private String email;

  @NotNull(message = "Password cannot be null")
  @Size(min = 8, message = "Password must be equals or grater then 8 characters")
  private String password;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
