package io.mngt.converters;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import io.mngt.commands.ContactInfoCommand;
import io.mngt.domain.ContactInfo;
import lombok.Synchronized;

@Component
public class ContactInfoToContactInfoCommand implements Converter<ContactInfo, ContactInfoCommand> {

  private ClientToClientCommand clientCommandConverter;

  public ContactInfoToContactInfoCommand(@Lazy ClientToClientCommand clientCommandConverter) {
    this.clientCommandConverter = clientCommandConverter;
    
  }

  @Synchronized
  @Nullable
  @Override
  public ContactInfoCommand convert(ContactInfo source) {
    if (source == null)
      return null;

    final ContactInfoCommand contactInfoCommand = new ContactInfoCommand();

    contactInfoCommand.setApartment(source.getApartment());
    contactInfoCommand.setBuilding(source.getBuilding());
    contactInfoCommand.setCellphone(source.getCellphone());
    contactInfoCommand.setCity(source.getCity());

    if (source.getClient() != null) {
      contactInfoCommand.setClient(clientCommandConverter.convert(source.getClient()));
    }

    contactInfoCommand.setDistribution(source.getDistributionAgreement());
    contactInfoCommand.setEmail(source.getEmail());
    contactInfoCommand.setEntrance(source.getEntrance());
    contactInfoCommand.setId(source.getId());
    contactInfoCommand.setPostalBox(source.getPostalBox());
    contactInfoCommand.setPostalCode(source.getPostalCode());
    contactInfoCommand.setStreet(source.getStreet());
    contactInfoCommand.setTelephone(source.getTelephone());

    return contactInfoCommand;
  }

  
}