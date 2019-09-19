package io.mngt.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.mngt.entity.Transaction;
import io.mngt.repositories.TransactionRepository;

@Repository
public class TransactionDaoImpl implements TransactionDao {

  private static final String FIND_TRANSACTION_BY_STATUS = "SELECT t FROM Transaction t WHERE t.status = :status";
  private static final String FIND_TRANSACTION_BY_EXTERNAL_ACCOUNT = "SELECT t FROM Transaction t WHERE t.isAccountExternal = :isAccountExternal";

  @PersistenceContext
  private EntityManager em;

  @Autowired
  private ClientOperationsLogDao clientOperationsLogDao;

  @Autowired
  private TransactionRepository transactionRepository;

  @Override
  public void persist(TransactionDao transactionDao){
    em.persist(transactionDao);
  }

  @Override
  public List<Transaction> findTransactionByStatus(int status){
   return em.createQuery(FIND_TRANSACTION_BY_STATUS, Transaction.class).setParameter("status", status)
          .getResultList();

  }

  @Override
  public List<Transaction> findTransactionByExternalAccount(boolean isAccountExternal){
    return em.createQuery(FIND_TRANSACTION_BY_EXTERNAL_ACCOUNT, Transaction.class)
      .setParameter("isAccountExternal", isAccountExternal)
      .getResultList();
  }

  @Override
  public Transaction save(Transaction transaction) {
    return transactionRepository.save(transaction);
  }
  
}