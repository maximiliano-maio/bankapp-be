package io.mngt.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import io.mngt.entity.Credential;

public interface CredentialRepository extends CrudRepository<Credential, Long> {
  // it works
  Optional<Credential> findByUsername(String username);
  
  Optional<Credential> findByPassword(String password);
  
  Optional<Credential> findByHashcode(int hashcode);
  
}