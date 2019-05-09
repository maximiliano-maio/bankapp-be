package io.mngt.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import io.mngt.domain.Credential;
import io.mngt.repositories.CredentialRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * UserBootstrap: Load initial user for login test.
 */
@Slf4j
@Component
@Profile("default")
public class UserBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired
  private CredentialRepository credentialRepository;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    setLoginUser();

  }

  private void setLoginUser() {
    credentialRepository.deleteAll();
    Credential credential = new Credential("maxi", "maio", "test@test.com", "admin", 90);
    credentialRepository.save(credential);
  }

  
}