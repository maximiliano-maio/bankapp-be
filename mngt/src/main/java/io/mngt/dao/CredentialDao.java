package io.mngt.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import io.mngt.entity.Credential;

public interface CredentialDao extends JpaRepository<Credential, Long> {
  Credential findByUsername(String username);

  Credential findByPassword(String password);

  Credential findByHashcode(int hashcode);
  
}