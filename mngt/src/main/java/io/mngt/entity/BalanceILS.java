package io.mngt.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.NamedQuery;

import lombok.Data;

@Data
@Entity
@NamedQuery(query = "SELECT b FROM BalanceILS b WHERE b.client = :id", name = "find balances by client")
public class BalanceILS {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonBackReference(value = "client_balanceils")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "client_id")
  private Client client;

  private Date date;
  private String description;
  
  private int debt;
  private int credit;
  private int balance;
  

}
