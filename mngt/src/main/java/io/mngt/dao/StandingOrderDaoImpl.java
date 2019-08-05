package io.mngt.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.mngt.entity.StandingOrder;
import io.mngt.repositories.StandingOrderRepository;

@Component
public class StandingOrderDaoImpl implements StandingOrderDao {
  
  @Autowired
  private StandingOrderRepository standingOrderRepository;

  private static final String FIND_STANDING_ORDERS_BY_DATE = "SELECT so FROM StandingOrder so WHERE so.date = :date";

  @PersistenceContext
  private EntityManager em;

  @Override
  public void persist(StandingOrder standingOrder){
    em.persist(standingOrder);
  }
  

  @Override
  public List<StandingOrder> findAllStandingOrdersByDate(Date date){
    
    List<StandingOrder> standingOrderList = em.createQuery(FIND_STANDING_ORDERS_BY_DATE, StandingOrder.class)
      .setParameter("date", date)
      .getResultList();

    if (standingOrderList.size() > 0) return standingOrderList;

    return null;
  }

  @Override
  public StandingOrder save(StandingOrder standingOrder) {
    return standingOrderRepository.save(standingOrder);
  }

}