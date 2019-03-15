package io.mngt.repositories;

import org.springframework.data.repository.CrudRepository;

import io.mngt.domain.CheckBook;


public interface CheckBookOrderRepository extends CrudRepository<CheckBook, Long> {

    
    
}