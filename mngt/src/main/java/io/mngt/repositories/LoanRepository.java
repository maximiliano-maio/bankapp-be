package io.mngt.repositories;

import org.springframework.data.repository.CrudRepository;

import io.mngt.domain.Loan;

public interface LoanRepository extends CrudRepository<Loan, Long> {

  
}