package io.mngt.converters;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import io.mngt.commands.ContactInfoCommand;
import io.mngt.domain.ContactInfo;
import lombok.Synchronized;

@Component
public class ContactInfoCommandToContactInfo implements Converter<ContactInfoCommand, ContactInfo> {

  private final ClientCommandToClient clientConverter;

  public ContactInfoCommandToContactInfo(@Lazy ClientCommandToClient clientConverter) {
    this.clientConverter = clientConverter;
  }

  @Synchronized
  @Nullable
  @Override
  public ContactInfo convert(ContactInfoCommand source) {
    if (source == null) return null;

    final ContactInfo contactInfo = new ContactInfo();

    contactInfo.setApartment(source.getApartment());
    contactInfo.setBuilding(source.getBuilding());
    contactInfo.setCellphone(source.getCellphone());
    contactInfo.setCity(source.getCity());

    if (source.getClientId() != null) {
      contactInfo.setClientId(clientConverter.convert(source.getClientId()));
    }
    
    contactInfo.setDistributionAgreement(source.getDistribution());
    contactInfo.setEmail(source.getEmail());
    contactInfo.setEntrance(source.getEntrance());
    contactInfo.setId(source.getId());
    contactInfo.setPostalBox(source.getPostalBox());
    contactInfo.setPostalCode(source.getPostalCode());
    contactInfo.setStreet(source.getStreet());
    contactInfo.setTelephone(source.getTelephone());
    
    return contactInfo;
  }

  
}