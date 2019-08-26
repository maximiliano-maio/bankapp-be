package io.mngt.services;

import io.mngt.entity.Client;
import io.mngt.entity.Credential;

public interface CredentialService {
  Credential login(String username, String password);
  Boolean logout(int hashcode);
  int isAuthenticated(int hashcode);
  Credential findCredentialByHashcode(int hashcode);
  int getValidationCode(String clientId);
  boolean isValidationCodeCorrect(int validationCode, String clientId);
  Credential setCredential(Client client, String username, String password, String mail);
}