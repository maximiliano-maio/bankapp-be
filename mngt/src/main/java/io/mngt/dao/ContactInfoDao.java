package io.mngt.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import io.mngt.domain.ContactInfo;

public interface ContactInfoDao extends JpaRepository<ContactInfo, Long> {

  
}