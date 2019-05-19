package io.mngt.services;

import io.mngt.domain.Credential;

public interface CredentialService {
  Credential login(String username, String password);
  Iterable<Credential> findAll();

  
}