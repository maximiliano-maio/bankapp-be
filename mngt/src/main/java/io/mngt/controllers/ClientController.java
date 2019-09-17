package io.mngt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.mngt.entity.Client;
import io.mngt.entity.Credential;
import io.mngt.exceptions.NotFoundException;
import io.mngt.services.ClientService;
import io.mngt.services.CredentialService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        log.info("Client registration..");
        return clientService.setClient(client);
    }


    @GetMapping
    @RequestMapping("/user")
    public @ResponseBody Client getClient(@RequestParam(name = "code") String hashcode) {
        Credential credential = credentialService.findCredentialByHashcode(Integer.parseInt(hashcode));
        if(credential ==  null) return null;
        
        return credential.getClient();
    }
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public void handleNotFound(Exception e) {
        log.info("Not found exception, " + e.getMessage());
    }
    

}