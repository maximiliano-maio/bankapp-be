package io.mngt.services;

import io.mngt.entity.Credential;

public interface CredentialService {
  Credential login(String username, String password);
  Boolean logout(int hashcode);
  int isAuthenticated(int hashcode);
  Credential findCredentialByHashcode(int hashcode);
  int getValidationCode(String clientId);
}