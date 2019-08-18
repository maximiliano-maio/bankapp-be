package io.mngt.dao;

import io.mngt.entity.ClientOperationsLog;

public interface ClientOperationsLogDao {

  void persistLog(ClientOperationsLog clientOperationsLog);
  void updateLog(ClientOperationsLog clientOperationsLog);
  
}