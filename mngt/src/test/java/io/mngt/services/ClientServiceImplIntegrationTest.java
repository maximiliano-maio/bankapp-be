package io.mngt.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import io.mngt.entity.Client;
import io.mngt.entity.ContactInfo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class ClientServiceImplIntegrationTest {

  @TestConfiguration
  static class TestContextConfiguration {
    @Bean
    public ClientService clientService(){
      return new ClientServiceImpl();
    }
    @Bean
    public AccountingService accountingService(){
      return new AccountingServiceImpl();
    }
  }
  
  @Autowired
  private ClientService clientService;
  @Autowired
  private CredentialService credentialService;
  @Autowired
  private AccountingService accountingService;
  
  @Rollback
  @Test
  public void givenNewClient_whenSetClient_thenReturnClient(){
    Client newClient = new Client("100", "test", "test", "1");
    Client clientStored = clientService.setClient(newClient);
    assertThat(clientStored.getFirstName()).isEqualTo(newClient.getFirstName());
  }
  
  @Rollback
  @Test
  public void givenExistentClient_whenSetClient_thenReturnNull() {
    int hashcode = credentialService.login("maxi", "maio").getHashcode();
    Client client = accountingService.findClientByHashcode(hashcode);

    Client clientStored = clientService.setClient(client);
    assertThat(clientStored).isNull();
  }

  @Test
  public void givenValidId_whenFindClientById_thenReturnClient(){
    Long id = 1L;
    Client client = clientService.findClientById(id);
    assertThat(client).isNotNull();
  }

  @Test
  public void givenInvalidId_whenFindClientById_thenReturnNull() {
    Long id = 20L;
    Client client = clientService.findClientById(id);
    assertThat(client).isNull();
  }

  @Rollback
  @Transactional
  @Test
  public void givenContactInfoToNewClient_whenSetContactInformation_thenReturnContactInfo(){
    Client newClient = new Client("100", "test", "test", "1");
    ContactInfo contactInfo = new ContactInfo();
    contactInfo.setClientId(newClient);
    contactInfo.setEmail("mail@test.com");
    ContactInfo contactInfoStored = clientService.setContactInformation(contactInfo);
    assertThat(contactInfoStored.getEmail()).isEqualTo(contactInfo.getEmail());
  }
  
  @Test
  public void givenContactInfoToExistentClient_whenSetContactInformation_thenReturnContactInfo() {
    int hashcode = credentialService.login("maxi", "maio").getHashcode();
    Client client = accountingService.findClientByHashcode(hashcode);
    ContactInfo contactInfo = new ContactInfo();
    contactInfo.setClientId(client);
    contactInfo.setEmail("mail@test.com");
    ContactInfo contactInfoStored = clientService.setContactInformation(contactInfo);
    assertThat(contactInfoStored.getEmail()).isEqualTo(contactInfo.getEmail());
  }

  @Test
  public void givenValidClientId_whenFindByClientId_thenReturnClient(){
    String id = "338016777";
    Client client = clientService.findByClientId(id);
    assertThat(client).isNotNull();
  }

  @Transactional
  @Test
  public void givenInvalidClientId_whenFindByClientId_thenReturnNull() {
    String id = "102";
    Client client = clientService.findByClientId(id);
    assertThat(client).isNull();
  }

  @Test
  public void givenValidClientId_whenfindClientAndCredentialAssociatedByClientId_thenReturnCredential(){
    String id = "338016777";
    Client client = clientService.findClientAndCredentialAssociatedByClientId(id);
    assertThat(client.getCredential()).isNotNull();
  }

  @Transactional
  @Test
  public void givenInvalidClientId_whenfindClientAndCredentialAssociatedByClientId_thenReturnNull() {
    String id = "103";
    Client client = clientService.findClientAndCredentialAssociatedByClientId(id);
    assertThat(client).isNull();
  }

  @Test
  public void whenUpdateValidationCode_thenValidationCodeUpdated(){
    int hashcode = credentialService.login("maxi", "maio").getHashcode();
    Client client = accountingService.findClientByHashcode(hashcode);
    int validationCodeBefore = client.getValidationCode();

    clientService.updateValidationCode(client, 100);
    client = accountingService.findClientByHashcode(hashcode);
    int validationCodeAfter = client.getValidationCode();
    assertThat(validationCodeAfter).isNotEqualTo(validationCodeBefore);
  }


}