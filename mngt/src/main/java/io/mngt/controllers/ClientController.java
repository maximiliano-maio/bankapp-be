package io.mngt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.mngt.domain.Client;
import io.mngt.services.ClientService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin("*")
@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    @RequestMapping({ "/registerclient" })    
    public Client create(@RequestBody Client client) {
        return clientService.setClient(client);
    }

    @GetMapping
    @RequestMapping("/getClients")
    public Iterable<Client> getClients() {
        return clientService.findAll();
    }

    @GetMapping
    @RequestMapping("/user")
    public Client getLocalAccountBalance(@RequestParam(name = "code") Long id) {
        return clientService.findClient(id);
    }
    
    

}