package io.mngt.services;

import java.util.List;

import io.mngt.domain.BalanceILS;

public interface AccountingService {
  List<BalanceILS> getLocalAccountBalance(int id);
  
}