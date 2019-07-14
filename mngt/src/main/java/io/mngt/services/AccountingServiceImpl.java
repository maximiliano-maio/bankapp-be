package io.mngt.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.mngt.dao.BalanceDao;
import io.mngt.dao.BalanceDaoImpl;
import io.mngt.dao.BankAccountDao;
import io.mngt.dao.ClientDao;
import io.mngt.dao.ClientDaoImpl;
import io.mngt.domain.BalanceILS;
import io.mngt.domain.BankAccount;
import io.mngt.domain.Client;
import io.mngt.domain.Credential;
import io.mngt.domain.Transfer;
import io.mngt.domain.business.TransferLogic;
import io.mngt.repositories.BankAccountRepository;
import io.mngt.repositories.ClientRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@Service
public class AccountingServiceImpl implements AccountingService {

  @Autowired
  private CredentialService credentialService;

  @Autowired
  private ClientRepository clientRepository;
  @Autowired
  private BalanceDaoImpl balanceDaoImpl;
  @Autowired
  private BalanceDao balanceDao;
  @Autowired
  private BankAccountDao bankAccountDao;
  @Autowired
  private BankAccountRepository bankAccountRepository;
  @Autowired
  private ClientDaoImpl clientDaoImpl;


  @Transactional(readOnly = true)
  @Override
  public List<BalanceILS> getLocalAccountBalance(int hashcode) {
    Client c = getClientUsingHashcode(hashcode);
    if (c == null)
      return null;

    return c.getBalance();

  }

  @Transactional(readOnly = true)
  public List<BalanceILS> findLast5(int hashcode) {
    Client c = getClientUsingHashcode(hashcode);
    if (c == null)
      return null;

    return balanceDaoImpl.findLastBalancesByClient(c, 5);
  }

  @Transactional(readOnly = true)
  private Client getClientUsingHashcode(int hashcode) {
    Credential credential = credentialService.getCredential(hashcode);
    if (credential == null)
      return null;

    Client client = credential.getClient();
    if (client == null)
      return null;

    return client;
  }

  @Transactional(readOnly = true)
  @Override
  public BalanceILS getLastBalanceUsingHashcode(int hashcode) {
    Client c = getClientUsingHashcode(hashcode);
    return balanceDaoImpl.findLastBalancesByClient(c, 1).get(0);
  }

  @Transactional(readOnly = true)
  @Override
  public BalanceILS getLastBalanceUsingClient(Client c) {
    return balanceDaoImpl.findLastBalancesByClient(c, 1).get(0);
  }

  @Override
  public BalanceILS setTransfer(Transfer data) {
    int hashcode = Integer.parseInt(data.getHashcode());
    Client c = getClientUsingHashcode(hashcode);
    
    // Testing this function. Result will be displayed on Console
    getClientFromBankAccount(data.getAccountNumber());

    BalanceILS lastBalance = getLastBalanceUsingHashcode(hashcode);
    TransferLogic transfer = new TransferLogic(data, lastBalance);
    BalanceILS serviceCharge = transfer.getServiceCharge();
    serviceCharge.setClient(c);
    balanceDao.save(serviceCharge);

    BalanceILS balanceRelatedToTransfer = new BalanceILS();
    balanceRelatedToTransfer.setClient(c);
    balanceRelatedToTransfer.setBalance(serviceCharge.getBalance() - data.getAmount());
    balanceRelatedToTransfer.setCredit(0);
    balanceRelatedToTransfer.setDate(new Date());
    balanceRelatedToTransfer.setDebt(data.getAmount());
    balanceRelatedToTransfer.setDescription("העברה בנקאית");

    return balanceDao.save(balanceRelatedToTransfer);
  }

  @Transactional(readOnly = true)
  @Override
  public Transfer isTransferPossible(Transfer data) {
    BalanceILS balance = getLastBalanceUsingHashcode(Integer.parseInt(data.getHashcode()));
    TransferLogic transfer = new TransferLogic(data, balance);
    return transfer.isTransferPossible();
    
  }

  @Transactional(readOnly = true)
  public void getClientFromBankAccount(int bankAccountNumber) {
    BankAccount bankAccount = bankAccountDao.findByBankAccountNumber(bankAccountNumber);
    Client client = clientDaoImpl.findClientByBankAccount(bankAccount)
    if (client != null) {
      log.info("client: " + client.getClientId());
      log.info("client " + client.getFirstName());  
    }
    
  }
  

}