package io.mngt.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import io.mngt.domain.Client;
import io.mngt.domain.ContactInfo;
import io.mngt.repositories.CheckBookOrderRepository;
import io.mngt.repositories.ClientRepository;
import io.mngt.repositories.ContactInfoRepository;

//Create  few clients for testing purposes
@Component
public class ClientBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired
  private ClientRepository clientRepository;
  @Autowired
  private ContactInfoRepository contactInfoRepository;
  @Autowired
  private CheckBookOrderRepository checkBookOrderRepository;

  private void initData() {

    Client client = new Client();
    client.setClientId(100);
    client.setFirstName("Maxi");
    client.setLastName("Maio");
    client.setMaritalStatus(1);

    ContactInfo clientContactInfo = new ContactInfo();
    clientContactInfo.setCellphone("0515819763");
    clientContactInfo.setEmail("maxi_maio@hotmail.com");
    clientContactInfo.setDistributionAgreement(2);

    client.setContactInfo(clientContactInfo);

    clientRepository.save(client);
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    initData();
  }

  
  
}