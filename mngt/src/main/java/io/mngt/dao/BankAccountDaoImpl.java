package io.mngt.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import io.mngt.domain.BankAccount;

@Component
public class BankAccountDaoImpl {


  @PersistenceContext
  private EntityManager em;
  
  public void persist(BankAccount bankAccount) {
    em.persist(bankAccount);
  }

  
  


}