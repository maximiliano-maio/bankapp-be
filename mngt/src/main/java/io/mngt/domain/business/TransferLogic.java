package io.mngt.domain.business;

import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.mngt.domain.BalanceILS;
import io.mngt.domain.Transfer;
import lombok.Data;

@Data
public class TransferLogic {

  private BalanceILS lastBalanceILS;
  private BalanceILS serviceCharge;
  private Transfer data;

  public TransferLogic(Transfer data, BalanceILS lastBalance) {
    this.data = data;
    this.lastBalanceILS = lastBalance;
    this.serviceCharge = setServiceCharge();

  }

  private BalanceILS setServiceCharge() {

    this.serviceCharge = new BalanceILS();
    this.serviceCharge.setDebt(20);
    this.serviceCharge.setDescription("עמלת העברה");
    this.serviceCharge.setDate(new Date());
    this.serviceCharge.setBalance(this.lastBalanceILS.getBalance() - this.serviceCharge.getDebt());
    this.serviceCharge.setCredit(0);

    return this.serviceCharge;
  }

  public Transfer isTransferPossible() {
    Transfer returnedData = new Transfer();
    returnedData = this.data;
    
    int balanceAvailable = this.lastBalanceILS.getBalance();
    int amountToTransfer = this.serviceCharge.getDebt() + data.getAmount();

    if (balanceAvailable < amountToTransfer) {
      returnedData.setTransferPossible(false);
      returnedData.setCommission(0);
    }else{
      returnedData.setTransferPossible(true);
      returnedData.setCommission(this.serviceCharge.getDebt());
    }

    return returnedData;
  }
}