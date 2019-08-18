package io.mngt.dao;

import io.mngt.entity.ContactInfo;

public interface ContactInfoDao {

  ContactInfo save(ContactInfo contactInfo);
  ContactInfo update(ContactInfo contactInfo);
}