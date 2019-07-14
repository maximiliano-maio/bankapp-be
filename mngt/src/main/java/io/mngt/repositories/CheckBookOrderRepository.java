package io.mngt.repositories;

import org.springframework.data.repository.CrudRepository;

import io.mngt.domain.CheckBookOrder;


public interface CheckBookOrderRepository extends CrudRepository<CheckBookOrder, Long> {
    
}