package com.wjaronski.debter.server.service;

import com.wjaronski.debter.server.domain.Debt;
import com.wjaronski.debter.server.repository.DebtRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Wojciech Jaronski
 */

@Slf4j
@Service
public class DebtService {
  private final DebtRepository repository;
  private final DebtOptimizer debtOptimizer;

  public DebtService(DebtRepository repository, DebtOptimizer debtOptimizer) {
    this.repository = repository;
    this.debtOptimizer = debtOptimizer;
  }

  public List<Debt> findAll() {
    return repository.findAll();
  }

  public List<Debt> findAllFor(String user) {
    return findAll()
      .stream()
      .filter(debt -> user.equals(debt.getCreditor()) || user.equals(debt.getDebtor()))
      .collect(Collectors.toList());
  }

  @Transactional
  public void addDebt(final Debt debt) {
    Debt debtToPersist;
    if ((debtToPersist = debtExists(debt)) != null) {
      debtToPersist.updateAmount(debt.getAmount());
      saveDebt(debtToPersist);
    } else if ((debtToPersist = debtExists(Debt.getReversed(debt))) != null) {
      debtToPersist.updateAmount(debt.getAmount() * -1);
      saveDebt(debtToPersist);
    } else {
      saveDebt(debt);
    }
  }

  private void saveDebt(Debt debt) {
    if (debt.getAmount() < 0.0) {
      Optional<Debt> tempDebt = findByCreditorAndDebtor(debt.getCreditor(), debt.getDebtor());
      if (tempDebt.isPresent()) {
        debt.setId(tempDebt.get().getId());
      }
      debt = Debt.getReversed(debt);
    }
//    debt.setCreditor(userFromDb(debt.getCreditor()));
//    debt.setDebtor(userFromDb(debt.getDebtor()));
    if (debt.getAmount().equals(0.0)) {
      repository.deleteById(debt.getId());
    } else {
      repository.save(debt);
    }

  }

  private Debt debtExists(Debt d1) {
    return findByCreditorAndDebtor(d1.getCreditor(), d1.getDebtor()).orElse(null);
  }

  private Optional<Debt> findByCreditorAndDebtor(String creditor, String debtor) {
    return repository.findByCreditorAndDebtor(creditor, debtor);
  }

  @Transactional
  public List<Debt> optimizeDebts() {
    return debtOptimizer.optimize(findAll());
  }


}
