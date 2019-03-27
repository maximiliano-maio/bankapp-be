package io.mngt.services;

import static org.junit.Assert.assertEquals;

import java.io.Console;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import io.mngt.commands.ClientCommand;
import io.mngt.converters.ClientCommandToClient;
import io.mngt.converters.ClientToClientCommand;
import io.mngt.converters.ContactInfoCommandToContactInfo;
import io.mngt.converters.ContactInfoToContactInfoCommand;
import io.mngt.domain.Client;
import io.mngt.repositories.ClientRepository;
import io.mngt.repositories.ContactInfoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ClientServiceIT {

  private static final String FIRST_NAME = "Maxi";
  private Logger logger = LoggerFactory.getLogger(ClientServiceIT.class);
  ClientService clientService;
  @Mock
  ClientRepository clientRepository;
  @Mock
  ContactInfoRepository contactInfoRepository;
  @Mock
  ClientToClientCommand clientToClientCommand;
  @Mock
  ClientCommandToClient clientCommandToClient;
  @Mock
  ContactInfoCommandToContactInfo contactInfoCommandToContactInfo;
  @Mock
  ContactInfoToContactInfoCommand contactInfoToContactInfoCommand;
  @Mock
  ClientCommand clientCommand;

  @Before
  public void setupMock() {
    MockitoAnnotations.initMocks(this.getClass());
    clientService = new ClientServiceImpl(clientRepository, contactInfoRepository, clientToClientCommand,
        clientCommandToClient, contactInfoCommandToContactInfo, contactInfoToContactInfoCommand);
  }

  @Transactional
  @Test
  public void TestSetClient() throws Exception {
    // given
    Client client = clientRepository.findAll().iterator().next();
    this.logger.debug("client from repo: ", client);
    ClientCommand clientCommand = clientToClientCommand.convert(client);

    //when
    clientCommand.setFirstName(FIRST_NAME);
    ClientCommand savedClientCommand = clientService.setClient(clientCommand);

    //then
    assertEquals(FIRST_NAME, savedClientCommand.getFirstName());
    //assertEquals(client.getId(), savedClientCommand.getId());

  }
  
}