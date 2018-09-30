package com.wjaronski.debter.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Wojciech Jaronski
 */

@RestController
public class LoginController {

  @GetMapping("/resource")
  @ResponseBody
  public Map<String, Object> home() {
    Map<String, Object> model = new HashMap<>();
    model.put("id", UUID.randomUUID().toString());
    model.put("content", "Hello World !");
    return model;
  }

  @RequestMapping("/user")
  public Principal user(Principal user) {
    return user;
  }

}
