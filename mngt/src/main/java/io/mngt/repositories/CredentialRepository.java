package io.mngt.repositories;

import org.springframework.data.repository.CrudRepository;

import io.mngt.domain.Credential;

public interface CredentialRepository extends CrudRepository<Credential, Long> {
  // it works
  Credential findByUsername(String username);
  
  Credential findByPassword(String password);
  Credential findByHashcode(int hashcode);
  
}