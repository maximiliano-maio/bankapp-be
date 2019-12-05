package io.mngt.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity(name = "ClientOperationsLog")
@Table(name = "Log")
public class ClientOperationsLog {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private Integer operationCode;
  private String operationDescription;
  private Integer ErrorCode;
  private String errorDescription;
  private LocalDateTime date;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "client_id")
  private Client client;

  public Integer getOperationCode() {
    return operationCode;
  }

  public void setOperationCode(Integer operationCode) {
    this.operationCode = operationCode;
  }

  public String getOperationDescription() {
    return operationDescription;
  }

  public void setOperationDescription(String operationDescription) {
    this.operationDescription = operationDescription;
  }

  public Integer getErrorCode() {
    return ErrorCode;
  }

  public void setErrorCode(Integer errorCode) {
    ErrorCode = errorCode;
  }

  public String getErrorDescription() {
    return errorDescription;
  }

  public void setErrorDescription(String errorDescription) {
    this.errorDescription = errorDescription;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  
  
}