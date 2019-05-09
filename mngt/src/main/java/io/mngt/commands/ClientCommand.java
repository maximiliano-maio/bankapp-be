package io.mngt.commands;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientCommand {
  private Long id;
  private Set<CheckBookCommand> checkBookSet;
  
  @NotNull
  @NotBlank
  private String clientId;
  
  @NotBlank
  @Size(min = 3, max = 30)
  private String firstName;
  
  @NotBlank
  @Size(min = 3, max = 30)
  private String lastName;
  private String maritalStatus;
  private ContactInfoCommand contactInfo;
  
}