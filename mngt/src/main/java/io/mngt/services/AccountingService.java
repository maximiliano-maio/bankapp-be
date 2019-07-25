package io.mngt.services;

import java.util.List;

import io.mngt.domain.BalanceILS;
import io.mngt.domain.Client;
import io.mngt.domain.Transaction;
import io.mngt.domain.Transfer;

public interface AccountingService {
  List<BalanceILS> findBalanceIlsListByHashcode(int hashcode);
  List<BalanceILS> findLast5BalanceIlsListByHashcode(int hashcode);
  BalanceILS findLastBalanceByHashcode(int hashcode);
  BalanceILS findLastBalanceByClient(Client c);
  
  Client findClientByBankAccount(int bankAccountNumber);
  Client findClientByHashcode(int hashcode);
  
  Transaction setTransaction(Transfer data);
  Transfer isTransferPossible(Transfer data);

  void doTransaction();
  List<Transaction> getOutgoingTransactions();

}