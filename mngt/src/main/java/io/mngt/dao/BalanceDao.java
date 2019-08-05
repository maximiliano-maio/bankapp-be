package io.mngt.dao;

import java.util.List;

import io.mngt.entity.BalanceILS;
import io.mngt.entity.Client;

public interface BalanceDao {

  void persist(BalanceILS balance);
  List<BalanceILS> findByClient(Client client);
  BalanceILS save(BalanceILS balanceILS);

  List<BalanceILS> findLastBalancesByClient(Client c, int x);

}