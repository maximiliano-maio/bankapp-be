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

import lombok.Data;

@Data
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
  // When setting new Standing Order from client-side, a hashcode value is passed to search Client
  private String hashcode;

  
}