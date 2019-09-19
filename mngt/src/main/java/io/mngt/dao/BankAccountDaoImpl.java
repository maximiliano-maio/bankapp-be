package io.mngt.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.mngt.entity.BankAccount;
import io.mngt.entity.Client;

@Repository
public class BankAccountDaoImpl implements BankAccountDao {

  private static final String FIND_BANK_ACCOUNT_BY_CLIENT = "SELECT b FROM BankAccount b WHERE b.client = :client";
  private static final String FIND_BANK_ACCOUNT_AND_CLIENT_ASSOCIATED_BY_ACCOUNT_NUMBER= "SELECT b FROM BankAccount b JOIN FETCH b.client WHERE b.bankAccountNumber = :bankAccountNumber";

  @PersistenceContext
  private EntityManager em;

  @Autowired
  private ClientOperationsLogDao clientOperationsLogDao;

  // TODO: Filter bankAccount by status (i.e. active, disabled)
  @Override
  public BankAccount findBankAccountByClient(Client client){
    return em.createQuery(FIND_BANK_ACCOUNT_BY_CLIENT, BankAccount.class)
      .setParameter("client", client)
      .getResultList().get(0);
  }
  
  @Override
  public BankAccount findBankAccountByAccountNumber(int bankAccountNumber){
   
    List<BankAccount> bankAccountList = em.createQuery(FIND_BANK_ACCOUNT_AND_CLIENT_ASSOCIATED_BY_ACCOUNT_NUMBER, BankAccount.class)
    .setParameter("bankAccountNumber", bankAccountNumber)
    .getResultList();

    if(bankAccountList.size() > 0) return bankAccountList.get(0);

    return null;
  }
  


}