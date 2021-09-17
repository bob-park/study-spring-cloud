package com.example.firstservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("welcome")
public class FirstController {

  @GetMapping
  public String welcome() {
    return String.format("Welcome to the First Service");
  }
}
