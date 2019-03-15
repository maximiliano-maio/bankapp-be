package io.mngt.services;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import io.mngt.commands.ClientCommand;
import io.mngt.converters.ClientCommandToClient;
import io.mngt.converters.ClientToClientCommand;
import io.mngt.domain.Client;
import io.mngt.repositories.ClientRepository;
import io.mngt.repositories.ContactInfoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientServiceIT {

  private static final String FIRST_NAME = "John Doe"; 

  @Autowired
  ClientService clientService;

  @Autowired
  ClientRepository clientRepository;

  @Autowired
  ContactInfoRepository contactInfoRepository;

  @Autowired
  ClientToClientCommand clientToClientCommand;

  @Autowired
  ClientCommandToClient clientCommandToClient;

  @Transactional
  @Test
  public void TestSetClient() throws Exception {
    //given
    Client client = clientRepository.findAll().iterator().next();
    ClientCommand clientCommand = clientToClientCommand.convert(client);

    //when
    clientCommand.setFirstName(FIRST_NAME);
    ClientCommand savedClientCommand = clientService.setClient(clientCommand);

    //then
    assertEquals(FIRST_NAME, savedClientCommand.getFirstName());
    assertEquals(client.getId(), savedClientCommand.getId());

  }
  
}