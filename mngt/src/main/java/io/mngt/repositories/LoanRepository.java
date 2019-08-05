package io.mngt.repositories;

import org.springframework.data.repository.CrudRepository;

import io.mngt.entity.Loan;

public interface LoanRepository extends CrudRepository<Loan, Long> {

  
}