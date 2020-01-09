package io.mngt.services.transactions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
  private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
  
  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getTransactionsRequest")
  @ResponsePayload
  public GetTransactionsResponse getTransaction(@RequestPayload GetTransactionsRequest request){
    GetTransactionsResponse response =  new GetTransactionsResponse();
    OutgoingTransaction transaction;
    List<Transaction> list = new ArrayList<>();
    List<OutgoingTransaction> transactionList = new ArrayList<>();

    list = accountingService.getOutgoingTransactionsList();
    DateFormat format = simpleDateFormat;
    
    for(Transaction t : list) {
      transaction = new OutgoingTransaction();
      transaction.setAmount(t.getAmount());
      transaction.setCreditAccount(t.getCreditAccount());
      transaction.setDebitAccount(t.getDebitAccount());
      String dateString = format.format(t.getDate());
      try {
        XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateString);
        transaction.setDateTime(date);
      } catch (DatatypeConfigurationException e) {
        e.printStackTrace();
      }
      
      transactionList.add(transaction);
      
    }
    response.setTransaction(transactionList);
    return response;
  }
  
}