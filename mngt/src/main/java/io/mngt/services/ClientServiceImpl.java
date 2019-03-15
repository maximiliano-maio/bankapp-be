package io.mngt.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.mngt.commands.ClientCommand;
import io.mngt.commands.ContactInfoCommand;
import io.mngt.converters.ClientCommandToClient;
import io.mngt.converters.ClientToClientCommand;
import io.mngt.converters.ContactInfoCommandToContactInfo;
import io.mngt.converters.ContactInfoToContactInfoCommand;
import io.mngt.domain.Client;
import io.mngt.domain.ContactInfo;
import io.mngt.repositories.ClientRepository;
import io.mngt.repositories.ContactInfoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ContactInfoRepository contactInfoRepository;
    private final ClientToClientCommand clientToClientCommand;
    private final ClientCommandToClient clientCommandToClient;
    private final ContactInfoCommandToContactInfo contactInfoCommandToContactInfo;
    private final ContactInfoToContactInfoCommand contactInfoToContactInfoCommand;

    public ClientServiceImpl(
        ClientRepository clientRepository,
        ContactInfoRepository contactInfoRepository,
        @Lazy ClientToClientCommand clientToClientCommand,
        ClientCommandToClient clientCommandToClient,
        ContactInfoCommandToContactInfo contactInfoCommandToContactInfo,
        ContactInfoToContactInfoCommand contactInfoToContactInfoCommand) {
        this.clientRepository = clientRepository;
        this.contactInfoRepository = contactInfoRepository;
        this.clientToClientCommand = clientToClientCommand;
        this.clientCommandToClient = clientCommandToClient;
        this.contactInfoCommandToContactInfo = contactInfoCommandToContactInfo;
        this.contactInfoToContactInfoCommand = contactInfoToContactInfoCommand;
    }

    private Set<Client> clientSet;

    @Override
    @Transactional
    public ClientCommand setClient(ClientCommand clientCommand) {
        Client client = clientCommandToClient.convert(clientCommand);
        Client savedClient = clientRepository.save(client);
        log.debug("Saved Client id: " + savedClient.getClientId());
        return clientToClientCommand.convert(savedClient);
    }

    @Override
    public boolean deleteClient(Long id) {
        Optional<Client> user = clientRepository.findById(id);
        if (!user.isPresent())
            return false;
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
    public Set<Client> findAll() {
        clientSet = new HashSet<Client>();
        clientSet.add((Client) clientRepository.findAll());
        return clientSet;
    }

    @Override
    public ContactInfoCommand setContactInformation(ContactInfoCommand contactInfoCommand) {
        ContactInfo contactInfo = contactInfoCommandToContactInfo.convert(contactInfoCommand);
        ContactInfo savedContactInfo =  contactInfoRepository.save(contactInfo);
        log.debug("Cellphone is: " + contactInfo.getCellphone());
        return contactInfoToContactInfoCommand.convert(savedContactInfo);
    }

   


    
}