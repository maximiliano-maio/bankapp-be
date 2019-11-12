package io.mngt.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.transaction.Transactional;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;


import lombok.Data;

@Transactional
@Data
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

}