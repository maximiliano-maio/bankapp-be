package io.mngt.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mngt.domain.Credential;
import io.mngt.repositories.CredentialRepository;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(path = "/login")
public class CredentialController {

  private static final Logger log = LoggerFactory.getLogger(CredentialController.class);

  @Autowired
  private CredentialRepository userRepository;

  @PostMapping(path = "/login/user")
  public @ResponseBody boolean validateUser(@RequestParam String username, @RequestParam String password) {
    log.info("++username received: " + username);
    log.info("++password received: " + password);
    // Testing purpose
    Credential userToValidate = new Credential();
    userToValidate = userRepository.findByUsername(username);

    if (userToValidate.getUsername() != "maxi") { 
      log.info("Username is not correct");
      return false;
    }
    if (userToValidate.getPassword() != "maio") {
      log.info("Password is not correct");
      return false;
    }
    
    log.info("Username and Password are correct");
    return true;
  }

  @GetMapping(path = "/all")
  public @ResponseBody Iterable<Credential> getAllUsers() {
      return userRepository.findAll();
  }
  


  
}