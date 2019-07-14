package io.mngt.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import io.mngt.domain.BalanceILS;
import io.mngt.domain.Client;

@Component
public class BalanceDaoImpl {

  private static final String LAST_X_BALANCES_OF_CLIENT = "SELECT b FROM BalanceILS b WHERE b.client = :client ORDER BY b.id DESC";
  
  @PersistenceContext
  private EntityManager em;

  public void persist(BalanceILS balance) {
    em.persist(balance);
  }


  public List<BalanceILS> findLastBalancesByClient(Client c, int x) {
    return em.createQuery(LAST_X_BALANCES_OF_CLIENT, BalanceILS.class)
      .setParameter("client", c)
      .setMaxResults(x)
      .getResultList();
  }
  
}