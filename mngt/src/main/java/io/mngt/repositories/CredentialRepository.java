package io.mngt.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.mngt.domain.Credential;

public interface CredentialRepository extends MongoRepository<Credential, Integer> {
  Credential findByUsername(String username);
  Credential findByPassword(String password);
  
}