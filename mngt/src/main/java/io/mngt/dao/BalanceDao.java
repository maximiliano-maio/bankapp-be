package io.mngt.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.mngt.domain.BalanceILS;
import io.mngt.domain.Client;

@Repository
public interface BalanceDao extends JpaRepository<BalanceILS, Long> {

  public List<BalanceILS> findByClient(Client client);


}