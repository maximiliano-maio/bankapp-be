package io.mngt.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import io.mngt.domain.Client;

public class ClientDaoImpl {


  @PersistenceContext
  private EntityManager em;

  public void persist(Client client) {
    em.persist(client);
  }

  
  
}