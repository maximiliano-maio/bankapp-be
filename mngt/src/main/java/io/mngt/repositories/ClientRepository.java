package io.mngt.repositories;

import org.springframework.data.repository.CrudRepository;
import java.util.Optional;
import io.mngt.entity.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {
  Optional<Client> findByClientId (String clientId);
  
  Optional<Client> findClientById(Long id);

    
}