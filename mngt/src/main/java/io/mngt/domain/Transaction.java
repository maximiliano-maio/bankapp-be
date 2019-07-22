package io.mngt.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private int debitAccount;
  private int creditAccount;
  private int amount;
  private Date date;
  // Status: 0 - recorded; Status: 1 - Performed
  private int status;

  public Transaction(int debitAccount, int creditAccount, int amount, Date date){
    this.debitAccount = debitAccount;
    this.creditAccount = creditAccount;
    this.amount = amount;
    this.date = date;
    this.status = 0;
  }

  public Transaction() {
    super();
  }

  
}