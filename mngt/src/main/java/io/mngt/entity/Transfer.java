package io.mngt.entity;

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

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public String getBeneficiary() {
    return beneficiary;
  }

  public void setBeneficiary(String beneficiary) {
    this.beneficiary = beneficiary;
  }

  public int getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(int accountNumber) {
    this.accountNumber = accountNumber;
  }

  public int getReason() {
    return reason;
  }

  public void setReason(int reason) {
    this.reason = reason;
  }

  public String getHashcode() {
    return hashcode;
  }

  public void setHashcode(String hashcode) {
    this.hashcode = hashcode;
  }

  public boolean isTransferPossible() {
    return isTransferPossible;
  }

  public void setTransferPossible(boolean isTransferPossible) {
    this.isTransferPossible = isTransferPossible;
  }

  public int getCommission() {
    return commission;
  }

  public void setCommission(int commission) {
    this.commission = commission;
  }

  

  
}