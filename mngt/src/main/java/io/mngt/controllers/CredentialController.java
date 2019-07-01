package io.mngt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.mngt.domain.Credential;
import io.mngt.services.CredentialService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin("*")
@RestController
public class CredentialController {

  @Autowired
  private CredentialService credentialService;

  @PostMapping
  @RequestMapping("/login/user")
  public @ResponseBody Credential validateUser(@RequestBody Credential c) {
    return credentialService.login(c.getUsername(), c.getPassword());
  }

  @GetMapping("/auth/authState")
  public @ResponseBody int authenticateUser(@RequestParam(name = "code") String hashcode){
    
    return credentialService.isAuthenticated(Integer.parseInt(hashcode));
  }

  @GetMapping("/logout")
  public @ResponseBody boolean logout(@RequestParam(name = "code") String hashcode) {

    return credentialService.logout(Integer.parseInt(hashcode));
  }
  @GetMapping(path = "/all")
  public @ResponseBody Iterable<Credential> getAllUsers() {
    return credentialService.findAll();
  }
  


  
}