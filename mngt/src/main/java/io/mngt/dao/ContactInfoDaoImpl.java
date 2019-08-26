package io.mngt.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.mngt.entity.ContactInfo;
import io.mngt.repositories.ContactInfoRepository;

@Component
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