package io.mngt.services;

import io.mngt.domain.Client;
import io.mngt.domain.ContactInfo;

public interface ClientService {
    Client setClient(Client client);
    ContactInfo setContactInformation(ContactInfo contactInfo);
    boolean deleteClient(Long id);
    Client findClient(Long id);
    Iterable<Client> findAll();
}