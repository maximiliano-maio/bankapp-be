package io.mngt.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.mngt.dao.BalanceDao;
import io.mngt.dao.BankAccountDao;
import io.mngt.dao.StandingOrderDao;
import io.mngt.dao.TransactionDao;
import io.mngt.entity.BalanceILS;
import io.mngt.entity.BankAccount;
import io.mngt.entity.Client;
import io.mngt.entity.Credential;
import io.mngt.entity.StandingOrder;
import io.mngt.entity.Transaction;
import io.mngt.entity.Transfer;
import io.mngt.repositories.EnvironmentRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountingServiceImpl implements AccountingService {

  @Autowired
  private CredentialService credentialService;
  @Autowired
  private BalanceDao balanceDao;
  @Autowired
  private BankAccountDao bankAccountDao;
  @Autowired
  private TransactionDao transactionDao;
  @Autowired
  private StandingOrderDao standingOrderDao;
  @Autowired
  private EnvironmentRepository environmentRepository;
  @Autowired
  private Transaction transaction;
  @Autowired
  private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
  
  @Transactional(readOnly = true)
  @Override
  public List<BalanceILS> findBalanceIlsListByHashcode(int hashcode) {
    Client c = findClientByHashcode(hashcode);
    if (c == null) return null;

    return balanceDao.findLastBalancesByClient(c, 20);
  }

  @Transactional(readOnly = true)
  public List<BalanceILS> findLast5BalanceIlsListByHashcode(int hashcode) {
    Client c = findClientByHashcode(hashcode);
    if (c == null) return null;

    return balanceDao.findLastBalancesByClient(c, 5);
  }

  @Transactional(readOnly = true)
  @Override
  public Client findClientByHashcode(int hashcode) {
    Credential credential = credentialService.findCredentialByHashcode(hashcode);
    if (credential == null) return null;

    Client client = credential.getClient();
    if (client == null) return null;

    return client;
  }

  @Transactional(readOnly = true)
  @Override
  public BalanceILS findLastBalanceByHashcode(int hashcode) {
    Client c = findClientByHashcode(hashcode);
    return balanceDao.findLastBalancesByClient(c, 1).get(0);
  }

  @Transactional(readOnly = true)
  @Override
  public BalanceILS findLastBalanceByClient(Client c) {
    List<BalanceILS> balanceIlsList = balanceDao.findLastBalancesByClient(c, 1);
    if(balanceIlsList.size()>0) return balanceIlsList.get(0);

    return null;
  }

  @Transactional(readOnly = false)
  @Override
  public Transaction setTransaction(Transfer data) {
    // Debit account

    int hashcode = Integer.parseInt(data.getHashcode());
    Client c = findClientByHashcode(hashcode);
    BankAccount b = bankAccountDao.findBankAccountByClient(c);
    int debitAccount = b.getBankAccountNumber();
    
    // Credit account
    int creditAccount = data.getAccountNumber();

    transaction.setDebitAccount(debitAccount);
    transaction.setCreditAccount(creditAccount);
    transaction.setAmount(data.getAmount());
    transaction.setDate(new Date());
    transaction.setStatus(0);

    return transactionDao.save(transaction);
  }

  @Override
  public void doTransaction(){
    List<Transaction> transactionList = transactionDao.findTransactionByStatus(0);
    int OUTGOING_BANK_ACCOUNT = Integer.parseInt(environmentRepository.findByKey("OUTGOING_BANK_ACCOUNT").getValue());
    for (Transaction t : transactionList) {
      // ---- Debit ----
      Client clientDebitAccount = findClientByBankAccount(t.getDebitAccount());
      debitAccount(clientDebitAccount, t.getAmount(), "העברה בנקאית");
      debitAccount(clientDebitAccount, 20, "עמלה בנקאית");

      // ---- Credit ----
      Client clientCreditAccount = findClientByBankAccount(t.getCreditAccount());
      if (clientCreditAccount == null) {
        
        clientCreditAccount = findClientByBankAccount(OUTGOING_BANK_ACCOUNT);
        t.setAccountExternal(true);
      } else {
        t.setAccountExternal(false);
      }

      creditAccount(clientCreditAccount, t.getAmount(), "העברה בנקאית");
      Client creditBankAccount = findClientByBankAccount(OUTGOING_BANK_ACCOUNT);
      creditAccount(creditBankAccount, 20, "עמלה בנקאית");

      t.setStatus(1);
      transactionDao.save(t);
    }
    
  }

  private BalanceILS creditAccount(Client client, int amount, String description) {
    
    BalanceILS lastBalance = findLastBalanceByClient(client);
    
    BalanceILS creditBalance = new BalanceILS();
    creditBalance.setClient(client);
    creditBalance.setDebt(0);
    creditBalance.setDate(new Date());
    creditBalance.setCredit(amount);
    creditBalance.setDescription(description);
    creditBalance.setBalance(lastBalance.getBalance() + amount );
    return balanceDao.save(creditBalance);
  }

  private BalanceILS debitAccount(Client client, int amount, String description) {
    BalanceILS lastBalance = findLastBalanceByClient(client);

    BalanceILS debitBalance = new BalanceILS();
    debitBalance.setClient(client);
    debitBalance.setBalance(lastBalance.getBalance() - amount );
    debitBalance.setCredit(0);
    debitBalance.setDate(new Date());
    debitBalance.setDebt(amount);
    debitBalance.setDescription(description);
    return balanceDao.save(debitBalance);
  }

  @Transactional(readOnly = true)
  @Override
  public Transfer isTransferPossible(Transfer data) {
    int hashcode = Integer.parseInt(data.getHashcode());
    BalanceILS lastBalanceILS = findLastBalanceByHashcode(hashcode);
    
    Transfer returnedData = data;
    int balanceAvailable = lastBalanceILS.getBalance();
    // TODO: Get a better way to set commissions
    int amountToTransfer = 20 + data.getAmount();

    if (balanceAvailable < amountToTransfer) {
      returnedData.setTransferPossible(false);
      returnedData.setCommission(0);
    } else {
      returnedData.setTransferPossible(true);
      returnedData.setCommission(20);
    }
    return returnedData;
  }

  @Transactional(readOnly = true)
  @Override
  public Client findClientByBankAccount(int bankAccountNumber) {
    BankAccount bankAccount = bankAccountDao.findBankAccountByAccountNumber(bankAccountNumber);
    if (bankAccount == null) return null;

    Client client = bankAccount.getClient();
    return client;
  }

  @Override
  public List<Transaction> getOutgoingTransactions() {
    return transactionDao.findTransactionByExternalAccount(true);
  }

  @Override
  public StandingOrder setStandingOrder(StandingOrder data, String hash) {
    int hashcode = Integer.parseInt(hash);
    Client client = findClientByHashcode(hashcode);

    StandingOrder standingOrder = new StandingOrder();
    standingOrder.setClient(client);
    standingOrder.setAmount(data.getAmount());
    standingOrder.setCompanyName(data.getCompanyName());
    
    String dateString = simpleDateFormat.format(data.getDate());
    Date date;
    try {
      date = simpleDateFormat.parse(dateString);
      standingOrder.setDate(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    
    standingOrder.setFrecuency(data.getFrecuency());
    standingOrder.setStatus(0);
    
    return standingOrderDao.save(standingOrder);
  }

  @Override
  public void doStandingOrder() {
    Date today = getTodayDate();
    List<StandingOrder> standingOrderList = standingOrderDao.findAllStandingOrdersByDateAndStatus(today, 0);
    if (standingOrderList == null) return; 
    
    int INCOMING_BANK_ACCOUNT = Integer.parseInt(environmentRepository.findByKey("INCOMING_BANK_ACCOUNT").getValue());
    Client bankAccount = findClientByBankAccount(INCOMING_BANK_ACCOUNT);

    for (StandingOrder so : standingOrderList) {
      log.info("Id: " + so.getId() + ". Date: " + so.getDate());

      debitAccount(so.getClient(), so.getAmount(), "תשלום הוראת קבע בחברה:" + so.getCompanyName());
      creditAccount(bankAccount, so.getAmount(), "תשלום הוראת קבע בחברה:" + so.getCompanyName());
      so.setStatus(1);
      standingOrderDao.save(so);
    }
    
  }

  @Override
  public void setNextStandingOrder() {
    Date today = getTodayDate();
    List<StandingOrder> standingOrderList = standingOrderDao.findAllStandingOrdersByDateAndStatus(today, 1);
    if (standingOrderList == null) return;
    Calendar calendar = Calendar.getInstance();
    for(StandingOrder so : standingOrderList) {
      calendar.setTime(so.getDate());

      switch (so.getFrecuency()) {
        case WEEKLY:
          calendar.add(Calendar.DAY_OF_MONTH, 7);
          so.setDate(calendar.getTime());
          break;
        case MONTHLY:
          calendar.add(Calendar.MONTH, 1);
          so.setDate(calendar.getTime());
          break;
        case YEARLY:
          calendar.add(Calendar.YEAR, 1);
          so.setDate(calendar.getTime());
          break;
        default:
          break;
      }
      so.setStatus(0);
      standingOrderDao.save(so);
    }
  }

  static Date getTodayDate() {
    Date today = new Date();

    try {
      today = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return today;
  }
  

}