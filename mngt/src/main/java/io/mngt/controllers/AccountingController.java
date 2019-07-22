package io.mngt.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.mngt.domain.BalanceILS;
import io.mngt.domain.Transaction;
import io.mngt.domain.Transfer;
import io.mngt.services.AccountingService;

@CrossOrigin("*")
@RestController
public class AccountingController {

  @Autowired
  private AccountingService accountingService;

  @GetMapping
  @RequestMapping("/getShortAccountBalances")
  @ResponseBody
  public List<BalanceILS> getShortAccountBalancesILS(@RequestParam( name = "code") String hashcode) {
    return accountingService.findLast5BalanceIlsListByHashcode(Integer.parseInt(hashcode));
  }
  

  @GetMapping
  @RequestMapping("/getLongAccountBalances")
  @ResponseBody
  public List<BalanceILS> getLongAccountBalancesILS(@RequestParam(name = "code") String hashcode) {
    return accountingService.findBalanceIlsListByHashcode(Integer.parseInt(hashcode));
  }

  @PostMapping
  @RequestMapping("/verifyTransferFromAccount")
  @ResponseBody
  public Transfer isTransferPossible(@RequestBody Transfer data) {
    return accountingService.isTransferPossible(data);
  }

  @PostMapping
  @RequestMapping("/setTransfer")
  @ResponseBody
  public Transaction setTransfer(@RequestBody Transfer data) {
    return accountingService.setTransaction(data);
  }
}