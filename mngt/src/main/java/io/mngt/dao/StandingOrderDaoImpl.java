package io.mngt.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.mngt.entity.StandingOrder;
import io.mngt.repositories.StandingOrderRepository;

@Repository
public class StandingOrderDaoImpl implements StandingOrderDao {

  private static final String FIND_STANDING_ORDERS_BY_DATE = "SELECT so FROM StandingOrder so WHERE so.date = :date";
  private static final String FIND_STANDING_ORDERS_BY_DATE_AND_STATUS = "SELECT so FROM StandingOrder so WHERE so.date = :date AND so.status = :status";

  @PersistenceContext
  private EntityManager em;

  @Autowired
  private StandingOrderRepository standingOrderRepository;

  @Autowired
  private ClientOperationsLogDao clientOperationsLogDao;

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

  @Override
  public StandingOrder update(StandingOrder standingOrder) {

    // TODO: Update Standing Order
    
    return null;
  }

  @Override
  public List<StandingOrder> findAllStandingOrdersByDateAndStatus(Date date, int status) {
    List<StandingOrder> standingOrderList = em.createQuery(FIND_STANDING_ORDERS_BY_DATE_AND_STATUS, StandingOrder.class)
        .setParameter("date", date)
        .setParameter("status", status)
        .getResultList();
    if (standingOrderList.size() > 0)
      return standingOrderList;

    return null;
  }

}