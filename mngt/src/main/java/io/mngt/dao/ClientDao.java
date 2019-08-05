package io.mngt.dao;

import java.util.Optional;

import io.mngt.entity.Client;

public interface ClientDao {
  
  void persist(Client client);
  Client findByClientId(String clientId);
  Client save(Client client);
  Optional<Client> findById(Long id);
  void delete(Client client);

  Iterable<Client> findAll();

}