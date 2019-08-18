package io.mngt.repositories;

import org.springframework.data.repository.CrudRepository;

import io.mngt.entity.ClientOperationsLog;

public interface ClientOperationsLogRepository extends CrudRepository<ClientOperationsLog, Long> {

  
}