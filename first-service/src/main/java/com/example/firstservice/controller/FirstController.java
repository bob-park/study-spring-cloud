package com.example.firstservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("first-service")
public class FirstController {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @GetMapping(path="welcome")
  public String welcome() {
    return String.format("Welcome to the First Service");
  }

  @GetMapping(path = "message")
  public String message(@RequestHeader("first-request") String header) {

    log.info("first-request : {}", header);

    return String.format("message : %s", header);
  }
}
