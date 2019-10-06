package io.mngt.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import io.mngt.business.externaltransactions.TransactionXML;
import io.mngt.dao.StandingOrderDaoImpl;
import io.mngt.dao.TransactionDao;
import io.mngt.entity.BalanceILS;
import io.mngt.entity.Client;
import io.mngt.entity.Credential;
import io.mngt.entity.StandingOrder;
import io.mngt.entity.StandingOrderFrecuency;
import io.mngt.entity.Transaction;
import io.mngt.entity.Transfer;
import io.mngt.repositories.EnvironmentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class AccountServiceImplIntegrationTest {

  @TestConfiguration
  static class TestContextConfiguration {

    @Bean
    public AccountingService accountingService() {
      return new AccountingServiceImpl();
    }

    @Bean
    public SimpleDateFormat simpleDateFormat() {
      return new SimpleDateFormat("yyyy-MM-dd");
    }
  }

  @Autowired
  private AccountingService accountingService;
  @Autowired
  private CredentialService credentialService;
  @Autowired
  private TransactionDao transactionDao;
  @Autowired
  private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");;
  @Autowired
  private EnvironmentRepository environmentRepository;
  @Autowired
  private StandingOrderDaoImpl standingOrderDaoImpl;
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void givenHashcode_whenFindBalanceIlsListByHashcode_thenReturnBalanceILSList() {
    Credential credential = credentialService.login("maxi", "maio");
    int hashcode = credential.getHashcode();

    List<BalanceILS> list = accountingService.findBalanceIlsListByHashcode(hashcode);
    assertThat(list).isNotNull();

  }

  @Test
  public void givenHashcode_whenFindClientByHashcode_thenReturnClient() {
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
  public void givenTransfer_whenSetTransaction_thenReturnTransaction() {
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
  public void givenClient_Amount_Description_whenCreditAccount_thenReturnBalanceIls() {
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
  public void givenValidTransfer_whenIsTransferPossible_thenReturnTrue() {
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
  public void givenExistentBankAccount_whenFindClientByBankAccount_thenReturnClient() {
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

  @Rollback
  @Transactional
  @Test
  public void whenDoTransaction_thenUpdateTransaction() {
    Transfer data = new Transfer();
    Credential credential = credentialService.login("maxi", "maio");
    data.setHashcode(Integer.toString(credential.getHashcode()));
    data.setAmount(100);
    data.setAccountNumber(200200);
    data.setBeneficiary("ישראל ישראלי");
    data.setReason(1);

    accountingService.setTransaction(data);
    accountingService.doTransaction();
    List<Transaction> list = transactionDao.findTransactionByStatus(1);
    assertThat(list).hasSize(1);
  }

  @Rollback
  @Transactional
  @Test
  public void whenDoTransaction_thenDebitBalance() {
    Transfer data = new Transfer();
    Credential credential = credentialService.login("maxi", "maio");
    credential = credentialService.findCredentialByHashcode(credential.getHashcode());

    data.setHashcode(Integer.toString(credential.getHashcode()));
    data.setAmount(100);
    data.setAccountNumber(200200);
    data.setBeneficiary("ישראל ישראלי");
    data.setReason(1);
    int bankCommission = 20;
    BalanceILS balanceBeforeTransaction = accountingService.findLastBalanceByClient(credential.getClient());

    accountingService.setTransaction(data);
    accountingService.doTransaction();
    BalanceILS balanceAfterTransaction = accountingService.findLastBalanceByClient(credential.getClient());
    assertThat(balanceAfterTransaction.getBalance())
        .isEqualTo(balanceBeforeTransaction.getBalance() - data.getAmount() - bankCommission);
  }

  @Rollback
  @Transactional
  @Test
  public void whenDoTransaction_thenCreditBalance() {
    Transfer data = new Transfer();
    Credential credential = credentialService.login("maxi", "maio");

    data.setHashcode(Integer.toString(credential.getHashcode()));
    data.setAmount(100);
    data.setAccountNumber(200200);
    data.setBeneficiary("ישראל ישראלי");
    data.setReason(1);
    Client creditAccountClient = accountingService.findClientByBankAccount(data.getAccountNumber());
    BalanceILS balanceBeforeTransaction = accountingService.findLastBalanceByClient(creditAccountClient);

    accountingService.setTransaction(data);
    accountingService.doTransaction();
    BalanceILS balanceAfterTransaction = accountingService.findLastBalanceByClient(creditAccountClient);
    assertThat(balanceAfterTransaction.getBalance())
        .isEqualTo(balanceBeforeTransaction.getBalance() + data.getAmount());
  }

  @Rollback
  @Transactional
  @Test
  public void whenDoTransaction_thenCreditBalanceBankAccount() {
    Transfer data = new Transfer();
    Credential credential = credentialService.login("maxi", "maio");

    data.setHashcode(Integer.toString(credential.getHashcode()));
    data.setAmount(100);
    data.setAccountNumber(200200);
    data.setBeneficiary("ישראל ישראלי");
    data.setReason(1);
    int bankCommission = 20;
    int OUTGOING_BANK_ACCOUNT = Integer.parseInt(environmentRepository.findByKey("OUTGOING_BANK_ACCOUNT").getValue());
    Client bankCreditAccount = accountingService.findClientByBankAccount(OUTGOING_BANK_ACCOUNT);
    BalanceILS balanceBeforeTransaction = accountingService.findLastBalanceByClient(bankCreditAccount);

    accountingService.setTransaction(data);
    accountingService.doTransaction();
    BalanceILS balanceAfterTransaction = accountingService.findLastBalanceByClient(bankCreditAccount);
    assertThat(balanceAfterTransaction.getBalance()).isEqualTo(balanceBeforeTransaction.getBalance() + bankCommission);
  }

  @Transactional
  @Test
  public void whenGetOutgoingTransactions_thenReturnXMLString() throws JsonProcessingException, Exception {
    int OUTGOING_BANK_ACCOUNT = Integer.parseInt(environmentRepository.findByKey("OUTGOING_BANK_ACCOUNT").getValue());
    Date today = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
    Transaction transaction = new Transaction();
    transaction.setId(1L);
    transaction.setAmount(100);
    transaction.setDebitAccount(OUTGOING_BANK_ACCOUNT);
    transaction.setDate(today);
    transaction.setAccountExternal(true);
    transactionDao.save(transaction);
    
    transaction.setId(2L);
    transaction.setAmount(200);
    transaction.setDebitAccount(OUTGOING_BANK_ACCOUNT);
    transaction.setDate(today);
    transaction.setAccountExternal(true);
    transactionDao.save(transaction);

    String xml = accountingService.getOutgoingTransactions();
    TransactionXML transactionXML = objectMapper.readValue(xml, TransactionXML.class);
    assertThat(transactionXML.getTransactionQty()).isEqualTo(2);
  }

  @Rollback
  @Test
  public void givenStandingOrderAndHashcode_whenSetStandingOrder_thenReturnStandingOrder() throws Exception {
    Credential credential = credentialService.login("maxi", "maio");
    credential = credentialService.findCredentialByHashcode(credential.getHashcode());
    StandingOrder standingOrder = new StandingOrder();
    standingOrder.setClient(credential.getClient());
    standingOrder.setAmount(100);
    standingOrder.setCompanyName("CompanyIntegrationTest");
    standingOrder.setDate(simpleDateFormat.parse(simpleDateFormat.format(new Date())));

    StandingOrder standingOrderStored = accountingService.setStandingOrder(standingOrder, Integer.toString(credential.getHashcode()));
    assertThat(standingOrderStored.getAmount()).isEqualTo(100);
  }

  @Rollback
  @Transactional
  @Test
  public void whenDoStandingOrder_thenDebitAccount() throws Exception {
    Credential credential = credentialService.login("maxi", "maio");
    credential = credentialService.findCredentialByHashcode(credential.getHashcode());
    StandingOrder standingOrder = new StandingOrder();
    standingOrder.setClient(credential.getClient());
    standingOrder.setAmount(100);
    standingOrder.setCompanyName("CompanyIntegrationTest");
    standingOrder.setDate(simpleDateFormat.parse(simpleDateFormat.format(new Date())));
    standingOrder.setStatus(0);
    accountingService.setStandingOrder(standingOrder, Integer.toString(credential.getHashcode()));
    BalanceILS lastBalanceBeforeStandingOrder = accountingService.findLastBalanceByClient(credential.getClient());
    accountingService.doStandingOrder();

    BalanceILS lastBalance = accountingService.findLastBalanceByClient(credential.getClient());
    assertThat(lastBalance.getBalance())
      .isEqualTo(lastBalanceBeforeStandingOrder.getBalance() - standingOrder.getAmount());
  }

  @Rollback
  @Transactional
  @Test
  public void whenDoStandingOrder_thenCreditAccount() throws Exception {
    Credential credential = credentialService.login("maxi", "maio");
    credential = credentialService.findCredentialByHashcode(credential.getHashcode());
    StandingOrder standingOrder = new StandingOrder();
    standingOrder.setClient(credential.getClient());
    standingOrder.setAmount(100);
    standingOrder.setCompanyName("CompanyIntegrationTest");
    standingOrder.setDate(simpleDateFormat.parse(simpleDateFormat.format(new Date())));
    standingOrder.setStatus(0);
    accountingService.setStandingOrder(standingOrder, Integer.toString(credential.getHashcode()));
    
    int OUTGOING_BANK_ACCOUNT = Integer.parseInt(environmentRepository.findByKey("OUTGOING_BANK_ACCOUNT").getValue());
    Client bankCreditAccount = accountingService.findClientByBankAccount(OUTGOING_BANK_ACCOUNT);
    BalanceILS lastBalanceBeforeStandingOrder = accountingService
      .findLastBalanceByClient(bankCreditAccount);
    
    accountingService.doStandingOrder();

    BalanceILS lastBalance = accountingService.findLastBalanceByClient(bankCreditAccount);
    assertThat(lastBalance.getBalance())
        .isEqualTo(lastBalanceBeforeStandingOrder.getBalance() + standingOrder.getAmount());
  }

  @Rollback
  @Transactional
  @Test
  public void whenDoStandingOrder_thenUpdateStandingOrder() throws Exception {
    Credential credential = credentialService.login("maxi", "maio");
    credential = credentialService.findCredentialByHashcode(credential.getHashcode());
    Date today = simpleDateFormat.parse(simpleDateFormat.format(new Date()));

    StandingOrder standingOrder = new StandingOrder();
    standingOrder.setClient(credential.getClient());
    standingOrder.setAmount(100);
    standingOrder.setCompanyName("CompanyIntegrationTest");
    standingOrder.setDate(today);
    standingOrder.setStatus(0);
    accountingService.setStandingOrder(standingOrder, Integer.toString(credential.getHashcode()));

    accountingService.doStandingOrder();

    StandingOrder standingOrderUpdated = standingOrderDaoImpl.findAllStandingOrdersByDate(today).get(0);
    assertThat(standingOrderUpdated.getStatus()).isEqualTo(1);

  }

  @Rollback
  @Transactional
  @Test
  public void whenSetNextStandingOrder_thenCreateStandingOrder() throws Exception {
    Credential credential = credentialService.login("maxi", "maio");
    credential = credentialService.findCredentialByHashcode(credential.getHashcode());
    Date today = simpleDateFormat.parse(simpleDateFormat.format(new Date()));

    StandingOrder standingOrder = new StandingOrder();
    standingOrder.setClient(credential.getClient());
    standingOrder.setAmount(100);
    standingOrder.setCompanyName("CompanyIntegrationTest");
    standingOrder.setDate(today);
    standingOrder.setStatus(0);
    standingOrder.setId(10L);
    standingOrder.setFrecuency(StandingOrderFrecuency.WEEKLY);
    accountingService.setStandingOrder(standingOrder, Integer.toString(credential.getHashcode()));

    accountingService.doStandingOrder();
    accountingService.setNextStandingOrder();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(today);
    calendar.add(Calendar.DAY_OF_MONTH, 7);
    Date futureSODate = calendar.getTime();
    
    List<StandingOrder> list = standingOrderDaoImpl.findAllStandingOrdersByDate(futureSODate);
    assertThat(list).hasSize(1);
  }

}