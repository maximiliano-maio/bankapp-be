package io.mngt.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.mngt.domain.Client;
import io.mngt.domain.BankAccount;

@Repository
public interface ClientDao extends JpaRepository<Client, Long> {
  Client findByClientId(String clientId);
  
}