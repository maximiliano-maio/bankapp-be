package io.mngt.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.*;

import io.mngt.entity.Client;
import io.mngt.entity.Credential;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class CredentialServiceImplIntegrationTest {

  @TestConfiguration
  static class TestContextConfiguration {
    @Bean
    public CredentialService credentialService() {
      return new CredentialServiceImpl();
    }
  }
  
  @Autowired
  private CredentialService credentialService;
  @Autowired
  private ClientService clientService;

  @Test
  public void givenValidUsernameAndPassword_whenLogin_thenReturnCredential(){
    String username = "maxi";
    String password = "maio";
    Credential credential = credentialService.login(username, password);
    assertThat(credential).isNotNull();
  }
  
  @Test
  public void givenValidUsernameAndWrongPassword_whenLogin_thenReturnNull() {
    String username = "maxi";
    String password = "maxi";
    Credential credential = credentialService.login(username, password);
    assertThat(credential).isNull();
  }

  @Test
  public void givenWrongUsername_whenLogin_thenReturnNull() {
    String username = "maio";
    String password = "maio";
    Credential credential = credentialService.login(username, password);
    assertThat(credential).isNull();
  }

  @Test
  public void givenValidHashcode_whenIsAuthenticated_thenReturn1(){
    int hashcode = credentialService.login("maxi", "maio").getHashcode();
    int status = credentialService.isAuthenticated(hashcode);
    assertThat(status).isEqualTo(1);
  }

  @Test
  public void givenInvalidHashcode_whenIsAuthenticated_thenReturn0() {
    int hashcode = 100;
    int status = credentialService.isAuthenticated(hashcode);
    assertThat(status).isEqualTo(0);
  }

  @Test
  public void givenValidHashcode_whenFindCredentialByHashcode_thenReturnCredential(){
    int hashcode = credentialService.login("maxi", "maio").getHashcode();
    Credential credential = credentialService.findCredentialByHashcode(hashcode);
    assertThat(credential).isNotNull();
  }

  @Test
  public void givenInvalidHashcode_whenFindCredentialByHashcode_thenReturnNull() {
    int hashcode = 100;
    Credential credential = credentialService.findCredentialByHashcode(hashcode);
    assertThat(credential).isNull();
  }

  @Test
  public void givenValidHashcode_whenLogout_thenReturnTrue(){
    int hashcode = credentialService.login("maxi", "maio").getHashcode();
    boolean isLoggedOut = credentialService.logout(hashcode);
    assertThat(isLoggedOut).isTrue();
  }

  @Test
  public void givenInvalidHashcode_whenLogout_thenReturnFalse() {
    int hashcode = 100;
    boolean isLoggedOut = credentialService.logout(hashcode);
    assertThat(isLoggedOut).isFalse();
  }

  @Test
  public void givenValidHashcode_whenGetCredentialFromHashcode_thenReturnCredential(){
    int hashcode = credentialService.login("maxi", "maio").getHashcode();
    Credential credential = credentialService.findCredentialByHashcode(hashcode);
    assertThat(credential).isNotNull();
  }

  @Test
  public void givenInvalidHashcode_whenGetCredentialFromHashcode_thenReturnNull() {
    int hashcode = 100;
    Credential credential = credentialService.findCredentialByHashcode(hashcode);
    assertThat(credential).isNull();
  }

  @Test
  public void whenGetValidationCode_thenReturn4digitNumber(){
    int validationCode = credentialService.getValidationCode();
    assertThat(validationCode).isGreaterThan(0).isLessThanOrEqualTo(9999);
  }

  @Test
  public void givenMatchingValidationCode_whenIsValidationCodeCorrect_thenReturnTrue(){
    int validationCode = 0150;
    int hashcode = credentialService.login("maxi", "maio").getHashcode();
    Client client = credentialService.findCredentialByHashcode(hashcode).getClient();
    clientService.updateValidationCode(client, validationCode);
    boolean isValidationCodeCorrect = credentialService
      .isValidationCodeCorrect(validationCode, client.getClientId());
    assertThat(isValidationCodeCorrect).isTrue();
  }

  @Test
  public void givenUnmatchingValidationCode_whenIsValidationCodeCorrect_thenReturnFalse() {
    int validationCode = 0150;
    int hashcode = credentialService.login("maxi", "maio").getHashcode();
    Client client = credentialService.findCredentialByHashcode(hashcode).getClient();
    int validationCodeGenerated = credentialService.getValidationCode();
    clientService.updateValidationCode(client, validationCodeGenerated);
    boolean isValidationCodeCorrect = credentialService
      .isValidationCodeCorrect(validationCode, client.getClientId());
    assertThat(isValidationCodeCorrect).isFalse();
  }
}