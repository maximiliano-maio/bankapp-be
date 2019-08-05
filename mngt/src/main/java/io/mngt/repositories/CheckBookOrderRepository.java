package io.mngt.repositories;

import org.springframework.data.repository.CrudRepository;

import io.mngt.entity.CheckBookOrder;


public interface CheckBookOrderRepository extends CrudRepository<CheckBookOrder, Long> {
    
}