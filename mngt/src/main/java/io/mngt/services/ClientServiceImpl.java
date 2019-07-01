package io.mngt.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.mngt.dao.ClientDao;
import io.mngt.dao.ContactInfoDao;
import io.mngt.domain.Client;
import io.mngt.domain.ContactInfo;
import io.mngt.exceptions.NotFoundException;

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
    public boolean deleteClient(Long id) {
        Optional<Client> user = clientDao.findById(id);
        if (!user.isPresent())
            throw new NotFoundException("Client not found");
        clientDao.delete(user.get());
        return true;
    }

    @Override
    public Client findClient(Long id) {
        Optional<Client> user = clientDao.findById(id);
        if (!user.isPresent())
            return null;
        return user.get();
    }

    @Override
    public Iterable<Client> findAll() {
        return clientDao.findAll();
    }

    @Override
    public ContactInfo setContactInformation(ContactInfo contactInfo) {
        return contactInfoDao.save(contactInfo);
    }

    @Override
    public Client getClient(Long id) {
        Client client = new Client();
        Optional<Client> optionalClient = clientDao.findById(id);
        
        if (!optionalClient.isPresent()) return null;
        
        return client;
    }
    
}