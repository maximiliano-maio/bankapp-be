package io.mngt.repositories;

import org.springframework.data.repository.CrudRepository;

import io.mngt.entity.Environment;

public interface EnvironmentRepository extends CrudRepository<Environment, Long> {

  Environment findByKey(String key);
  
}