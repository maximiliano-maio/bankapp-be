package io.mngt.services;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    log.info("username: " + username);
    credentialFromDB = credentialRepository.findByUsername(username);
    if (credentialFromDB == null) return null;

    if (username.equals(credentialFromDB.getUsername()) && password.equals(credentialFromDB.getPassword())) {
      log.info("Credentials are correct");
      // Generate HashCode from username, password and date:
      this.date = new Date(System.currentTimeMillis());
      String hashcode =  new String();
      hashcode = credentialFromDB.getUsername() + credentialFromDB.getPassword() + this.formatter.format(date);
      credentialFromDB.setHashcode(hashcode.hashCode());
      credentialRepository.save(credentialFromDB);
      // Do not send back user's password:
      credentialFromDB.setPassword(null);

      return credentialFromDB;
      
    } else {
      log.info("Username and Password are not correct");
      return null;
    }
    
  }

  @Override
  public Iterable<Credential> findAll() {
    return credentialRepository.findAll();
  }

  
}