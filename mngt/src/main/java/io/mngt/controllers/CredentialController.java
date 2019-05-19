package io.mngt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mngt.domain.Credential;
import io.mngt.services.CredentialService;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class CredentialController {

  @Autowired
  private CredentialService credentialService;

  @PostMapping
  @RequestMapping("/login/user")
  public @ResponseBody Credential validateUser(@RequestBody Credential c) {
    Credential credential = credentialService.login(c.getUsername(), c.getPassword());
    return credential;
  }

  @GetMapping(path = "/all")
  public @ResponseBody Iterable<Credential> getAllUsers() {
    return credentialService.findAll();
  }
  


  
}