package io.mngt.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import io.mngt.entity.Environment;
import io.mngt.repositories.EnvironmentRepository;

@Component
@Profile("default")
public class EnvironmentBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired
  private EnvironmentRepository environmentRepository;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    initEnviromentProperties();
  }

  private void initEnviromentProperties() {
    environmentRepository.save(new Environment("INCOMING_BANK_ACCOUNT", "999990", "INCOMING BANK ACCOUNT"));
    environmentRepository.save(new Environment("OUTGOING_BANK_ACCOUNT", "999991", "OUTGOING BANK ACCOUNT"));
  }

  
}