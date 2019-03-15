package io.mngt.converters;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import io.mngt.commands.ClientCommand;
import io.mngt.domain.Client;
import lombok.Synchronized;

@Component
public class ClientToClientCommand implements Converter<Client, ClientCommand> {

  private final CheckBookToCheckBookCommand checkBookCommandConverter;
  private final ContactInfoToContactInfoCommand contactInfoCommandConverter;

  public ClientToClientCommand(
    @Lazy CheckBookToCheckBookCommand checkBookCommandConverter,
    ContactInfoToContactInfoCommand contactInfoCommandConverter
  ) {
    this.checkBookCommandConverter = checkBookCommandConverter;
    this.contactInfoCommandConverter = contactInfoCommandConverter;
  }

  @Synchronized
  @Nullable
  @Override
  public ClientCommand convert(Client source) {
    if (source == null)
      return null;

    final ClientCommand clientCommand = new ClientCommand();

    clientCommand.setId(source.getId());
    clientCommand.setFirstName(source.getFirstName());
    clientCommand.setLastName(source.getLastName());
    clientCommand.setClientId(source.getClientId());
    clientCommand.setMaritalStatus(source.getMaritalStatus());

    if (source.getCheckBookSet() != null && source.getCheckBookSet().size() > 0) {
      source.getCheckBookSet()
          .forEach(checkbook -> clientCommand.getCheckBookSet().add(checkBookCommandConverter.convert(checkbook)));
    }

    if (source.getContactInfo() != null) {
      clientCommand.setContactInfo(contactInfoCommandConverter.convert(source.getContactInfo()));

    }

    return clientCommand;
  }

  
}