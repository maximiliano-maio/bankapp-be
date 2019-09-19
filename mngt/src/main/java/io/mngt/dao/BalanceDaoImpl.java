package io.mngt.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.mngt.entity.BalanceILS;
import io.mngt.entity.Client;
import io.mngt.repositories.BalanceILSRepository;

@Repository
public class BalanceDaoImpl implements BalanceDao {
  
  private static final String LAST_X_BALANCES_OF_CLIENT = "SELECT b FROM BalanceILS b WHERE b.client = :client ORDER BY b.id DESC";
  
  @PersistenceContext
  private EntityManager em;
  
  @Autowired
  private ClientOperationsLogDao clientOperationsLogDao;

  @Autowired
  private BalanceILSRepository balanceILSRepository;


  @Override
  public void persist(BalanceILS balance) {
    em.persist(balance);
  }

  @Override
  public List<BalanceILS> findLastBalancesByClient(Client c, int x) {
    return em.createQuery(LAST_X_BALANCES_OF_CLIENT, BalanceILS.class)
      .setParameter("client", c)
      .setMaxResults(x)
      .getResultList();
  }

  @Override
  public List<BalanceILS> findByClient(Client client) {
    return null;
  }

  @Override
  public BalanceILS save(BalanceILS balanceILS) {
    return balanceILSRepository.save(balanceILS);
  }

  @Override
  public BalanceILS findById(long id) {
    Optional<BalanceILS> balance = balanceILSRepository.findById(id);
    if (balance.isPresent()) return balance.get();

    return null;
  }
  
}