package io.mngt.business.externaltransactions;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import io.mngt.entity.Transaction;
import lombok.Data;

@Data
// @JacksonXmlRootElement(localName = "transactions")
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

    
}