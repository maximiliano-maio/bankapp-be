package io.mngt.dao;

import io.mngt.entity.Client;

public interface ClientDao {
  
  void persist(Client client);
  Client findByClientId(String clientId);
  Client save(Client client);
  Client findById(Long id);
  void delete(Client client);
  Iterable<Client> findAll();

  // Custom queries:
  Client findClientAndCredentialAssociatedByClientId(String clientId);
  void updateValidationCode(Client client, int validationCode);

}