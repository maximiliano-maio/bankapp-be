package io.mngt.services;

import io.mngt.domain.Credential;

public interface CredentialService {
  Credential login(String username, String password);
  Boolean logout(int hashcode);
  Iterable<Credential> findAll();
  int isAuthenticated(int hashcode);
  Credential getCredential(int hashcode);
}