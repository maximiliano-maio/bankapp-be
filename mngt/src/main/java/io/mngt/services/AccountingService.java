package io.mngt.services;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.mngt.entity.BalanceILS;
import io.mngt.entity.Client;
import io.mngt.entity.StandingOrder;
import io.mngt.entity.Transaction;
import io.mngt.entity.Transfer;

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
  void doStandingOrder();
  String getOutgoingTransactions() throws JsonProcessingException;
  StandingOrder setStandingOrder(StandingOrder data, String hashcode);
  void setNextStandingOrder();
  BalanceILS debitAccount(Client client, int amount, String description);
  BalanceILS creditAccount(Client client, int amount, String description);

}