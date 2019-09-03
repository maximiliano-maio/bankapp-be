package io.mngt.dao;

import java.util.Date;
import java.util.List;

import io.mngt.entity.StandingOrder;

public interface StandingOrderDao {
  void persist(StandingOrder standingOrder);
  StandingOrder save(StandingOrder standingOrder);
  StandingOrder update(StandingOrder standingOrder);
  
  List<StandingOrder> findAllStandingOrdersByDate(Date date);
  List<StandingOrder> findAllStandingOrdersByDateAndStatus(Date date, int status);
}