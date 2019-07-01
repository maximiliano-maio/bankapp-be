package io.mngt.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.mngt.domain.BalanceILS;
import io.mngt.domain.Client;

public interface BalanceDao extends JpaRepository<BalanceILS, Long> {

  public List<BalanceILS> findByClient(Client client);


}