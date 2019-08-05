package io.mngt.repositories;

import org.springframework.data.repository.CrudRepository;

import io.mngt.entity.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {
  Client findByClientId (String clientId);

    
}