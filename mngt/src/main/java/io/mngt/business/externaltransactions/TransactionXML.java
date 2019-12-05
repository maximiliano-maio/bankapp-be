package io.mngt.business.externaltransactions;

import java.util.List;

import io.mngt.entity.Transaction;


public class TransactionXML {

  private String date;
  private String fileName;
  // @JacksonXmlElementWrapper(useWrapping = true, localName = "operations")
  private List<Transaction> item;
  private int transactionQty;
  private long transactionTotalAmount;
  
  public void setList(List<Transaction> list) {
    this.item = list;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public List<Transaction> getItem() {
    return item;
  }

  public void setItem(List<Transaction> item) {
    this.item = item;
  }

  public int getTransactionQty() {
    return transactionQty;
  }

  public void setTransactionQty(int transactionQty) {
    this.transactionQty = transactionQty;
  }

  public long getTransactionTotalAmount() {
    return transactionTotalAmount;
  }

  public void setTransactionTotalAmount(long transactionTotalAmount) {
    this.transactionTotalAmount = transactionTotalAmount;
  }

  

    
}