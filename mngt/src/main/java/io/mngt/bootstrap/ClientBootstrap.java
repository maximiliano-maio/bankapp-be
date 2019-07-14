package io.mngt.bootstrap;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import io.mngt.domain.BalanceILS;
import io.mngt.domain.BankAccount;
import io.mngt.domain.CheckBookOrder;
import io.mngt.domain.Client;
import io.mngt.domain.ContactInfo;
import io.mngt.domain.Credential;
import io.mngt.domain.Loan;
import io.mngt.repositories.BalanceILSRepository;
import io.mngt.repositories.BankAccountRepository;
import io.mngt.repositories.CheckBookOrderRepository;
import io.mngt.repositories.ClientRepository;
import io.mngt.repositories.CredentialRepository;
import io.mngt.repositories.LoanRepository;

@Component
@Profile("default")
public class ClientBootstrap implements ApplicationListener<ContextRefreshedEvent> {

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

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    // Population for testing purpose:
    initSetClientData();
  }

  private void initSetClientData() {

    clientRepository.deleteAll();
    checkBookOrderRepository.deleteAll();
    credentialRepository.deleteAll();
    balanceILSRepository.deleteAll();
    loanRepository.deleteAll();
    checkBookOrderRepository.deleteAll();

    // Client 1:
    Client client = new Client("338011321", "Maxi", "Maio", "1");

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
    
    BankAccount bankAccount = new BankAccount();
    bankAccount.setClient(client);
    bankAccount.setStatus("active");
    bankAccount.setBankAccountNumber(100200);
    bankAccountRepository.save(bankAccount);

    bankAccount = new BankAccount();
    bankAccount.setClient(client);
    bankAccount.setStatus("disable");
    bankAccount.setBankAccountNumber(100300);
    bankAccountRepository.save(bankAccount);

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

    bankAccount = new BankAccount();
    bankAccount.setClient(client);
    bankAccount.setStatus("active");
    bankAccount.setBankAccountNumber(200200);
    bankAccountRepository.save(bankAccount);

    bankAccount = new BankAccount();
    bankAccount.setClient(client);
    bankAccount.setStatus("active");
    bankAccount.setBankAccountNumber(200300);
    bankAccountRepository.save(bankAccount);

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

    bankAccount = new BankAccount();
    bankAccount.setClient(client);
    bankAccount.setStatus("disable");
    bankAccount.setBankAccountNumber(300200);
    bankAccountRepository.save(bankAccount);

    bankAccount = new BankAccount();
    bankAccount.setClient(client);
    bankAccount.setStatus("disable");
    bankAccount.setBankAccountNumber(300300);
    bankAccountRepository.save(bankAccount);

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

    bankAccount = new BankAccount();
    bankAccount.setClient(client);
    bankAccount.setStatus("disable");
    bankAccount.setBankAccountNumber(400200);
    bankAccountRepository.save(bankAccount);

    bankAccount = new BankAccount();
    bankAccount.setClient(client);
    bankAccount.setStatus("active");
    bankAccount.setBankAccountNumber(400300);
    bankAccountRepository.save(bankAccount);

    setBalances(client);
    setLoans(client);
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

  private void setBalances(Client client) {
    BalanceILS balance = new BalanceILS();
    balance.setClient(client);

    balance.setDebt(8000);
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
    balance.setCredit(6000);
    date = new GregorianCalendar(2018, Calendar.APRIL, 3).getTime();
    balance.setDate(date);
    balance.setDescription("משכורת");
    balance.setBalance(59910);

    balanceILSRepository.save(balance);

    balance = new BalanceILS();
    balance.setClient(client);
    balance.setDebt(500);
    date = new GregorianCalendar(2018, Calendar.APRIL, 12).getTime();
    balance.setDate(date);
    balance.setDescription("מסעדה 2");
    balance.setBalance(50410);

    balanceILSRepository.save(balance);

    balance = new BalanceILS();
    balance.setClient(client);
    balance.setDebt(150);
    date = new GregorianCalendar(2018, Calendar.APRIL, 15).getTime();
    balance.setDate(date);
    balance.setDescription("בר 1");
    balance.setBalance(50260);

    balanceILSRepository.save(balance);

    balance = new BalanceILS();
    balance.setClient(client);
    balance.setDebt(500);
    date = new GregorianCalendar(2018, Calendar.APRIL, 18).getTime();
    balance.setDate(date);
    balance.setDescription("כלי בית");
    balance.setBalance(49760);

    balanceILSRepository.save(balance);

    balance = new BalanceILS();
    balance.setClient(client);
    balance.setCredit(8000);
    date = new GregorianCalendar(2018, Calendar.MAY, 2).getTime();
    balance.setDate(date);
    balance.setDescription("משכורת");
    balance.setBalance(57760);

    balanceILSRepository.save(balance);

    balance = new BalanceILS();
    balance.setClient(client);
    balance.setDebt(700);
    date = new GregorianCalendar(2018, Calendar.MAY, 12).getTime();
    balance.setDate(date);
    balance.setDescription("מסעדה 4");
    balance.setBalance(57060);

    balanceILSRepository.save(balance);

    balance = new BalanceILS();
    balance.setClient(client);
    balance.setDebt(60);
    date = new GregorianCalendar(2018, Calendar.MAY, 28).getTime();
    balance.setDate(date);
    balance.setDescription("מסעדה 5");
    balance.setBalance(57000);

    balanceILSRepository.save(balance);

    balance = new BalanceILS();
    balance.setClient(client);
    balance.setCredit(10000);
    date = new GregorianCalendar(2018, Calendar.JUNE, 2).getTime();
    balance.setDate(date);
    balance.setDescription("משכורת");
    balance.setBalance(67000);

    balanceILSRepository.save(balance);

  }

}