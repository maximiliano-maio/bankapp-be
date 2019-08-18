package io.mngt.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Entity
@Component
@JacksonXmlRootElement(localName = "Transaction")
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @JacksonXmlProperty(isAttribute = true)
  private Long id;

  @JacksonXmlProperty
  private int debitAccount;
  
  @JacksonXmlProperty
  private int creditAccount;
  private boolean isAccountExternal;
  
  @JacksonXmlProperty
  private int amount;
  @JacksonXmlProperty
  private Date date;
  
  // Status: 0 - recorded; Status: 1 - Performed
  private int status;

}