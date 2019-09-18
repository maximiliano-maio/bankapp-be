package io.mngt.controllers;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import io.mngt.entity.Client;
import io.mngt.entity.Credential;
import io.mngt.services.ClientService;
import io.mngt.services.CredentialService;
import io.mngt.services.SmsService;

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
  private SmsService smsService;
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
  public void givenExistingClient_whenGetValidationCode_thenValidationCodeReceivedBySMS() throws Exception {
    Credential maxiCredential = credentialService.login("maxi", "maio");
    maxiCredential = credentialService.findCredentialByHashcode(maxiCredential.getHashcode());
    byte[] content = objectMapper.writeValueAsBytes(maxiCredential.getClient());
    this.mockMvc.perform(post("/sendValidationCode").contentType(MediaType.APPLICATION_JSON).content(content))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.messages[0].status", is("OK")));

  }



}