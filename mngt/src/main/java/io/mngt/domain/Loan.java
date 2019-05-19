package io.mngt.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
public class Loan {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne
  @JoinColumn(name = "client_id")
  @JsonBackReference
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