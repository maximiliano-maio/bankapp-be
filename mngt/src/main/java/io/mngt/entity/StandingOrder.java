package io.mngt.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
public class StandingOrder {
  
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  
  @JsonBackReference(value = "client_standingorder")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "client_id")
  private Client client;

  private int amount;
  private String companyName;
  private Date date;
  
  @Enumerated(EnumType.STRING)
  private StandingOrderFrecuency frecuency;
  private int status;
  // @Deprecated:  When setting new Standing Order from client-side, a hashcode value is passed to search Client
  // private String hashcode;
  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public StandingOrderFrecuency getFrecuency() {
    return frecuency;
  }

  public void setFrecuency(StandingOrderFrecuency frecuency) {
    this.frecuency = frecuency;
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