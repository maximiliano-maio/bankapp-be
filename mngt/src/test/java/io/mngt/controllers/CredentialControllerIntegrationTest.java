package io.mngt.controllers;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import io.mngt.entity.Client;
import io.mngt.entity.ContactInfo;
import io.mngt.entity.Credential;
import io.mngt.services.ClientService;
import io.mngt.services.CredentialService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc()
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class CredentialControllerIntegrationTest {

  @Autowired
  private CredentialService credentialService;
  @Autowired
  private ClientService clientService;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void givenExistentCredential_whenValidateUser_thenCredentialReturned() throws Exception {
    Credential credential = new Credential("maxi", "maio", "maxi_maio@hotmail.com", "admin", 90);
    byte[] content = objectMapper.writeValueAsBytes(credential);
    this.mockMvc.perform(post("/login/user").contentType(MediaType.APPLICATION_JSON).content(content))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.role", is("admin")));
  }

  @Test
  public void givenNonExistentCredential_whenValidateUser_thenNullReturned() throws Exception {
    Credential credential = new Credential("maxi", "maxi", "maxi_maio@hotmail.com", "admin", 90);
    byte[] content = objectMapper.writeValueAsBytes(credential);
    this.mockMvc.perform(post("/login/user").contentType(MediaType.APPLICATION_JSON).content(content))
        .andExpect(status().isOk()).andExpect(jsonPath("$").doesNotExist());
  }
  
  // Testing SQL Injection?
  @Test
  public void givenSQLInjection_whenValidateUser_thenNullReturned() throws Exception {
    Credential credential = new Credential("' or '1=1'", "' or '1=1'", "maxi_maio@hotmail.com", "admin", 90);
    byte[] content = objectMapper.writeValueAsBytes(credential);
    this.mockMvc.perform(post("/login/user").contentType(MediaType.APPLICATION_JSON).content(content))
        .andExpect(status().isOk()).andExpect(jsonPath("$").doesNotExist());
  }

  @Test
  public void givenExistingHashcode_whenIsAuthenticated_thenReturnStatus1() throws Exception {
    Credential maxiCredential = credentialService.login("maxi", "maio");
    String hashcode = Integer.toString(maxiCredential.getHashcode());
    this.mockMvc.perform(get("/auth/authState").param("code", hashcode))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", is(1)));
  }

  @Test
  public void givenNonExistingHashcode_whenIsAuthenticated_thenReturnStatus0() throws Exception {
    Credential maxiCredential = credentialService.login("maxi", "maio");
    String hashcode = Integer.toString(maxiCredential.getHashcode() + 1000);
    this.mockMvc.perform(get("/auth/authState").param("code", hashcode))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", is(0)));
  }

  @Test
  public void givenExistingHashcode_whenLogOut_thenReturnTrue() throws Exception {
    Credential maxiCredential = credentialService.login("maxi", "maio");
    String hashcode = Integer.toString(maxiCredential.getHashcode());
    this.mockMvc.perform(get("/logout").param("code", hashcode))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", is(true)));
  }

  @Test
  public void givenNonExistingHashcode_whenLogOut_thenReturnFalse() throws Exception {
    Credential maxiCredential = credentialService.login("maxi", "maio");
    String hashcode = Integer.toString(maxiCredential.getHashcode() + 1000);
    this.mockMvc.perform(get("/logout").param("code", hashcode)).andExpect(status().isOk())
        .andExpect(jsonPath("$", is(false)));
  }

  @Test
  public void givenExistingClient_whenSendValidationCode_thenValidationCodeReceivedBySMS() throws Exception {
    Credential maxiCredential = credentialService.login("maxi", "maio");
    maxiCredential = credentialService.findCredentialByHashcode(maxiCredential.getHashcode());
    byte[] content = objectMapper.writeValueAsBytes(maxiCredential.getClient());
    this.mockMvc.perform(post("/sendValidationCode").contentType(MediaType.APPLICATION_JSON).content(content))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.messages[0].status", is("OK")));
  }

  @Test
  public void givenNullAsClient_whenSendValidationCode_thenBadRequest() throws Exception {
    byte[] content = objectMapper.writeValueAsBytes(null);
    this.mockMvc.perform(post("/sendValidationCode").contentType(MediaType.APPLICATION_JSON).content(content))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void givenNewClient_whenSendValidationCode_thenValidationCodeReceivedBySMS() throws Exception {
    Client newClient = new Client("1234567", "test", "test", "0");
    ContactInfo clientContactInfo = new ContactInfo();
    clientContactInfo.setCellphone("0515819763");
    newClient.setContactInfo(clientContactInfo);
    Client registeredClient = clientService.setClient(newClient);

    byte[] content = objectMapper.writeValueAsBytes(registeredClient);
    this.mockMvc.perform(post("/sendValidationCode").contentType(MediaType.APPLICATION_JSON).content(content))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.messages[0].status", is("OK")));
  }

  @Test
  public void givenNewCredential_whenSetCredential_thenReturnTrue() throws Exception{
    Client newClient = new Client("1234567", "test", "test", "0");
    clientService.setClient(newClient);

    Credential newCredential = new Credential();
    newCredential.setUsername("newTestUser");
    newCredential.setPassword("newTestPass");
    newCredential.setMail("newTest@test.com");
    newCredential.setRole(newClient.getClientId());

    byte[] content = objectMapper.writeValueAsBytes(newCredential);
    this.mockMvc.perform(post("/setCredential").contentType(MediaType.APPLICATION_JSON).content(content))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", is(true)));
  }

  @Test
  public void givenExistentCredential_whenSetCredential_thenReturnFalse() throws Exception {
    Client newClient = new Client("1234567", "test", "test", "0");
    clientService.setClient(newClient);

    Credential newCredential = new Credential();
    newCredential.setUsername("maxi"); // Existent username which must be unique
    newCredential.setPassword("newTestPass");
    newCredential.setMail("newTest@test.com");
    newCredential.setRole(newClient.getClientId());

    byte[] content = objectMapper.writeValueAsBytes(newCredential);
    this.mockMvc.perform(post("/setCredential").contentType(MediaType.APPLICATION_JSON).content(content))
        .andExpect(status().isOk()).andExpect(jsonPath("$", is(false)));
  }
 
  @Test
  public void givenMatchingCode_whenValidateValidationCode_thenReturnTrue() throws Exception {
    Credential maxiCredential = credentialService.login("maxi", "maio");
    maxiCredential = credentialService.findCredentialByHashcode(maxiCredential.getHashcode());
    byte[] content = objectMapper.writeValueAsBytes(maxiCredential.getClient());
    this.mockMvc.perform(post("/sendValidationCode").contentType(MediaType.APPLICATION_JSON).content(content));

    int validationCodeSent = credentialService
      .findCredentialByHashcode(maxiCredential.getHashcode())
      .getClient()
      .getValidationCode();
    maxiCredential.getClient().setValidationCode(validationCodeSent);
    byte[] clientData = objectMapper.writeValueAsBytes(maxiCredential.getClient());
    this.mockMvc.perform(post("/validateCode").contentType(MediaType.APPLICATION_JSON).content(clientData))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", is(true)));
  }

  @Test
  public void givenNonMatchingCode_whenValidateValidationCode_thenReturnFalse() throws Exception {
    Credential maxiCredential = credentialService.login("maxi", "maio");
    maxiCredential = credentialService.findCredentialByHashcode(maxiCredential.getHashcode());
    byte[] content = objectMapper.writeValueAsBytes(maxiCredential.getClient());
    this.mockMvc.perform(post("/sendValidationCode").contentType(MediaType.APPLICATION_JSON).content(content));

    int validationCodeSent = credentialService.findCredentialByHashcode(maxiCredential.getHashcode()).getClient()
        .getValidationCode() + 10;
    maxiCredential.getClient().setValidationCode(validationCodeSent);
    byte[] clientData = objectMapper.writeValueAsBytes(maxiCredential.getClient());
    this.mockMvc.perform(post("/validateCode").contentType(MediaType.APPLICATION_JSON).content(clientData))
        .andExpect(status().isOk()).andExpect(jsonPath("$", is(false)));
  }


}