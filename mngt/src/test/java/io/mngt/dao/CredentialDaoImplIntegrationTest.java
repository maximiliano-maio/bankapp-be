package io.mngt.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import io.mngt.entity.Client;
import io.mngt.entity.Credential;
import io.mngt.services.CredentialService;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class CredentialDaoImplIntegrationTest {

  @Autowired
  private CredentialDao credentialDao;
  @Autowired
  private CredentialService credentialService;

  @Test
  public void givenExistentUsername_whenFindByUsername_thenReturnCredential(){
    String username = "maxi";
    Credential credential = credentialDao.findByUsername(username);
    assertThat(credential.getPassword()).isEqualTo("maio");
  }
  
  @Test
  public void givenNonExistentUsername_whenFindByUsername_thenReturnNull() {
    String username = "maio";
    Credential credential = credentialDao.findByUsername(username);
    assertThat(credential).isNull();
  }

  @Test
  public void givenExistentPassword_whenFindByPassword_thenReturnCredential() {
    String password = "maio";
    Credential credential = credentialDao.findByPassword(password);
    assertThat(credential.getUsername()).isEqualTo("maxi");
  }

  @Test
  public void givenNonExistentPassword_whenFindByPassword_thenReturnNull() {
    String password = "maxi";
    Credential credential = credentialDao.findByPassword(password);
    assertThat(credential).isNull();
  }

  @Test
  public void givenExistentHashcode_whenFindByHashcode_thenReturnCredential(){
    Credential credential = credentialService.login("maxi", "maio");
    Credential credentialReturned = credentialDao.findByHashcode(credential.getHashcode());
    assertThat(credentialReturned.getHashcode()).isEqualTo(credential.getHashcode());
  }
  
  @Test
  public void givenNonExistentHashcode_whenFindByHashcode_thenReturnCredential() {
    int hashcode = 100;
    Credential credentialReturned = credentialDao.findByHashcode(hashcode);
    assertThat(credentialReturned).isNull();
  }

  @Rollback(value = true)
  @Test
  public void givenNewCredentialToNewClient_whenSave_thenReturnCredential(){
    Credential newCredential = 
      new Credential(new Client("100", "test", "test", "1"), "username", "password", "test@test.com");
    Credential credentialStored = credentialDao.save(newCredential);
    assertThat(credentialStored).isNotNull();
  }

  @Transactional
  @Rollback(value = true)
  @Test
  public void givenNewCredentialToExistentClient_whenSave_thenReturnCredential() {
    Credential credential = credentialService.login("maxi", "maio");
    Client client = credentialDao.findByHashcode(credential.getHashcode()).getClient();
    Credential newCredential = new Credential(client, "username", "password", "test@test.com");
    Credential credentialStored = credentialDao.save(newCredential);
    assertThat(credentialStored).isNotNull();
  }


}