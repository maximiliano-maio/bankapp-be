package io.mngt.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CheckBookCommand {
  private Long id;
  private int orderNumber;
  private String status;
  private ClientCommand client;
  private String checkType;
  private int firstCheck;
  private int lastCheck;
  private int checkAmount;
  private String currency;
  
}