package io.mngt.services;

import java.util.List;

import io.mngt.domain.BalanceILS;
import io.mngt.domain.Client;
import io.mngt.domain.Transfer;

public interface AccountingService {
  List<BalanceILS> getLocalAccountBalance(int id);
  List<BalanceILS> findLast5(int hashcode);
  BalanceILS getLastBalanceUsingHashcode(int hashcode);
  BalanceILS setTransfer(Transfer data);
  BalanceILS getLastBalanceUsingClient(Client c);
  Transfer isTransferPossible(Transfer data);
  
}