package com.wjaronski.debter.server.service;

import com.wjaronski.debter.server.domain.Debt;
import com.wjaronski.debter.server.repository.DebtRepository;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Wojciech Jaronski
 */

@Component
public class TestDataInitializer {

  public TestDataInitializer(DebtRepository repository) {
    repository.save(Debt.builder().creditor("wojtek").debtor("kator").amount(ThreadLocalRandom.current().nextDouble(30.0)).build());
    repository.save(Debt.builder().creditor("arek").debtor("draken").amount(ThreadLocalRandom.current().nextDouble(30.0)).build());
    repository.save(Debt.builder().creditor("kuba").debtor("mefix").amount(ThreadLocalRandom.current().nextDouble(30.0)).build());
  }
}
