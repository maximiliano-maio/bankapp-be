package io.mngt.repositories;

import org.springframework.data.repository.CrudRepository;

import io.mngt.entity.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

  
}