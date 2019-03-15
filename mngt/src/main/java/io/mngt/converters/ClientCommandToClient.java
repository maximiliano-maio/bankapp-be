package io.mngt.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import io.mngt.commands.ClientCommand;
import io.mngt.domain.Client;
import lombok.Synchronized;

@Component
public class ClientCommandToClient implements Converter<ClientCommand, Client> {

  
  private final CheckBookCommandToCheckBook checkBookConverter;
  private final ContactInfoCommandToContactInfo contactInfoConverter;

  public ClientCommandToClient(
    CheckBookCommandToCheckBook checkBookConverter, 
    ContactInfoCommandToContactInfo contactInfoConverter) {
    this.checkBookConverter = checkBookConverter;
    this.contactInfoConverter = contactInfoConverter;
  }

  @Synchronized
  @Nullable
  @Override
  public Client convert(ClientCommand source) {
    if(source == null) return null;

    final Client client = new Client();

    client.setId(source.getId());
    client.setFirstName(source.getFirstName());
    client.setLastName(source.getLastName());
    client.setClientId(source.getClientId());
    client.setMaritalStatus(source.getMaritalStatus());

    if (source.getCheckBookSet() != null && source.getCheckBookSet().size() > 0) {
      source.getCheckBookSet().
        forEach(checkbook -> client.getCheckBookSet().
                              add(checkBookConverter.convert(checkbook))
      );
    }
 
    if (source.getContactInfo() != null) {
      client.setContactInfo(contactInfoConverter.convert(source.getContactInfo()));
      
    }

    return client;

  }


  
}