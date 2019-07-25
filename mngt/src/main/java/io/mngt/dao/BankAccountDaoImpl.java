package io.mngt.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import io.mngt.domain.BankAccount;
import io.mngt.domain.Client;

@Component
public class BankAccountDaoImpl {

  private static final String FIND_BANK_ACCOUNT_BY_CLIENT = "SELECT b FROM BankAccount b WHERE b.client = :client";
  private static final String FIND_BANK_ACCOUNT_BY_ACCOUNT_NUMBER= "SELECT b FROM BankAccount b JOIN FETCH b.client WHERE b.bankAccountNumber = :bankAccountNumber";

  @PersistenceContext
  private EntityManager em;
  
  public void persist(BankAccount bankAccount) {
    em.persist(bankAccount);
  }

  public BankAccount findBankAccountByClient(Client client){
    return em.createQuery(FIND_BANK_ACCOUNT_BY_CLIENT, BankAccount.class)
      .setParameter("client", client)
      .getResultList().get(0);
  }
  
  public BankAccount findBankAccountByAccountNumber(int bankAccountNumber){
   
    List<BankAccount> bankAccountList = em.createQuery(FIND_BANK_ACCOUNT_BY_ACCOUNT_NUMBER, BankAccount.class)
    .setParameter("bankAccountNumber", bankAccountNumber)
    .getResultList();

    if(bankAccountList.size() > 0) return bankAccountList.get(0);

    return null;
  }
  


}