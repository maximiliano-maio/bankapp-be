package io.mngt.repositories;

import org.springframework.data.repository.CrudRepository;

import io.mngt.entity.BankAccount;

public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {
  
}