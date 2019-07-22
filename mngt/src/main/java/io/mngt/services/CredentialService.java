package io.mngt.services;

import io.mngt.domain.Credential;

public interface CredentialService {
  Credential login(String username, String password);
  Boolean logout(int hashcode);
  int isAuthenticated(int hashcode);
  Credential findCredentialByHashcode(int hashcode);
}