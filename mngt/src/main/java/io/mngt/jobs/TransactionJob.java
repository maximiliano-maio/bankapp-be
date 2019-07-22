package io.mngt.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.mngt.services.AccountingService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TransactionJob implements Job {

  @Autowired
  private AccountingService accountingService;
  

  @Override
  public void execute(JobExecutionContext arg0) throws JobExecutionException {
    log.info("Transaction batch job is running...");
    accountingService.doTransaction();

    log.info("Transaction batch job has finished");
  }

  
}