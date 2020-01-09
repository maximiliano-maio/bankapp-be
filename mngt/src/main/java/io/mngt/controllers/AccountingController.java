package io.mngt.controllers;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import io.mngt.entity.BalanceILS;
import io.mngt.entity.StandingOrder;
import io.mngt.entity.Transaction;
import io.mngt.entity.Transfer;
import io.mngt.exceptions.NotFoundException;
import io.mngt.services.AccountingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
@Api(value = "Accounting", description = "Data service operations on accounting", tags = ("accounting, balance, standing order, transaction"))
public class AccountingController {

  private final static Logger logger = LoggerFactory.getLogger(AccountingController.class);

  @Autowired
  private AccountingService accountingService;

  @GetMapping
  @RequestMapping("/getShortAccountBalances")
  @ResponseBody
  @ApiOperation(value = "Get brief balance", notes = "Get the last balances", nickname = "getShortBalance")
  public List<BalanceILS> getShortAccountBalancesILS(@RequestParam(name = "code") String hashcode) {
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
  public StandingOrder setStandingOrder(@RequestBody StandingOrder data, @RequestParam(name = "code") String hashcode) {
    return accountingService.setStandingOrder(data, hashcode);
  }

  @GetMapping
  @RequestMapping(value = "/outgoingTransactionsXMLFile", produces = MediaType.APPLICATION_XML_VALUE)
  public String getOutgoungTransactionListXML() throws JsonProcessingException {
    return accountingService.getOutgoingTransactions();
  }

  @GetMapping
  @RequestMapping(value = "/outgoingTransactionsJSONFile", produces = MediaType.APPLICATION_JSON_VALUE)
  public String getOutgoungTransactionListJSON() throws JsonProcessingException {
    return accountingService.getOutgoingTransactions();
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public void handleNotFound(Exception e) {
    logger.info("Not found exception, " + e.getMessage());
  }
  
}