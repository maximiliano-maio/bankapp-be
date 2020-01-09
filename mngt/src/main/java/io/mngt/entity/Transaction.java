package io.mngt.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.transaction.Transactional;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import io.mngt.jobs.domain.TransactionItem;

@Transactional
@Entity
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private int debitAccount;
  private int creditAccount;
  @Column(nullable = true)
  private boolean isAccountExternal;
  private int amount;
  private Date date;
  // Status: 0 - recorded; Status: 1 - Performed
  @JacksonXmlText(value = true)
  @Column(nullable = true)
  private int status;

  public Transaction() {
  }

  public Transaction(TransactionItem item) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    this.id = Long.parseLong(item.getId()) + 100L;
    this.debitAccount = Integer.parseInt(item.getDebitAccount());
    this.creditAccount = Integer.parseInt(item.getCreditAccount());
    this.amount = Integer.parseInt(item.getAmount());
    this.isAccountExternal = false;
    
    try {
      this.date = sdf.parse(item.getDate());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public String toString() {
    return id + "," + debitAccount + "," + creditAccount + "," + amount + "," + dateToString();
  }

  private String dateToString() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    return sdf.format(this.date);
  }

  public int getDebitAccount() {
    return debitAccount;
  }

  public void setDebitAccount(int debitAccount) {
    this.debitAccount = debitAccount;
  }

  public int getCreditAccount() {
    return creditAccount;
  }

  public void setCreditAccount(int creditAccount) {
    this.creditAccount = creditAccount;
  }

  public boolean isAccountExternal() {
    return isAccountExternal;
  }


  public void setAccountExternal(boolean isAccountExternal) {
    this.isAccountExternal = isAccountExternal;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  

  

  

}