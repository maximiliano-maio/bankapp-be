package io.mngt.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
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
  
}