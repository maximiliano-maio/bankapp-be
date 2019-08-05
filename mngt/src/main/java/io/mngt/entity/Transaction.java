package io.mngt.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;

@Data
@Entity
@JacksonXmlRootElement(localName = "Transaction")
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @JacksonXmlProperty(isAttribute = true)
  private Long id;

  @JacksonXmlProperty
  private int debitAccount;
  
  @JacksonXmlProperty
  private int creditAccount;
  private boolean isAccountExternal;
  
  @JacksonXmlProperty
  private int amount;
  @JacksonXmlProperty
  private Date date;
  
  // Status: 0 - recorded; Status: 1 - Performed
  private int status;

  public Transaction(int debitAccount, int creditAccount, int amount, Date date, boolean isAccountExternal){
    this.debitAccount = debitAccount;
    this.creditAccount = creditAccount;
    this.amount = amount;
    this.date = date;
    this.status = 0;
    this.isAccountExternal = isAccountExternal;
  }

  public Transaction(int debitAccount, int creditAccount, int amount, Date date) {
    this.debitAccount = debitAccount;
    this.creditAccount = creditAccount;
    this.amount = amount;
    this.date = date;
    this.status = 0;
    this.isAccountExternal = false;
  }

  public Transaction() {
    super();
  }

  
}