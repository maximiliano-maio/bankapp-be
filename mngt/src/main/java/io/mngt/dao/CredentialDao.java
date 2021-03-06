package io.mngt.dao;

import io.mngt.entity.Client;
import io.mngt.entity.Credential;

public interface CredentialDao {
  Credential findByUsername(String username);
  Credential findByPassword(String password);
  Credential findByHashcode(int hashcode);
  Credential save(Credential credential);
  Credential setCredential(Client client, String username, String password, String mail);
  
}