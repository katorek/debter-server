package com.wjaronski.debter.server.controller;

import com.wjaronski.debter.server.domain.Debt;
import com.wjaronski.debter.server.service.DebtService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Wojciech Jaronski
 */
@RestController
@RequestMapping("/api/debts")
public class DebtController {

  private final DebtService debtService;

  public DebtController(DebtService debtService) {
    this.debtService = debtService;
  }

  @GetMapping()
  public List<Debt> getAll() {
    return debtService.findAll();
  }

  @GetMapping("/{user}")
  @ResponseBody
  public List<Debt> getAllDebtsForUser(@PathVariable String user) {
    return debtService.findAllFor(user);
  }

  @PostMapping()
  public void addDebt(@RequestBody @Valid Debt debt) {
    debtService.addDebt(debt);
  }

  @GetMapping("/optimize")
  public List<Debt> optimizeDebts() {
    return debtService.optimizeDebts();
  }
}
