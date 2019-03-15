package io.mngt.converters;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import io.mngt.commands.CheckBookCommand;
import io.mngt.domain.CheckBook;
import lombok.Synchronized;

@Component
public class CheckBookCommandToCheckBook implements Converter<CheckBookCommand, CheckBook>{

  private final ClientCommandToClient clientConverter;

  public CheckBookCommandToCheckBook(@Lazy ClientCommandToClient clientConverter) {
    this.clientConverter = clientConverter;
  }

  @Synchronized
  @Nullable
  @Override
  public CheckBook convert(CheckBookCommand source) {
    if (source == null) return null;

    final CheckBook checkBook = new CheckBook();

    checkBook.setCheckAmount(source.getCheckAmount());
    checkBook.setCheckType(source.getCheckType());

    if (source.getClient() != null) {
      checkBook.setClient(clientConverter.convert(source.getClient()));
    }
    
    checkBook.setCurrency(source.getCurrency());
    checkBook.setFirstCheck(source.getFirstCheck());
    checkBook.setId(source.getId());
    checkBook.setLastCheck(source.getLastCheck());
    checkBook.setOrderNumber(source.getOrderNumber());
    checkBook.setStatus(source.getStatus());

    return checkBook;
  }

  
}