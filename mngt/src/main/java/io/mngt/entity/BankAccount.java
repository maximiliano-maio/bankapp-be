package io.mngt.entity;

import javax.persistence.Column;
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
public class BankAccount {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonBackReference(value = "client_bankaccount")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "client_id")
  private Client client;

  @Column(unique = true)
  private int bankAccountNumber;
  private String status;

  public BankAccount(){}

  public BankAccount(Client client, int bankAccountNumber, String status){
    this.client = client;
    this.bankAccountNumber = bankAccountNumber;
    this.status = status;
  }

}