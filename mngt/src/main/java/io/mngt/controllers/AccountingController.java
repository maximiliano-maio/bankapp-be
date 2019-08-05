package io.mngt.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.mngt.entity.Transfer;
import io.mngt.exceptions.NotFoundException;
import io.mngt.entity.BalanceILS;
import io.mngt.entity.StandingOrder;
import io.mngt.entity.Transaction;
import io.mngt.services.AccountingService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

  @PostMapping
  @RequestMapping("/setStandingOrder")
  public StandingOrder setStandingOrder(@RequestBody StandingOrder data) {
    return accountingService.setStandingOrder(data);
  }

  @RequestMapping(value = "/outgoingTransactions", produces = MediaType.APPLICATION_XML_VALUE)
  public List<Transaction> getOutgoungTransactionList() {
    return accountingService.getOutgoingTransactions();
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public void handleNotFound(Exception e) {
    log.info("Not found exception, " + e.getMessage());
  }
  
}