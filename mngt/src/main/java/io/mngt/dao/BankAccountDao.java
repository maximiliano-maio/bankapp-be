package io.mngt.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.mngt.domain.BankAccount;

@Repository
public interface BankAccountDao extends JpaRepository<BankAccount, Long> {

  // BankAccount findByBankAccountNumber(int bankAccountNumber);
}