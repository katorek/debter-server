package com.wjaronski.debter.server.service;

import com.wjaronski.debter.server.domain.Debt;
import com.wjaronski.debter.server.repository.DebtRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DebtOptimizer {
  private final DebtRepository debtRepository;

  public DebtOptimizer(DebtRepository debtRepository) {
    this.debtRepository = debtRepository;
  }

  @Transactional
  public List<Debt> optimize(List<Debt> debts) {
    boolean changed = true;
    while (changed) {
      changed = false;
      for (int i = 0; i < debts.size() - 1; i++) {
        if (debts.get(i).isToDelete()) continue;
        for (int j = i + 1; j < debts.size(); j++) {
          changed = fixLoopedDebts(debts.get(i), debts.get(j));
        }
      }
    }
    List<Debt> toDelete = debts.stream().filter(Debt::isToDelete).collect(Collectors.toList());
    List<Debt> toSave = debts.stream().filter(debt -> !debt.isToDelete()).collect(Collectors.toList());
    delete(toDelete);
    save(toSave);

    return toSave;
  }

  @Transactional
  public void delete(List<Debt> debts) {
    debts.forEach(d -> debtRepository.deleteById(d.getId()));
  }

  @Transactional
  public void save(List<Debt> debts) {
    debtRepository.saveAll(debts);
  }

  private boolean fixLoopedDebts(Debt d1, Debt d2) {
    if (d1.isToDelete() || d2.isToDelete()) return false;
    if (isRecursiveDebt(d1) || isRecursiveDebt(d2)) {
      return true;
    }
    if (areTheSame(d1, d2)) {
      d1.updateAmount(d2.getAmount());
      d2.setAmount(0.0);
      d2.setToDelete(true);
      return true;
    } else if (loopedDebt(d1, d2)) {
      if (d1.getAmount().equals(d2.getAmount())) {
        d1.setToDelete(true);
        d2.setToDelete(true);
      } else if (d1.getAmount() > d2.getAmount()) {
        d1.updateAmount(d2.getAmount() * -1);
        d2.setToDelete(true);
      } else {
        d2.updateAmount(d1.getAmount() * -1);
        d1.setToDelete(true);
      }
      return true;
    } else if (canByMerged(d1, d2)) {
      moveDebts(d1, d2);
      return true;
    } else if (canByMerged(d2, d1)) {
      moveDebts(d1, d2);
      return true;
    }
    return false;
  }

  private boolean isRecursiveDebt(Debt debt) {
    if (debt.getDebtor().equals(debt.getCreditor()) && !debt.isToDelete()) {
      log.debug("{}", debt);
      debt.setToDelete(true);
      return true;
    }
    return false;
  }

  private void moveDebts(Debt from, Debt to) {
    if (from.getAmount().equals(to.getAmount())) {
            /*  x 10 -> y \  \
                           ---  x 10 -> z
                y 10 -> z /  /              */
      from.setCreditor(to.getCreditor());
      to.setToDelete(true);
    } else if (from.getAmount() > to.getAmount()) {
            /*  x 20 -> y \  \   x (20-1)19 -> y
                           ---
                y 1 -> z  /  /   x 1 -> z */
      from.updateAmount(to.getAmount() * -1);
      to.setDebtor(from.getDebtor());
    } else {
            /*  x 1 -> y \  \  x 1 -> z
                           ---
                y 20 -> z /  /  y (20-1)19 -> z */
      from.setCreditor(to.getCreditor());
      to.updateAmount(from.getAmount() * -1);
    }
  }

  private boolean loopedDebt(Debt d1, Debt d2) {
    return (canByMerged(d1, d2) && canByMerged(d2, d1));
  }

  private boolean areTheSame(Debt d1, Debt d2) {
    return d1.getCreditor().equals(d2.getCreditor()) && d1.getDebtor().equals(d2.getDebtor());
  }

  private Double movableDebt(Debt from, Debt to) {
    if (from.getAmount().equals(to.getAmount())) return from.getAmount();
    else if (from.getAmount() > to.getAmount()) return to.getAmount();
    else return from.getAmount();
  }

  private boolean canByMerged(Debt from, Debt to) {
    return from.getCreditor().equals(to.getDebtor());
  }

}
