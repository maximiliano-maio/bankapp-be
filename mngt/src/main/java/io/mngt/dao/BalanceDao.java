package io.mngt.dao;

import java.util.List;

import io.mngt.entity.BalanceILS;
import io.mngt.entity.Client;

public interface BalanceDao {

  List<BalanceILS> findByClient(Client client);
  BalanceILS save(BalanceILS balanceILS);

  List<BalanceILS> findLastBalancesByClient(Client c, int x);
  BalanceILS findById(long id);

}