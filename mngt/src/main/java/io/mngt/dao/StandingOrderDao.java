package io.mngt.dao;

import java.util.Date;
import java.util.List;

import io.mngt.entity.StandingOrder;

public interface StandingOrderDao {

  void persist(StandingOrder standingOrder);
  List<StandingOrder> findAllStandingOrdersByDate(Date date);
  StandingOrder save(StandingOrder standingOrder);
  
  StandingOrder update(StandingOrder standingOrder);
  
}