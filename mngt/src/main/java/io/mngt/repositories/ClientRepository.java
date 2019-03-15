package io.mngt.repositories;

import org.springframework.data.repository.CrudRepository;

import io.mngt.domain.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {
    
}