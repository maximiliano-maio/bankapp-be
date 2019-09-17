package io.mngt.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.mngt.entity.Client;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClientRepositoryTest {

  @Autowired
  ClientRepository clientRepository;

  @Test
  public void whenSaveClient_thenReturnClient() {
    // given
    Client maxi = new Client("338016777", "Maximiliano", "Maio", "1");
    
    // when
    Client returnClient = clientRepository.save(maxi);

    // then
    assertEquals("Client 'Maxi' is not returned after saving him.", maxi, returnClient);

  }

  @Test
  public void whenFindByFirstName_thenReturnClient() {
    // given
    Client maxi = new Client("338016777", "Maximiliano", "Maio", "1");
    clientRepository.save(maxi);

    // when
    Client found = clientRepository.findByFirstName(maxi.getFirstName());

    // then
    assertEquals("Client 'Maxi' wasn't found searching by firstName.", maxi, found);

  }
  
  

}