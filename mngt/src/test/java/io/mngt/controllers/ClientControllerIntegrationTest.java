package io.mngt.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import io.mngt.entity.Client;
import io.mngt.services.ClientService;
import io.mngt.services.CredentialService;

@RunWith(SpringRunner.class)
@WebMvcTest(ClientController.class)
public class ClientControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ClientService clientService;

  @MockBean
  private CredentialService credentialService;

  public static byte[] convertObjectToJsonBytes(Client object) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsBytes(object);
  }

  @Test
  public void givenClient_whenCreate_thenReturnClient() throws Exception {

    Client maxi = new Client("338016777", "Maximiliano", "Maio", "1");
    byte[] content = convertObjectToJsonBytes(maxi);

    Mockito.when(clientService.setClient(maxi)).thenReturn(maxi);

    this.mockMvc.perform(post("/registerclient")
                          .contentType(MediaType.APPLICATION_JSON)
                          .content(content))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.firstName", is(maxi.getFirstName())));
      
    
      
    
  }
  
}