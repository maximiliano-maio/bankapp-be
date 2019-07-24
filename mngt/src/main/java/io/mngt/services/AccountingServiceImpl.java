package io.mngt.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.mngt.dao.BalanceDao;
import io.mngt.dao.BalanceDaoImpl;
import io.mngt.dao.BankAccountDaoImpl;
import io.mngt.dao.TransactionDao;
import io.mngt.dao.TransactionDaoImpl;
import io.mngt.domain.BalanceILS;
import io.mngt.domain.BankAccount;
import io.mngt.domain.Client;
import io.mngt.domain.Credential;
import io.mngt.domain.Transaction;
import io.mngt.domain.Transfer;
import io.mngt.domain.business.TransferLogic;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountingServiceImpl implements AccountingService {

  @Autowired
  private CredentialService credentialService;
  @Autowired
  private BalanceDaoImpl balanceDaoImpl;
  @Autowired
  private BalanceDao balanceDao;
  @Autowired
  private BankAccountDaoImpl bankAccountDaoImpl;
  @Autowired
  private TransactionDao transactionDao;
  @Autowired
  private TransactionDaoImpl transactionDaoImpl;

  // TODO: A table will be built containing clients' and banks' accounts
  private static final int OUTGOING_BANK_ACCOUNT = 999991;
  private static final int INCOMING_BANK_ACCOUNT = 999990;

  @Transactional(readOnly = true)
  @Override
  public List<BalanceILS> findBalanceIlsListByHashcode(int hashcode) {
    Client c = findClientByHashcode(hashcode);
    if (c == null) return null;

    return c.getBalance();
  }

  @Transactional(readOnly = true)
  public List<BalanceILS> findLast5BalanceIlsListByHashcode(int hashcode) {
    Client c = findClientByHashcode(hashcode);
    if (c == null) return null;

    return balanceDaoImpl.findLastBalancesByClient(c, 5);
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
    return balanceDaoImpl.findLastBalancesByClient(c, 1).get(0);
  }

  @Transactional(readOnly = true)
  @Override
  public BalanceILS findLastBalanceByClient(Client c) {
    return balanceDaoImpl.findLastBalancesByClient(c, 1).get(0);
  }

  @Transactional(readOnly = false)
  @Override
  public Transaction setTransaction(Transfer data) {
    // Debit account

    int hashcode = Integer.parseInt(data.getHashcode());
    Client c = findClientByHashcode(hashcode);
    BankAccount b = bankAccountDaoImpl.findBankAccountByClient(c);
    int debitAccount = b.getBankAccountNumber();
    
    // Credit account
    int creditAccount = data.getAccountNumber();

    Transaction transaction = new Transaction(debitAccount, creditAccount, data.getAmount(), new Date() );
    return transactionDao.save(transaction);
  }

  @Override
  public void doTransaction(){
    List<Transaction> transactionList = transactionDaoImpl.findTransactionByStatus(0);
    log.info("*** Transactions to be performed: " + transactionList.size() + " ***");
    

    for(Transaction t: transactionList){
      Client clientDebitAccount = findClientByBankAccount(t.getDebitAccount());
      debitAccount(clientDebitAccount, t.getAmount());
      
      Client clientCreditAccount = findClientByBankAccount(t.getCreditAccount());
      if (clientCreditAccount == null) 
        clientCreditAccount = findClientByBankAccount(OUTGOING_BANK_ACCOUNT);
      
      creditAccount(clientCreditAccount, t.getAmount());
      
      t.setStatus(1);
      transactionDao.save(t);
    }
  }

  private BalanceILS creditAccount(Client client, int amount) {
    BalanceILS lastBalance = findLastBalanceByClient(client);
    
    if(lastBalance == null){
      lastBalance = new BalanceILS();
      lastBalance.setBalance(0);
    }
    
    // TODO: To improve drastically! Credit bank's account with commission deducted from client's account
    TransferLogic transfer = new TransferLogic();
    BalanceILS serviceCharge = transfer.getServiceCharge();
    Client bankAccount = findClientByBankAccount(INCOMING_BANK_ACCOUNT);
    serviceCharge.setClient(bankAccount);
    serviceCharge.setCredit(serviceCharge.getDebt());
    serviceCharge.setDebt(0);
    serviceCharge.setBalance(-serviceCharge.getBalance());
    balanceDao.save(serviceCharge);

    BalanceILS creditBalance = new BalanceILS();
    creditBalance.setClient(client);
    creditBalance.setDebt(0);
    creditBalance.setDate(new Date());
    creditBalance.setCredit(amount);
    creditBalance.setDescription("העברה בנקאית");
    creditBalance.setBalance(lastBalance.getBalance() + amount );
    return balanceDao.save(creditBalance);
  }

  private BalanceILS debitAccount(Client client, int amount) {
    BalanceILS lastBalance = findLastBalanceByClient(client);

    // To charge client with commission
    TransferLogic transfer = new TransferLogic(lastBalance);
    BalanceILS serviceCharge = transfer.getServiceCharge();
    serviceCharge.setClient(client);
    balanceDao.save(serviceCharge);

    BalanceILS debitBalance = new BalanceILS();
    debitBalance.setClient(client);
    debitBalance.setBalance(serviceCharge.getBalance() - amount );
    debitBalance.setCredit(0);
    debitBalance.setDate(new Date());
    debitBalance.setDebt(amount);
    debitBalance.setDescription("העברה בנקאית");
    return balanceDao.save(debitBalance);
     
  }

  @Transactional(readOnly = true)
  @Override
  public Transfer isTransferPossible(Transfer data) {
    BalanceILS balance = findLastBalanceByHashcode(Integer.parseInt(data.getHashcode()));
    TransferLogic transfer = new TransferLogic(data, balance);
    return transfer.isTransferPossible();
    
  }

  @Transactional(readOnly = true)
  @Override
  public Client findClientByBankAccount(int bankAccountNumber) {
    BankAccount bankAccount = bankAccountDaoImpl.findBankAccountByAccountNumber(bankAccountNumber);
    if (bankAccount == null) return null;

    Client client = bankAccount.getClient();
    return client;
  }
  

}