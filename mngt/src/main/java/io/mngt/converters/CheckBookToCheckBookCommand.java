package io.mngt.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import io.mngt.commands.CheckBookCommand;
import io.mngt.domain.CheckBook;
import lombok.Synchronized;

@Component
public class CheckBookToCheckBookCommand implements Converter<CheckBook, CheckBookCommand> {

  private final ClientToClientCommand clientCommandConverter;

  public CheckBookToCheckBookCommand(ClientToClientCommand clientCommandConverter) {
    this.clientCommandConverter = clientCommandConverter;
  }

  @Synchronized
  @Nullable
  @Override
  public CheckBookCommand convert(CheckBook source) {
    if (source == null)
      return null;

    final CheckBookCommand checkBookCommand = new CheckBookCommand();

    checkBookCommand.setCheckAmount(source.getCheckAmount());
    checkBookCommand.setCheckType(source.getCheckType());

    if (source.getClient() != null) {
      checkBookCommand.setClient(clientCommandConverter.convert(source.getClient()));
    }

    checkBookCommand.setCurrency(source.getCurrency());
    checkBookCommand.setFirstCheck(source.getFirstCheck());
    checkBookCommand.setId(source.getId());
    checkBookCommand.setLastCheck(source.getLastCheck());
    checkBookCommand.setOrderNumber(source.getOrderNumber());
    checkBookCommand.setStatus(source.getStatus());

    return checkBookCommand;
  }

  
}