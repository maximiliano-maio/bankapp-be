package io.mngt.services.transactions;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import io.mngt.entity.Transaction;
import io.mngt.services.AccountingService;

@Endpoint
public class TransactionEndpoint {

  @Autowired
  private AccountingService accountingService;

  private static final String NAMESPACE_URI = "http://mngt.io/services/transactions";

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getTransactionsRequest")
  @ResponsePayload
  public GetTransactionsResponse getTransaction(@RequestPayload GetTransactionsRequest request)
      throws DatatypeConfigurationException {
    GetTransactionsResponse response =  new GetTransactionsResponse();
    List<Transaction> list = new ArrayList<>();
    list = accountingService.getOutgoingTransactionsList();
    OutgoingTransaction transaction = new OutgoingTransaction();
    List<OutgoingTransaction> transactionList = new ArrayList<>();
    for(Transaction t : list) {
      transaction.setAmount(t.getAmount());
      transaction.setCreditAccount(t.getCreditAccount());
      transaction.setDebitAccount(t.getDebitAccount());
      
      XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(t.getDate().toString());
      transaction.setDate(date);
      transactionList.add(transaction);
      
    }
    response.setTransaction(transactionList);
    return response;
  }
  
}