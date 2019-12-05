package io.mngt.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.transaction.Transactional;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;


@Transactional
@Entity
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  private int debitAccount;
  private int creditAccount;
  private boolean isAccountExternal;
  private int amount;
  private Date date;
  // Status: 0 - recorded; Status: 1 - Performed
  @JacksonXmlText(value = true)
  private int status;

  public String toString(){
    return id + "," + debitAccount + "," + creditAccount + "," + amount + "," + dateToString();
  }

  private String dateToString(){
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