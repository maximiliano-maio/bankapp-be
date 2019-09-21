package io.mngt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.mngt.dao.ClientDao;
import io.mngt.dao.ContactInfoDao;
import io.mngt.entity.Client;
import io.mngt.entity.ContactInfo;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientDao clientDao;
    @Autowired
    private ContactInfoDao contactInfoDao;

    @Override
    @Transactional
    public Client setClient(Client client) {
        return clientDao.save(client);
    }

    @Override
    public Client findClientById(Long id) {
        Client client = clientDao.findById(id);
        if (client == null) return null;
        return client;
    }

    @Override
    public ContactInfo setContactInformation(ContactInfo contactInfo) {
        return contactInfoDao.save(contactInfo);
    }

    @Override
    public Client findByClientId(String clientId) {
        Client client = clientDao.findByClientId(clientId);
        if (client == null) return null;

        return client;
    }

    @Override
    public Client findClientAndCredentialAssociatedByClientId(String clientId) {
        return clientDao.findClientAndCredentialAssociatedByClientId(clientId);
    }

    @Override
    public void updateValidationCode(Client client, int validationCode) {
        clientDao.updateValidationCode(client, validationCode);
    }

    
}