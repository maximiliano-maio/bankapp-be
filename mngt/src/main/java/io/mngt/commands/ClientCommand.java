package io.mngt.commands;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientCommand {
  private Long id;
  private Set<CheckBookCommand> checkBookSet;
  private int clientId;
  private String firstName;
  private String lastName;
  private int maritalStatus;
  private ContactInfoCommand contactInfo;
  
}