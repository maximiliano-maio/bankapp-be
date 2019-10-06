package io.mngt.jobs;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.mngt.services.AccountingService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TransactionJob {

  @Autowired
  private AccountingService accountingService;

  // Each day at 20 hs (for PROD env.)
  // @Scheduled(cron="0 0 20 1/1 * ?")

  @Scheduled(cron = "0 0/15 * 1/1 * ?")
  public void executeTransactions() {
    log.info("Transaction batch job is running...");
    accountingService.doTransaction();

    log.info("Transaction batch job has finished");
  }

  @Scheduled(cron = "0 0/40 * 1/1 * ?")
  public void buildOutgoingTransactionFile() throws JsonProcessingException {
    log.info("Building Outgoing transaction file...");
    accountingService.getOutgoingTransactions();
    log.info("Outgoing transaction's finished...");
  }

  @Scheduled(cron = "0 0/1 * 1/1 * ?")
  public void executeStandingOrders(){
    log.info("Standing orders job is running...");
    accountingService.doStandingOrder();
    accountingService.setNextStandingOrder();
    log.info("Standing orders job has finished...");
  }

  @Scheduled(cron = "0 0/10 * 1/1 * ?")
  public void setFirstAccount() {
    // TODO: Set first account for new clients
    // TODO: Set first movement about new account.
  }

  
}