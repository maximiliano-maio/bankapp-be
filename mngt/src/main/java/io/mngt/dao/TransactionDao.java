package io.mngt.dao;

import java.util.List;

import io.mngt.entity.Transaction;

public interface TransactionDao {

  List<Transaction> findTransactionByStatus(int status);
  Transaction save(Transaction transaction);
  List<Transaction> findTransactionByExternalAccount(boolean isAccountExternal);
  
}