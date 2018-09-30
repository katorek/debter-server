package com.wjaronski.debter.server.repository;

import com.wjaronski.debter.server.domain.Debt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Wojciech Jaronski
 */

public interface DebtRepository extends JpaRepository<Debt, Long> {
  Optional<Debt> findByCreditorAndDebtor(String creditor, String debtor);
}
