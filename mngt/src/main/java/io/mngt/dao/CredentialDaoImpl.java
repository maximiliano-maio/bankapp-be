package io.mngt.dao;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.mngt.entity.Client;
import io.mngt.entity.Credential;
import io.mngt.repositories.CredentialRepository;

@Repository
public class CredentialDaoImpl implements CredentialDao {

  @PersistenceContext
  private EntityManager em;

  @Autowired
  private ClientOperationsLogDao clientOperationsLogDao;

  @Autowired
  private CredentialRepository credentialRepository;
  
  public void persist(Credential credential) {
    em.persist(credential);
  }

  @Override
  public Credential findByUsername(String username) {
    Optional<Credential> credential = credentialRepository.findByUsername(username);
    if (!credential.isPresent()) return null;

    return credential.get();

  }

  @Override
  public Credential findByPassword(String password) {
    Optional<Credential> credential = credentialRepository.findByPassword(password);
    if (!credential.isPresent())
      return null;

    return credential.get();
  }

  @Override
  public Credential findByHashcode(int hashcode) {
    Optional<Credential> credential = credentialRepository.findByHashcode(hashcode);
    if (!credential.isPresent())
      return null;

    return credential.get();
  }

  @Override
  public Credential save(Credential credential) {
    return credentialRepository.save(credential);
  }

  @Override
  public Credential setCredential(Client client, String username, String password, String mail) {
    Credential credential = new Credential(client, username, password, mail);
    return save(credential);
  }


  
}