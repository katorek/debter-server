package com.wjaronski.debter.server.controller;

import com.wjaronski.debter.server.domain.JwtAuthenticationResponse;
import com.wjaronski.debter.server.domain.User;
import com.wjaronski.debter.server.domain.UserRequest;
import com.wjaronski.debter.server.exceptions.UserAlreadyExistsException;
import com.wjaronski.debter.server.repository.UserRepository;
import com.wjaronski.debter.server.security.JwtTokenProvider;
import com.wjaronski.debter.server.service.CustomUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Wojciech Jaronski
 */

@RestController
@RequestMapping("/api/auth")
public class LoginController {

  private final CustomUserDetailsService userDetailsService;
  private final UserRepository userRepository;
  private final PasswordEncoder encoder;
  private final JwtTokenProvider tokenProvider;

  private final AuthenticationManager authenticationManager;

  public LoginController(CustomUserDetailsService userDetailsService, UserRepository userRepository, PasswordEncoder encoder, JwtTokenProvider tokenProvider, AuthenticationManager authenticationManager) {
    this.userDetailsService = userDetailsService;
    this.userRepository = userRepository;
    this.encoder = encoder;
    this.tokenProvider = tokenProvider;
    this.authenticationManager = authenticationManager;
  }

  @GetMapping("/resource")
  @ResponseBody
  public Map<String, Object> home() {
    Map<String, Object> model = new HashMap<>();
    model.put("id", UUID.randomUUID().toString());
    model.put("content", "Hello World !");
    return model;
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser2(@Valid @RequestBody UserRequest signUpRequest) throws UserAlreadyExistsException {

    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body("Nazwa użytkownika jest już zajęta!");
    }

    User user = User.builder()
      .username(signUpRequest.getUsername())
      .password(encoder.encode(signUpRequest.getPassword()))
      .build();

    User result = userRepository.save(user);

    URI location = ServletUriComponentsBuilder
      .fromCurrentContextPath().path("/api/users/{username}")
      .buildAndExpand(result.getUsername()).toUri();
    return ResponseEntity.created(location).body("Użytkownik zarejestrowany poprawnie.");
  }

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        loginRequest.getUsername(),
        loginRequest.getPassword()
      )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = tokenProvider.generateToken(authentication);
    return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
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
