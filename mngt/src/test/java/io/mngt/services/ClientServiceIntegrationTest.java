package io.mngt.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import io.mngt.dao.ClientDao;
import io.mngt.dao.ContactInfoDao;
import io.mngt.entity.Client;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
public class ClientServiceIntegrationTest {

  @TestConfiguration
  static class ClientServiceImplTestContextConfiguration{
    
    @Bean
    public ClientService clientService(){
      return new ClientServiceImpl();
    }
    
  }

  @Autowired
  private ClientService clientService;

  @MockBean
  private ClientDao clientDao;
  @MockBean
  private ContactInfoDao ContactInfoDao;

  @Before
  public void setUp() {
    Client maxi = new Client("338016777", "Maximiliano", "Maio", "1");
    Mockito.when(clientDao.findByClientId(maxi.getClientId())).thenReturn(maxi);
  }

  @Test
  public void whenValidClientId_thenClientShouldBeFound() {
    String clientId = "338016777";
    Client found = clientService.findByClientId(clientId);

    assertThat(found.getClientId()).isEqualTo(clientId);
  }
  
}