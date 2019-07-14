package io.mngt.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import io.mngt.domain.BankAccount;
import io.mngt.domain.Client;

public class ClientDaoImpl {

  private static final String CLIENT_BY_BANK_ACCOUNT = "SELECT c FROM Client c WHERE c.bankAccount = :bankAccount";

  @PersistenceContext
  private EntityManager em;

  public void persist(Client client) {
    em.persist(client);
  }

  public Client findClientByBankAccount(BankAccount bankAccount) {
    return em.createQuery(CLIENT_BY_BANK_ACCOUNT, Client.class)
      .setParameter("bankAccount", bankAccount)
      .getResultList().get(0);
  }
  
}