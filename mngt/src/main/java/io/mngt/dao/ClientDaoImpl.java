package io.mngt.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.mngt.entity.Client;
import io.mngt.repositories.ClientRepository;

@Repository
public class ClientDaoImpl implements ClientDao {

  private static final String FIND_CLIENT_BY_CLIENT_ID = "SELECT c FROM Client c WHERE c.clientId = :clientId";
  private static final String UPDATE_VALIDATION_CODE = "UPDATE Client SET validationCode = :validationCode WHERE clientId = :clientId";

  @PersistenceContext
  private EntityManager em;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private ClientOperationsLogDao clientOperationsLogDao;

  private final static Logger logger = LoggerFactory.getLogger("ClientDaoImpl.class");
  private final static String url = "jdbc:h2:mem:test";
  private final static String username = "sa";
  private final static String password = "";


  public Object findMetadata(){
    try {
      Connection connection = DriverManager.getConnection(url, username, password);
      DatabaseMetaData metadata = connection.getMetaData();
      ResultSet resultSet = metadata.getColumns(null, null, "CLIENT", null);
    } catch (Exception e) {
      //TODO: handle exception
    }  
    

    return null;
    
  }

  @Override
  public Client findByClientId(String clientId) {
    Optional<Client> c = clientRepository.findByClientId(clientId);
    if (c.isPresent())
      return c.get();

    return null;
  }

  @Override
  public Client save(Client client) {
    if (findByClientId(client.getClientId()) != null)
      return null;
    return clientRepository.save(client);
  }

  @Override
  public Client findById(Long id) {
    Optional<Client> c = clientRepository.findById(id);
    if (c.isPresent())
      return c.get();

    return null;
  }

  @Override
  public Client findClientAndCredentialAssociatedByClientId(String clientId) {
    List<Client> clientList = em.createQuery(FIND_CLIENT_BY_CLIENT_ID, Client.class).setParameter("clientId", clientId)
        .getResultList();

    if (clientList.size() > 0)
      return clientList.get(0);

    return null;
  }

  @Override
  @Transactional
  public void updateValidationCode(Client client, int validationCode) {
    int updatedRows = em.createQuery(UPDATE_VALIDATION_CODE).setParameter("validationCode", validationCode)
        .setParameter("clientId", client.getClientId()).executeUpdate();
    logger.info("Updated rows on Update Validation Code: " + updatedRows);

  }


}
