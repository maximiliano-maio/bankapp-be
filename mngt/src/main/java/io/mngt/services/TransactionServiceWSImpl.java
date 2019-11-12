package io.mngt.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;

import io.mngt.business.externaltransactions.TransactionXML;
import io.mngt.dao.TransactionDao;
import io.mngt.entity.Transaction;

@WebService(endpointInterface = "io.mngt.transactions.TransactionService")
public class TransactionServiceWSImpl implements TransactionServiceWS {

  @Autowired
  private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
  @Autowired
  private TransactionDao transactionDao;
  @Autowired
  private TransactionXML transactionFile;
  @Autowired
  private ObjectMapper objectMapper;
  
  @Override
  public String getOutgoingTransactions() throws JsonProcessingException {
    String todayString = simpleDateFormat.format(getTodayDate());
    List<Transaction> list = transactionDao.findTransactionByExternalAccount(true);

    transactionFile.setList(list);
    transactionFile.setDate(todayString);
    transactionFile.setFileName(todayString + "-transactions-" + list.size());
    transactionFile.setTransactionQty(list.size());

    for (Transaction t : list) {
      transactionFile.setTransactionTotalAmount(t.getAmount() + transactionFile.getTransactionTotalAmount());
    }

    String xml = objectMapper.writeValueAsString(transactionFile);
    return xml;
  }

  static Date getTodayDate() {
    Date today = new Date();

    try {
      today = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return today;
  }
  
}