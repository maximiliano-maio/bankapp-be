package io.mngt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.mngt.domain.Client;
import io.mngt.domain.Credential;
import io.mngt.services.ClientService;
import io.mngt.services.CredentialService;

@CrossOrigin("*")
@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private CredentialService credentialService;

    @PostMapping
    @RequestMapping({ "/registerclient" })    
    public Client create(@RequestBody Client client) {
        return clientService.setClient(client);
    }


    @GetMapping
    @RequestMapping("/user")
    public @ResponseBody Client getLocalAccountBalance(@RequestParam(name = "code") String hashcode) {
        Credential credential = credentialService.findCredentialByHashcode(Integer.parseInt(hashcode));
        if(credential ==  null) return null;
        
        return credential.getClient();
    }
    
    

}