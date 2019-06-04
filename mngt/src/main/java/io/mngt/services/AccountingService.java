package io.mngt.services;

import java.util.Set;

import io.mngt.domain.BalanceILS;

public interface AccountingService {
  Set<BalanceILS> getLocalAccountBalance(Integer id);
  
}