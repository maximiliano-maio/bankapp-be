package io.mngt.services;

import io.mngt.entity.Client;
import io.mngt.entity.ContactInfo;

public interface ClientService {
    Client setClient(Client client);
    Client getClient(Long id);
    ContactInfo setContactInformation(ContactInfo contactInfo);
    boolean deleteClient(Long id);
    Client findClient(Long id);
    Iterable<Client> findAll();
}