package com.wjaronski.debter.server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by Wojciech Jaronski
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "debts")
public class Debt {
  @Id
  @GeneratedValue
  private Long id;
  private String debtor;
  private String creditor;
  @Column(columnDefinition = "Decimal(10,2)")
  private Double amount;

  @Transient
  private boolean toDelete = false;

  public static Debt getReversed(Debt debt) {
    return Debt.builder()
      .id(debt.id)
      .debtor(debt.creditor)
      .creditor(debt.debtor)
      .amount(debt.amount * -1)
      .build();
  }

  public void updateAmount(Double amount) {
    this.amount += amount;
  }
}
