package io.mngt.services;

import javax.jws.WebService;

import com.fasterxml.jackson.core.JsonProcessingException;

@WebService
public interface TransactionServiceWS {

  String getOutgoingTransactions() throws JsonProcessingException;
  
}