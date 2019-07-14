package io.mngt.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import io.mngt.domain.BankAccount;

public interface BankAccountDao extends JpaRepository<BankAccount, Long> {

  BankAccount findByBankAccountNumber(int bankAccountNumber);
}