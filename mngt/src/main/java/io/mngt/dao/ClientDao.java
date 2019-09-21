package io.mngt.dao;

import io.mngt.entity.Client;

public interface ClientDao {
  
  Client findByClientId(String clientId);
  Client save(Client client);
  Client findById(Long id);
  // Custom queries:
  Client findClientAndCredentialAssociatedByClientId(String clientId);
  void updateValidationCode(Client client, int validationCode);

}