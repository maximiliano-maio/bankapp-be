package io.mngt.jobs;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.mngt.services.AccountingService;


@Component
public class TransactionJob {

  @Autowired
  private AccountingService accountingService;
  @Autowired
  private JobLauncher jobLauncher;
  @Autowired
  private BeanFactory beanFactory;

  private final static Logger logger = LoggerFactory.getLogger("TransactionJob.class");

  // Each day at 20 hs (for PROD env.)
  // @Scheduled(cron="0 0 20 1/1 * ?")

  @Scheduled(cron = "0 0/1 * 1/1 * ?")
  public void executeTransactions() {
    logger.info("Transaction batch job is running...");
    accountingService.doTransaction();

    logger.info("Transaction batch job has finished");
  }

  // SOAP WS implemented to transfer transaction's information
  // @Scheduled(cron = "0 0/40 * 1/1 * ?")
  public void buildOutgoingTransactionFile() throws JsonProcessingException {
    logger.info("Building Outgoing transaction file...");
    accountingService.getOutgoingTransactions();
    logger.info("Outgoing transaction's finished...");
  }

  @Scheduled(cron = "0 0/40 * 1/1 * ?")
  public void executeStandingOrders(){
    logger.info("Standing orders job is running...");
    accountingService.doStandingOrder();
    accountingService.setNextStandingOrder();
    logger.info("Standing orders job has finished...");
  }

  @Scheduled(cron = "0 0/50 * 1/1 * ?")
  public void setFirstAccount() {
    // TODO: Set first account for new clients
    // TODO: Set first movement about new account.
  }

  @Scheduled(cron = "0 0/10 * 1/1 * ?")
  public void incomingTransactionFile() throws Exception{
    Job job = (Job) beanFactory.getBean("processIncomingTransactionJob");
    JobExecution jobExecution = jobLauncher.run(job, new JobParametersBuilder().toJobParameters());
    
    
  }

  
}