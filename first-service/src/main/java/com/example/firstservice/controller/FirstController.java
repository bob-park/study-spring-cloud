package com.example.firstservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("first-service")
public class FirstController {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private final Environment env;

  public FirstController(Environment env) {
    this.env = env;
  }

  @GetMapping(path = "welcome")
  public String welcome() {
    return String.format("Welcome to the First Service");
  }

  @GetMapping(path = "message")
  public String message(@RequestHeader("first-request") String header) {

    log.info("first-request : {}", header);

    return String.format("message : %s", header);
  }

  @GetMapping(path = "check")
  public String check(HttpServletRequest request) {

    log.info("Server port={}", request.getServerPort());

    return String.format(
        "Hi, there. This is a message from First Service on PORT %s",
        env.getProperty("local.server.port"));
  }
}
