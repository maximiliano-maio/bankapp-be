package io.mngt.controllers;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import io.mngt.entity.Client;
import io.mngt.entity.Credential;
import io.mngt.services.ClientService;
import io.mngt.services.CredentialService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class ClientControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ClientService clientService;

  @Autowired 
  private CredentialService credentialService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void givenClient_whenCreate_thenClientIsReturned() throws Exception {
    Client maxi = new Client("338000777", "Maximiliano", "Maio", "1");
    maxi.setId(1L);
    byte[] content = objectMapper.writeValueAsBytes(maxi);
    clientService.setClient(maxi);
    this.mockMvc.perform(
      post("/registerclient").contentType(MediaType.APPLICATION_JSON).content(content))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName", is(maxi.getFirstName())));
  }

  @Test
  public void givenHashcode_whenGetClient_thenClientIsReturned() throws Exception {
    Credential maxiCredential = credentialService.login("maxi", "maio");
    String hashcode = Integer.toString(maxiCredential.getHashcode());
    MvcResult mvcResult = this.mockMvc.perform(get("/user").param("code", hashcode))
                            .andExpect(status().isOk())
                            .andReturn();
    String clientFirstName = objectMapper.readValue(
      mvcResult.getResponse().getContentAsString(), 
      Client.class).getFirstName();
    assertEquals(clientFirstName, "Maxi");
  }

  
}