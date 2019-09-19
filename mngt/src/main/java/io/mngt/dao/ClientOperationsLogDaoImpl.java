package io.mngt.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import io.mngt.entity.ClientOperationsLog;

@Repository
public class ClientOperationsLogDaoImpl implements ClientOperationsLogDao {

  @PersistenceContext
  private EntityManager em;

  @Override
  public void updateLog(ClientOperationsLog clientOperationsLog) {

    // TODO: Update logic


  }


  
}