package io.mngt.repositories;

import org.springframework.data.repository.CrudRepository;

import io.mngt.entity.ContactInfo;


public interface ContactInfoRepository extends CrudRepository<ContactInfo, Long> {

    
}