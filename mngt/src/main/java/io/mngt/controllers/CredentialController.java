package io.mngt.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
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

import io.mngt.entity.Credential;
import io.mngt.exceptions.NotFoundException;
import io.mngt.services.CredentialService;
import lombok.extern.slf4j.Slf4j;

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
  
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public void handleNotFound(Exception e) {
    log.info("Not found exception, " + e.getMessage());
  }

  
}