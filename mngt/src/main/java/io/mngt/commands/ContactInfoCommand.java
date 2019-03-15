package io.mngt.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContactInfoCommand {
  private Long id;
  private ClientCommand client;
  private String telephone;
  private String cellphone;
  private String email;
  private int distribution;
  private String street;
  private String building;
  private String entrance;
  private String apartment;
  private String postalCode;
  private String postalBox;
  private String city;
  
}