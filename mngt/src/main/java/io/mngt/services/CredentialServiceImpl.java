package io.mngt.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.mngt.domain.Credential;
import io.mngt.repositories.CredentialRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CredentialServiceImpl implements CredentialService {

  private Credential credentialFromDB = new Credential();
  SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
  Date date;

  @Autowired
  private CredentialRepository credentialRepository;

  @Override
  public Credential login(String username, String password) {
    
    credentialFromDB = credentialRepository.findByUsername(username);
    if (credentialFromDB == null) return null;

    // If Credential exists, so..
    if (password.equals(credentialFromDB.getPassword())) {
      
      log.info("Credentials are correct");
      
      // Generate HashCode from username, password and date:
      this.date = new Date(System.currentTimeMillis());
      String hashcode =  new String();
      hashcode = credentialFromDB.getUsername() + credentialFromDB.getPassword() + this.formatter.format(date);
      credentialFromDB.setHashcode( hashcode.hashCode());
      
      // Declare user has logged in
      credentialFromDB.setStatus(1);
      
      credentialRepository.save(credentialFromDB);
      
      // Sending back only relevant fields:
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
  public Iterable<Credential> findAll() {
    return credentialRepository.findAll();
  }

  @Override
  public int isAuthenticated(int hashcode) {
    
    Credential c = credentialRepository.findByHashcode(hashcode);
    if (c == null) return 0;

    return c.getStatus();
    

  }

  
}