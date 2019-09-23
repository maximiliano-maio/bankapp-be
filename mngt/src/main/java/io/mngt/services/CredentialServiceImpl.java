package io.mngt.services;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.mngt.business.ValidationCodeGenerator;
import io.mngt.dao.ClientDao;
import io.mngt.dao.CredentialDao;
import io.mngt.entity.Client;
import io.mngt.entity.Credential;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CredentialServiceImpl implements CredentialService {

  private Credential credentialFromDB = new Credential();
  SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
  Date date;

  @Autowired
  private CredentialDao credentialDao;
  @Autowired
  private ClientDao clientDao;

  @Autowired
  private ValidationCodeGenerator validationCodeGenerator;

  @Override
  public Credential login(String username, String password) {
    
    credentialFromDB = credentialDao.findByUsername(username);
    if (credentialFromDB == null) return null;

    if (password.equals(credentialFromDB.getPassword())) {
      log.info("Credentials are correct");
      
      // Generate HashCode from username, password and date:
      this.date = new Date(System.currentTimeMillis());
      String hashcode =  new String();
      hashcode = credentialFromDB.getUsername() + credentialFromDB.getPassword() + this.formatter.format(date);
      credentialFromDB.setHashcode( hashcode.hashCode());
      
      // Declare user has logged in
      credentialFromDB.setStatus(1);
      credentialDao.save(credentialFromDB);
      
      Credential returnedCredential = new Credential();
      returnedCredential.setUsername(credentialFromDB.getUsername());
      returnedCredential.setRole(credentialFromDB.getRole());
      returnedCredential.setStatus(credentialFromDB.getStatus());
      returnedCredential.setHashcode(credentialFromDB.getHashcode());

      return returnedCredential;
    } else {
      log.info("Username and Password are not correct");
      return null;
    }
  }

  @Override
  public int isAuthenticated(int hashcode) {
    Credential c = getCredentialFromHashcode(hashcode);
    if (c == null) return 0;
    
    return c.getStatus();
  }

  @Override
  public Credential findCredentialByHashcode(int hashcode) {
    Credential c = getCredentialFromHashcode(hashcode);
    if (c == null) return null;

    return c; 
  }

  @Override
  public Boolean logout(int hashcode) {
    Credential c = getCredentialFromHashcode(hashcode);
    if (c == null) return false;
    
    c.setHashcode(0);
    return true;
    
  }

  public Credential getCredentialFromHashcode(int hashcode) throws NullPointerException {
    Credential c = credentialDao.findByHashcode(hashcode);
    if (c == null) return null;
    
    return c;
  }

  @Override
  public int getValidationCode() {
    return validationCodeGenerator.generateCode(4);
  }

  @Override
  public boolean isValidationCodeCorrect(int validationCode, String clientId) {
    Client client = clientDao.findByClientId(clientId);
    if (client == null) return false;
    if (client.getValidationCode() != validationCode) return false;
    
    return true;
  }

  @Override
  public Credential setCredential(Client client, String username, String password, String mail) {
    return credentialDao.setCredential(client, username, password, mail);
  }
  
}