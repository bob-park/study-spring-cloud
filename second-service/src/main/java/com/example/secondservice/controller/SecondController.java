package com.example.secondservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("second-service")
public class SecondController {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @GetMapping(path = "welcome")
  public String welcome() {
    return String.format("Welcome to the Second Service");
  }

  @GetMapping(path = "message")
  public String message(@RequestHeader("second-request") String header) {

    log.info("second-request : {}", header);

    return String.format("message : %s", header);
  }

  @GetMapping(path = "check")
  public String check() {
    return "Hi, there. This is a message from Second Service.";
  }
}
