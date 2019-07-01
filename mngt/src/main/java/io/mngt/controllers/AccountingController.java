package io.mngt.controllers;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.mngt.domain.BalanceILS;
import io.mngt.services.AccountingService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin("*")
@RestController
public class AccountingController {

  @Autowired
  private AccountingService accountingService;

  @GetMapping
  @RequestMapping("/getAccounting")
  @ResponseBody
  public List<BalanceILS> getAccountingILS(@RequestParam( name = "code") String hashcode) {
    return accountingService.getLocalAccountBalance(Integer.parseInt(hashcode));
  }
  
}