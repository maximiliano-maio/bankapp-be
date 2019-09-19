package io.mngt.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.*;
import io.mngt.entity.Client;
import io.mngt.entity.Credential;
import io.mngt.services.CredentialService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class ClientDaoImplIntegrationTest {

  @Autowired
  private ClientDao clientDao;
  @Autowired
  private CredentialService credentialService;

  @Test
  public void givenClientId_whenFindByClientId_thenReturnClient(){
    String clientId = "338016777";
    Client client = clientDao.findByClientId(clientId);

    Credential credential = credentialService.login("maxi", "maio");
    Client clientMatcher = credentialService.findCredentialByHashcode(credential.getHashcode()).getClient();

    assertThat(client.getFirstName()).isEqualTo(clientMatcher.getFirstName());
  }

  @Test
  public void givenNonExistentClientId_whenFindByClientId_thenReturnNull() {
    String clientId = "777";
    Client client = clientDao.findByClientId(clientId);
    assertThat(client).isNull();
  }

  @Test
  public void givenNewClient_whenSave_thenReturnClient() {
    Client client = new Client("100", "test", "test", "1");
    Client clientStored = clientDao.save(client);
    assertThat(clientStored.getLastName()).isEqualTo(client.getLastName());
  }

  @Test
  public void givenExistingClient_whenSave_thenReturnNull() {
    Client client = new Client("338016777", "test", "test", "1");
    Client clientStored = clientDao.save(client);
    assertThat(clientStored).isNull();
  }

  @Test
  public void givenExistentClientId_whenFindClientAndCredentialAssociatedByClientId_thenReturnClient(){
    String clientId = "338016777";
    Client client = clientDao.findClientAndCredentialAssociatedByClientId(clientId);
    assertThat(client.getFirstName()).isEqualTo("Maxi");
  }
  
  @Test
  public void givenExistentClientId_whenFindClientAndCredentialAssociatedByClientId_thenReturnCredential() {
    String clientId = "338016777";
    Client client = clientDao.findClientAndCredentialAssociatedByClientId(clientId);
    assertThat(client.getCredential().getMail()).isEqualTo("maxi_maio@hotmail.com");
  }

  @Test
  public void givenNonExistentClientId_whenFindClientAndCredentialAssociatedByClientId_thenReturnNull() {
    String clientId = "777";
    Client client = clientDao.findClientAndCredentialAssociatedByClientId(clientId);
    assertThat(client).isNull();
  }

}