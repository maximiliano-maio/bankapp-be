package io.mngt.unittest.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.mngt.entity.Client;
import io.mngt.repositories.ClientRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClientRepositoryTest {

  @Autowired
  ClientRepository clientRepository;

  @Test
  public void whenSaveClient_thenReturnClient() {
    Client maxi = new Client("338016777", "Maximiliano", "Maio", "1");
    Client returnClient = clientRepository.save(maxi);
    assertEquals("Client 'Maxi' is not returned after saving him.", maxi, returnClient);
  }

  @Test
  public void whenFindByFirstName_thenReturnClient() {
    Client maxi = new Client("338016777", "Maximiliano", "Maio", "1");
    clientRepository.save(maxi);
    Client found = clientRepository.findByFirstName(maxi.getFirstName());
    assertEquals("Client 'Maxi' wasn't found searching by firstName.", maxi, found);
  }
  
  

}