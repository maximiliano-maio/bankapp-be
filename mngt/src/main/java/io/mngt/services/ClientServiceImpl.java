package io.mngt.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.mngt.domain.Client;
import io.mngt.domain.ContactInfo;
import io.mngt.exceptions.NotFoundException;
import io.mngt.repositories.ClientRepository;
import io.mngt.repositories.ContactInfoRepository;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ContactInfoRepository contactInfoRepository;

    @Override
    @Transactional
    public Client setClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public boolean deleteClient(Long id) {
        Optional<Client> user = clientRepository.findById(id);
        if (!user.isPresent())
            throw new NotFoundException("Client not found");
        clientRepository.delete(user.get());
        return true;
    }

    @Override
    public Client findClient(Long id) {
        Optional<Client> user = clientRepository.findById(id);
        if (!user.isPresent())
            return null;
        return user.get();
    }

    @Override
    public Iterable<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public ContactInfo setContactInformation(ContactInfo contactInfo) {
        return contactInfoRepository.save(contactInfo);
    }

    @Override
    public Client getClient(Long id) {
        Client client = new Client();
        Optional<Client> optionalClient = clientRepository.findById(id);
        
        if (!optionalClient.isPresent()) return null;
        
        return client;
    }
    
}