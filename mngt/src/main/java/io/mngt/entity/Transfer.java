package io.mngt.entity;

import lombok.Data;

@Data
public class Transfer {
  private int amount;
  private String beneficiary;
  private int accountNumber;
  private int reason;
  private String hashcode;
  private boolean isTransferPossible;
  private int commission;

  public Transfer() {
    this.isTransferPossible = false;
    this.commission = 0;
  }

  
}