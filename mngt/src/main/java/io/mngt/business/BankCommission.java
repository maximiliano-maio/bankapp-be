package io.mngt.business;

import io.mngt.entity.BalanceILS;
import io.mngt.entity.Client;
import io.mngt.entity.Transfer;

/*
 *  @Deprecated
 */

public class BankCommission {

  private BalanceILS lastBalanceILS;
  private BalanceILS serviceCharge;
  private Transfer data;
  private Client client;

  public BankCommission() {
  }
  

  public BalanceILS getLastBalanceILS() {
    return lastBalanceILS;
  }

  public void setLastBalanceILS(BalanceILS lastBalanceILS) {
    this.lastBalanceILS = lastBalanceILS;
  }

  public BalanceILS getServiceCharge() {
    return serviceCharge;
  }

  public void setServiceCharge(BalanceILS serviceCharge) {
    this.serviceCharge = serviceCharge;
  }

  public Transfer getData() {
    return data;
  }

  public void setData(Transfer data) {
    this.data = data;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  

  
  
}