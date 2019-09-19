package io.mngt.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.mngt.entity.ContactInfo;
import io.mngt.repositories.ContactInfoRepository;

@Repository
public class ContactInfoDaoImpl implements ContactInfoDao {

  @PersistenceContext
  private EntityManager em;
  
  @Autowired
  private ContactInfoRepository contactInfoRepository;

  @Autowired
  private ClientOperationsLogDao clientOperationsLogDao;

  @Override
  public ContactInfo save(ContactInfo contactInfo) {
    return contactInfoRepository.save(contactInfo);
  }

  @Override
  public ContactInfo update(ContactInfo contactInfo) {

    // TODO: Update Contac Info

    return null;
  }

  
  
}