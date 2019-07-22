package io.mngt.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.mngt.domain.Transaction;

@Repository
public interface TransactionDao extends JpaRepository<Transaction, Long> {

  
}