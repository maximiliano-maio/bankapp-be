package io.mngt.dao;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.mngt.entity.Client;
import io.mngt.repositories.ClientRepository;

@Component
public class ClientDaoImpl implements ClientDao {

  @Autowired
  private ClientRepository clientRepository;

  @PersistenceContext
  private EntityManager em;

  @Override
  public void persist(Client client) {
    em.persist(client);
  }

  @Override
  public Client findByClientId(String clientId) {
    return null;
  }

  @Override
  public Client save(Client client) {
    return clientRepository.save(client);
  }

  @Override
  public Optional<Client> findById(Long id) {
    return clientRepository.findById(id);
  }

  @Override
  public void delete(Client client) {
    clientRepository.delete(client);
  }

  @Override
  public Iterable<Client> findAll() {
	  return clientRepository.findAll();
  }

  
  
}