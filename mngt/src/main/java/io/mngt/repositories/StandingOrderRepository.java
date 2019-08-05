package io.mngt.repositories;

import org.springframework.data.repository.CrudRepository;

import io.mngt.entity.StandingOrder;

public interface StandingOrderRepository extends CrudRepository<StandingOrder, Long> {

  
}