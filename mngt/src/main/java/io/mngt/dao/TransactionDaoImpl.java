package io.mngt.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import io.mngt.domain.Transaction;

public class TransactionDaoImpl {

  private static final String FIND_TRANSACTION_BY_STATUS = "SELECT t FROM Transaction t WHERE t.status = :status";

  @PersistenceContext
  private EntityManager em;

  public void persist(TransactionDao transactionDao){
    em.persist(transactionDao);
  }

  public List<Transaction> findTransactionByStatus(int status){
   return em.createQuery(FIND_TRANSACTION_BY_STATUS, Transaction.class).setParameter("status", status)
          .getResultList();

  }
  
}