package io.mngt.services;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isNotNull;

import io.mngt.dao.TransactionDao;
import io.mngt.entity.BalanceILS;
import io.mngt.entity.Client;
import io.mngt.entity.Credential;
import io.mngt.entity.Transaction;
import io.mngt.entity.Transfer;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class AccountServiceImplIntegrationTest {

  @TestConfiguration
  static class AccountingServiceImplTestContextConfiguration {

    @Bean
    public AccountingService accountingService() {
      return new AccountingServiceImpl();
    }
  }

  @Autowired
  private AccountingService accountingService;
  @Autowired
  private CredentialService credentialService;
  @Autowired
  private TransactionDao transactionDao;

  @Test
  public void givenHashcode_whenFindBalanceIlsListByHashcode_thenReturnBalanceILSList(){
    Credential credential = credentialService.login("maxi", "maio");
    int hashcode = credential.getHashcode();

    List<BalanceILS> list = accountingService.findBalanceIlsListByHashcode(hashcode);
    assertThat(list).isNotNull();

  }

  @Test
  public void givenHashcode_whenFindClientByHashcode_thenReturnClient(){
    Credential credential = credentialService.login("maxi", "maio");
    int hashcode = credential.getHashcode();
    Client client = accountingService.findClientByHashcode(hashcode);
    assertThat(client.getFirstName()).isEqualTo("Maxi");
  }

  @Test
  public void givenHashcode_whenFindLastBalanceByHashcode_thenReturnBalanceIls() {
    Credential credential = credentialService.login("maxi", "maio");
    int hashcode = credential.getHashcode();
    BalanceILS balanceILS = accountingService.findLastBalanceByHashcode(hashcode);
    assertThat(balanceILS.getBalance()).isEqualTo(76279);
  }

  @Test
  public void givenClient_whenFindLastBalanceByClient_thenReturnBalanceIls() {
    Credential credential = credentialService.login("maxi", "maio");
    Client client = credentialService.findCredentialByHashcode(credential.getHashcode()).getClient();
    BalanceILS balanceILS = accountingService.findLastBalanceByClient(client);
    assertThat(balanceILS.getBalance()).isEqualTo(76279);
  }

  @Test
  public void givenTransfer_whenSetTransaction_thenReturnTransaction(){
    Transfer data = new Transfer();
    Credential credential = credentialService.login("maxi", "maio");
    data.setHashcode(Integer.toString(credential.getHashcode()));
    data.setAmount(100);
    data.setAccountNumber(200200);

    Transaction transaction = accountingService.setTransaction(data);
    assertThat(transaction.getCreditAccount()).isEqualTo(200200);
  }

  @Transactional
  @Test
  public void givenClient_Amount_Description_whenCreditAccount_thenReturnBalanceIls(){
    Credential credential = credentialService.login("maxi", "maio");
    Client client = credentialService.findCredentialByHashcode(credential.getHashcode()).getClient();
    int amount = 100;
    String description = "Credit Account Integration Test";
    BalanceILS LastBalance = accountingService.findLastBalanceByClient(client);
    
    BalanceILS balance = accountingService.creditAccount(client, amount, description);
    assertThat(balance.getBalance()).isEqualTo(LastBalance.getBalance() + amount);
  }

  @Transactional
  @Test
  public void givenClient_Amount_Description_whenDebitAccount_thenReturnBalanceIls() {
    Credential credential = credentialService.login("maxi", "maio");
    Client client = credentialService.findCredentialByHashcode(credential.getHashcode()).getClient();
    int amount = 100;
    String description = "Debit Account Integration Test";
    BalanceILS LastBalance = accountingService.findLastBalanceByClient(client);

    BalanceILS balance = accountingService.debitAccount(client, amount, description);
    assertThat(balance.getBalance()).isEqualTo(LastBalance.getBalance() - amount);
  }

  @Test
  public void givenValidTransfer_whenIsTransferPossible_thenReturnTrue(){
    Transfer data = new Transfer();
    Credential credential = credentialService.login("maxi", "maio");
    data.setHashcode(Integer.toString(credential.getHashcode()));
    data.setAmount(100);

    Transfer transferReturned = accountingService.isTransferPossible(data);
    assertThat(transferReturned.isTransferPossible()).isTrue();
  }

  @Test
  public void givenInvalidTransfer_whenIsTransferPossible_thenReturnFalse() {
    Transfer data = new Transfer();
    Credential credential = credentialService.login("maxi", "maio");
    data.setHashcode(Integer.toString(credential.getHashcode()));
    data.setAmount(100000);

    Transfer transferReturned = accountingService.isTransferPossible(data);
    assertThat(transferReturned.isTransferPossible()).isFalse();
  }

  @Test
  public void givenExistentBankAccount_whenFindClientByBankAccount_thenReturnClient(){
    int bankAccount = 100200;
    Client client = accountingService.findClientByBankAccount(bankAccount);
    assertThat(client).isNotNull();
  }

  @Test
  public void givenNonExistentBankAccount_whenFindClientByBankAccount_thenReturnNull() {
    int bankAccount = 100;
    Client client = accountingService.findClientByBankAccount(bankAccount);
    assertThat(client).isNull();
  }

  @Test
  public void whenDoTransaction_thenUpdateTransaction(){
    // TODO: Write test 
  }

  @Test
  public void whenDoTransaction_thenDebitBalance() {
    // TODO: Write test
  }

  @Test
  public void whenDoTransaction_thenCreditBalance() {
    // TODO: Write test
  }

  @Transactional
  @Test
  public void whenGetOutgoingTransactions_thenReturnTransactionList(){
    Transaction transaction = new Transaction();
    transaction.setId(1L);
    transaction.setAccountExternal(true);
    transactionDao.save(transaction);
    
    List<Transaction> list = accountingService.getOutgoingTransactions();
    assertThat(list).hasSize(1); 
  }

  @Test
  public void givenStandingOrderAndHashcode_whenSetStandingOrder_thenReturnStandingOrder(){
    // TODO: Write test
  }

  @Test
  public void whenDoStandingOrder_thenDebitAccount(){
    // TODO: Write test
  }

  @Test
  public void whenDoStandingOrder_thenCreditAccount() {
    // TODO: Write test
  }

  @Test
  public void whenDoStandingOrder_thenUpdateStandingOrder() {
    // TODO: Write test
  }

  @Test
  public void whenSetNextStandingOrder_thenCreateStandingOrder() {
    // TODO: Write test
  }

}