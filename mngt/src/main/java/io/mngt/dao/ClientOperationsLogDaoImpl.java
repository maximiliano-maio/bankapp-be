package io.mngt.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import io.mngt.entity.ClientOperationsLog;

@Component
public class ClientOperationsLogDaoImpl implements ClientOperationsLogDao {

  @PersistenceContext
  private EntityManager em;

  @Override
  public void persistLog(ClientOperationsLog clientOperationsLog){
    em.persist(clientOperationsLog);
  }

  @Override
  public void updateLog(ClientOperationsLog clientOperationsLog) {

    // TODO: Update logic


  }


  
}