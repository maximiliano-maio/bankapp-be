package io.mngt.bootstrap;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import io.mngt.dao.TransactionDao;
import io.mngt.entity.BalanceILS;
import io.mngt.entity.BankAccount;
import io.mngt.entity.CheckBookOrder;
import io.mngt.entity.Client;
import io.mngt.entity.ContactInfo;
import io.mngt.entity.Credential;
import io.mngt.entity.Loan;
import io.mngt.entity.Transaction;
import io.mngt.repositories.BalanceILSRepository;
import io.mngt.repositories.BankAccountRepository;
import io.mngt.repositories.CheckBookOrderRepository;
import io.mngt.repositories.ClientRepository;
import io.mngt.repositories.CredentialRepository;
import io.mngt.repositories.LoanRepository;
import io.mngt.repositories.TransactionRepository;
import io.mngt.repositories.UserRepository;
import io.mngt.entity.User;

@Component
@Profile("default")
public class ClientBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  @PersistenceContext
  private EntityManager em;
  @Autowired
  private ClientRepository clientRepository;
  @Autowired
  private CheckBookOrderRepository checkBookOrderRepository;
  @Autowired
  private LoanRepository loanRepository;
  @Autowired
  private BalanceILSRepository balanceILSRepository;
  @Autowired
  private CredentialRepository credentialRepository;
  @Autowired
  private BankAccountRepository bankAccountRepository;
  @Autowired
  private TransactionRepository transactionRepository;
  @Autowired
  private TransactionDao transactionDao;
  @Autowired
  private UserRepository userRepository;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    // Population for testing purpose:
    initSetClientData();
    initSetTransactionData();
    initUsername();
    // Write population to files:
    try {
      initSetTransactionDataToTXT();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  private void initUsername() {
    userRepository.save(new User("maxi", "maio", "ADMIN"));
    userRepository.save(new User("user", "pass", "USER"));
    userRepository.save(new User("test", "test", "TEST"));
    userRepository.save(new User("mem", "mem", "ADMIN"));
  }

  private void initSetTransactionDataToTXT() throws IOException {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    String dateToFile = sdf.format(new Date());
    
    String path = "/home/max/Documents/projects/corebankapp/mngt/src/main/resources/files/transaction-" + dateToFile + ".txt";
    File file = new File(path);
    file.createNewFile();
    FileOutputStream fos = new FileOutputStream(file);
    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos));
    
    List<Transaction> list = transactionDao.findTransactionByStatus(1);
    
    bufferedWriter.write("id, debitAccount, creditAccount, amount, date");
    bufferedWriter.newLine();
    
    for(Transaction t : list) {
      bufferedWriter.write(t.toString());
      bufferedWriter.newLine();
    }
    bufferedWriter.flush();
    bufferedWriter.close();
  }

  private void initSetTransactionData() {
    LocalDate localDate = LocalDate.of(2019, 7, 10);
    Date date = java.sql.Date.valueOf(localDate);
    
    Transaction transaction = new Transaction();
    transaction.setDebitAccount(100100);
    transaction.setCreditAccount(200100);
    transaction.setDate(date);
    transaction.setStatus(1);
    transaction.setAmount(100);
    transaction.setAccountExternal(true);

    transactionRepository.save(transaction);

    localDate = LocalDate.of(2019, 6, 5);
    date = java.sql.Date.valueOf(localDate);
    transaction = new Transaction();
    transaction.setDebitAccount(200100);
    transaction.setCreditAccount(100100);
    transaction.setDate(date);
    transaction.setStatus(1);
    transaction.setAmount(500);
    transaction.setAccountExternal(true);

    transactionRepository.save(transaction);
  }

  private void initSetClientData() {
    
    clientRepository.deleteAll();
    checkBookOrderRepository.deleteAll();
    credentialRepository.deleteAll();
    balanceILSRepository.deleteAll();
    loanRepository.deleteAll();
    checkBookOrderRepository.deleteAll();

    // Client 1:
    Client client = new Client("338016777", "Maxi", "Maio", "1");

    Credential credential = new Credential("maxi", "maio", "maxi_maio@hotmail.com", "admin", 90);
    credential.setClient(client);
    credentialRepository.save(credential);

    ContactInfo clientContactInfo = new ContactInfo();
    clientContactInfo.setCellphone("0515819763");
    clientContactInfo.setEmail("maxi_maio@hotmail.com");
    clientContactInfo.setDistributionAgreement(2);
    client.setContactInfo(clientContactInfo);
    clientRepository.save(client);

    CheckBookOrder checks = new CheckBookOrder(100, "Ordered", "A", 0, 50, 50, "ILS");
    checks.setClient(client);
    checkBookOrderRepository.save(checks);
    
    BankAccount bankAccount = new BankAccount(client, 100200, "active");
    bankAccountRepository.save(bankAccount);

    bankAccount = new BankAccount(client, 100300, "disabled");
    bankAccountRepository.save(bankAccount);

    setInitBankAccount(client);
    setBalances(client);
    setLoans(client);


    // Client 2:
    client = new Client("101032700", "Israel", "Israeli", "1");

    clientContactInfo = new ContactInfo();
    clientContactInfo.setCellphone("0534292553");
    clientContactInfo.setEmail("ing.m.maio@gmail.com");
    clientContactInfo.setDistributionAgreement(1);
    client.setContactInfo(clientContactInfo);
    clientRepository.save(client);

    checks = new CheckBookOrder(101, "Delivered", "B", 101, 201, 100, "USD");
    checks.setClient(client);
    checkBookOrderRepository.save(checks);

    bankAccount = new BankAccount(client, 200200, "active");
    bankAccountRepository.save(bankAccount);

    bankAccount = new BankAccount(client, 200300, "active");
    bankAccountRepository.save(bankAccount);

    setInitBankAccount(client);
    setBalances(client);
    setLoans(client);

    // Client 3:
    client = new Client("101032701", "Moshe", "Klein", "5");

    clientContactInfo = new ContactInfo();
    clientContactInfo.setDistributionAgreement(5);

    client.setContactInfo(clientContactInfo);
    clientRepository.save(client);

    checks = new CheckBookOrder(102, "In branch", "A", 100, 150, 50, "USD");
    checks.setClient(client);
    checkBookOrderRepository.save(checks);

    bankAccount = new BankAccount(client, 300200, "disabled");
    bankAccountRepository.save(bankAccount);

    bankAccount = new BankAccount(client, 300300, "disabled");
    bankAccountRepository.save(bankAccount);

    setInitBankAccount(client);
    setBalances(client);
    setLoans(client);

    // Client 4:
    client = new Client("101032702", "Moshe", "Dayan", "4");

    clientContactInfo = new ContactInfo();
    clientContactInfo.setDistributionAgreement(4);

    client.setContactInfo(clientContactInfo);
    clientRepository.save(client);

    checks = new CheckBookOrder(103, "Returned", "C", 1000, 1200, 200, "ILS");
    checks.setClient(client);
    checkBookOrderRepository.save(checks);

    bankAccount = new BankAccount(client, 400200, "disabled");
    bankAccountRepository.save(bankAccount);

    bankAccount = new BankAccount(client, 400300, "active");
    bankAccountRepository.save(bankAccount);

    setInitBankAccount(client);
    setBalances(client);
    setLoans(client);

    // Bank account
    client = new Client("999999", "Branch", "100", "4");

    clientContactInfo = new ContactInfo();
    clientContactInfo.setCity("Tel Aviv");
    clientContactInfo.setEmail("branch_100@appbank.com");

    client.setContactInfo(clientContactInfo);
    clientRepository.save(client);
    // Incoming
    bankAccount = new BankAccount(client, 999990, "active");
    bankAccountRepository.save(bankAccount);
    // Outcoming
    bankAccount = new BankAccount(client, 999991, "active");
    bankAccountRepository.save(bankAccount);

    setInitBankAccount(client);

  }

  private void setLoans(Client client) {
    Loan loan = new Loan();
    loan = new Loan("Car", 20000, 60, (float) 3.4);
    loan.setClient(client);
    loanRepository.save(loan);

    loan = new Loan("Remodelation", 200000, 36, (float) 5.4);
    loan.setClient(client);
    loanRepository.save(loan);
  }

  private void setInitBankAccount(Client client) {
    BalanceILS balance = new BalanceILS();
    balance.setClient(client);

    balance.setDebt(0);
    balance.setCredit(0);

    Date date = new GregorianCalendar(2010, Calendar.MARCH, 5).getTime();
    balance.setDate(date);
    balance.setDescription("קיום חשבון חדש");
    balance.setBalance(0);

    balanceILSRepository.save(balance);
  }

  private void setBalances(Client client) {
    BalanceILS balance = new BalanceILS();
    balance.setClient(client);

    balance.setDebt(800);
    Date date = new GregorianCalendar(2018, Calendar.MARCH, 5).getTime();
    balance.setDate(date);
    balance.setDescription("קניות בשופרסל");
    balance.setBalance(54000);

    balanceILSRepository.save(balance);

    balance = new BalanceILS();
    balance.setClient(client);
    balance.setDebt(100);
    date = new GregorianCalendar(2018, Calendar.MARCH, 12).getTime();
    balance.setDate(date);
    balance.setDescription("מסעדה 1");
    balance.setBalance(53900);

    balanceILSRepository.save(balance);

    balance = new BalanceILS();
    balance.setClient(client);
    balance.setCredit(8000);
    date = new GregorianCalendar(2018, Calendar.APRIL, 3).getTime();
    balance.setDate(date);
    balance.setDescription("משכורת");
    balance.setBalance(61900);

    balanceILSRepository.save(balance);

    balance = new BalanceILS();
    balance.setClient(client);
    balance.setDebt(553);
    date = new GregorianCalendar(2018, Calendar.APRIL, 12).getTime();
    balance.setDate(date);
    balance.setDescription("מסעדה 2");
    balance.setBalance(61347);

    balanceILSRepository.save(balance);

    balance = new BalanceILS();
    balance.setClient(client);
    balance.setDebt(158);
    date = new GregorianCalendar(2018, Calendar.APRIL, 15).getTime();
    balance.setDate(date);
    balance.setDescription("בר 1");
    balance.setBalance(61189);

    balanceILSRepository.save(balance);

    balance = new BalanceILS();
    balance.setClient(client);
    balance.setDebt(500);
    date = new GregorianCalendar(2018, Calendar.APRIL, 18).getTime();
    balance.setDate(date);
    balance.setDescription("כלי בית");
    balance.setBalance(60689);

    balanceILSRepository.save(balance);

    balance = new BalanceILS();
    balance.setClient(client);
    balance.setCredit(8000);
    date = new GregorianCalendar(2018, Calendar.MAY, 2).getTime();
    balance.setDate(date);
    balance.setDescription("משכורת");
    balance.setBalance(68689);

    balanceILSRepository.save(balance);

    balance = new BalanceILS();
    balance.setClient(client);
    balance.setDebt(243);
    date = new GregorianCalendar(2018, Calendar.MAY, 12).getTime();
    balance.setDate(date);
    balance.setDescription("מסעדה 4");
    balance.setBalance(68446);

    balanceILSRepository.save(balance);

    balance = new BalanceILS();
    balance.setClient(client);
    balance.setDebt(167);
    date = new GregorianCalendar(2018, Calendar.MAY, 28).getTime();
    balance.setDate(date);
    balance.setDescription("מסעדה 5");
    balance.setBalance(68279);

    balanceILSRepository.save(balance);

    balance = new BalanceILS();
    balance.setClient(client);
    balance.setCredit(8000);
    date = new GregorianCalendar(2018, Calendar.JUNE, 2).getTime();
    balance.setDate(date);
    balance.setDescription("משכורת");
    balance.setBalance(76279);

    balanceILSRepository.save(balance);

  }

}