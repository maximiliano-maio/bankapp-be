package io.mngt.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import io.mngt.repositories.ClientRepository;
import io.mngt.repositories.ContactInfoRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ClientServiceIT {

  // private static final String FIRST_NAME = "Maxi";
  ClientService clientService;
  @Mock
  ClientRepository clientRepository;
  @Mock
  ContactInfoRepository contactInfoRepository;

  @Before
  public void setupMock() {
    MockitoAnnotations.initMocks(this.getClass());
    clientService = new ClientServiceImpl();
  }

  @Transactional
  @Test
  public void TestSetClient() throws Exception {
    // given

    //when

    //then
    
    //assertEquals(FIRST_NAME, savedClientCommand.getFirstName());
    //assertEquals(client.getId(), savedClientCommand.getId());

  }
  
}