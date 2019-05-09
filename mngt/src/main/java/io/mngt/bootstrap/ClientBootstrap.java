package io.mngt.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import io.mngt.domain.Client;
import io.mngt.domain.ContactInfo;
import io.mngt.repositories.ClientRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile("default")
public class ClientBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired
  private ClientRepository clientRepository;
  
  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    

  }

  private Client setInitClientData() {

    Client client = new Client("100", "Maxi", "Maio", "1");

    ContactInfo clientContactInfo = new ContactInfo();
    clientContactInfo.setCellphone("0515819763");
    clientContactInfo.setEmail("maxi_maio@hotmail.com");
    clientContactInfo.setDistributionAgreement(2);

    client.setContactInfo(clientContactInfo);
    Client returnedClient = clientRepository.save(client);
    log.info(returnedClient.getFirstName());
    return returnedClient;
  }


}