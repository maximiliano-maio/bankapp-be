package io.mngt.services;

import java.util.Set;

import io.mngt.commands.ClientCommand;
import io.mngt.commands.ContactInfoCommand;
import io.mngt.domain.Client;


public interface ClientService {
    ClientCommand setClient(ClientCommand clientCommand);
    ContactInfoCommand setContactInformation(ContactInfoCommand contactInfo);
    boolean deleteClient(Long id);
    Client findClient(Long id);
    Set<Client> findAll();
}