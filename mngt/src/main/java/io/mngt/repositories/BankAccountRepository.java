package io.mngt.repositories;

import org.springframework.data.repository.CrudRepository;

import io.mngt.domain.BankAccount;
import io.mngt.domain.Client;

public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {
  
}