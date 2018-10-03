package com.wjaronski.debter.server.service;

import com.wjaronski.debter.server.domain.User;
import com.wjaronski.debter.server.domain.UserPrincipal;
import com.wjaronski.debter.server.exceptions.UserAlreadyExistsException;
import com.wjaronski.debter.server.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by Wojciech Jaronski
 */

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository repository;
  private final PasswordEncoder encoder;

  public CustomUserDetailsService(UserRepository repository, PasswordEncoder encoder) {
    this.repository = repository;
    this.encoder = encoder;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return repository.findByUsername(username)
      .map(UserPrincipal::new)
      .orElseThrow(() ->
        new UsernameNotFoundException("Username not found with username: " + username)
      );
  }

  @Transactional
  public UserDetails loadUserById(Long id) {
    return repository.findById(id)
      .map(UserPrincipal::new)
      .orElseThrow(() ->
        new UsernameNotFoundException("User not found with id: " + id));
  }

  public User registerUser(User user) throws UserAlreadyExistsException {


    Optional<User> optionalUser = repository.findByUsername(user.getUsername());
    if (optionalUser.isPresent()) {
      throw new UserAlreadyExistsException(user.getUsername());
    }
    user.setPassword(encoder.encode(user.getPassword()));
    log.info("Registering new user: {}", user);
    return repository.save(user);
  }
}
