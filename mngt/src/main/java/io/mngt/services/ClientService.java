package io.mngt.services;

import io.mngt.entity.Client;
import io.mngt.entity.ContactInfo;

public interface ClientService {
    Client setClient(Client client);
    ContactInfo setContactInformation(ContactInfo contactInfo);
    boolean deleteClient(Long id);
    Client findClientById(Long id);
    Client findByClientId(String clientId);
    Iterable<Client> findAll();
    Client findClientAndCredentialAssociatedByClientId(String clientId);
    void updateValidationCode(Client client, int validationCode);
}