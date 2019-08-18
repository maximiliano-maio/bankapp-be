package io.mngt.business;

import org.springframework.stereotype.Component;

import io.mngt.entity.BalanceILS;
import io.mngt.entity.Client;
import io.mngt.entity.Transfer;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 *  @Deprecated
 */

@NoArgsConstructor
@Data
// @Component
public class BankCommission {

  private BalanceILS lastBalanceILS;
  private BalanceILS serviceCharge;
  private Transfer data;
  private Client client;

  
}