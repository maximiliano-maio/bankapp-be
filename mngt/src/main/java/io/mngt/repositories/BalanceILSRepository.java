package io.mngt.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.mngt.domain.BalanceILS;
import io.mngt.domain.Client;

public interface BalanceILSRepository extends CrudRepository<BalanceILS, Long>{
  // Following method not working, will be implemented JPQL instead of.
  Iterable<BalanceILS> findAllByClient(Client client);
  
}