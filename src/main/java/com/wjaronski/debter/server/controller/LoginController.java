package com.wjaronski.debter.server.controller;

import com.wjaronski.debter.server.domain.User;
import com.wjaronski.debter.server.exceptions.UserAlreadyExistsException;
import com.wjaronski.debter.server.service.MyUserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Wojciech Jaronski
 */

@RestController
public class LoginController {

  private final MyUserDetailsService userDetailsService;

  public LoginController(MyUserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

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


  @PostMapping("/register")
  public User registerUser(@RequestBody @Valid User user) throws UserAlreadyExistsException {
    return userDetailsService.registerUser(user);
  }
}
