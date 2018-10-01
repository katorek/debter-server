package com.wjaronski.debter.server.service;

import com.wjaronski.debter.server.domain.User;
import com.wjaronski.debter.server.repository.DebtRepository;
import com.wjaronski.debter.server.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by Wojciech Jaronski
 */

@Component
public class DataInitializer {

  public DataInitializer(DebtRepository debtRepository, UserRepository userRepository, PasswordEncoder encoder) {
    if (!userRepository.findByUsername("admin").isPresent()) {
      userRepository.save(User.builder().username("admin").password(encoder.encode("admin")).build());
    }
//    debtRepository.save(Debt.builder().creditor("wojtek").debtor("kator").amount(ThreadLocalRandom.current().nextDouble(30.0)).build());
//    debtRepository.save(Debt.builder().creditor("arek").debtor("draken").amount(ThreadLocalRandom.current().nextDouble(30.0)).build());
//    debtRepository.save(Debt.builder().creditor("kuba").debtor("mefix").amount(ThreadLocalRandom.current().nextDouble(30.0)).build());
//    userRepository.save(User.builder().username("user").password(encoder.encode("pass")).build());
  }
}
