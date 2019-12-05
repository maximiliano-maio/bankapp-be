package io.mngt.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
public class Loan implements Serializable {
  

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @JsonBackReference(value = "client_loan")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "client_id")
  private Client client;

  private String loanType;
  private int sum;
  private int quota;
  private Float interest;

  public Loan() { }

  public Loan(String loanType, int sum, int quota, Float interest) {
    this.loanType = loanType;
    this.sum = sum;
    this.quota = quota;
    this.interest = interest;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public String getLoanType() {
    return loanType;
  }

  public void setLoanType(String loanType) {
    this.loanType = loanType;
  }

  public int getSum() {
    return sum;
  }

  public void setSum(int sum) {
    this.sum = sum;
  }

  public int getQuota() {
    return quota;
  }

  public void setQuota(int quota) {
    this.quota = quota;
  }

  public Float getInterest() {
    return interest;
  }

  public void setInterest(Float interest) {
    this.interest = interest;
  }

  
  
}