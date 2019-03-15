package io.mngt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.mngt.commands.ClientCommand;
import io.mngt.commands.ContactInfoCommand;
import io.mngt.services.ClientService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins="http://localhost:4200/", maxAge=60)
@RestController
@RequestMapping({"/register"})
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public ClientCommand create(
        @RequestBody ClientCommand client, 
        @RequestBody ContactInfoCommand contactInfo) {
        clientService.setContactInformation(contactInfo);
        return clientService.setClient(client);
    }
    
    

}