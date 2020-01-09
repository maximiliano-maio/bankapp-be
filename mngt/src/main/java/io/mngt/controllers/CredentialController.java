package io.mngt.controllers;

import java.io.IOException;

import com.nexmo.client.NexmoClientException;
import com.nexmo.client.sms.SmsSubmissionResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import io.mngt.dao.CredentialDao;
import io.mngt.entity.Client;
import io.mngt.entity.Credential;
import io.mngt.exceptions.NotFoundException;
import io.mngt.services.ClientService;
import io.mngt.services.CredentialService;
import io.mngt.services.SmsService;

@CrossOrigin("*")
@RestController
public class CredentialController {

  private final static Logger logger = LoggerFactory.getLogger(CredentialController.class);

  @Autowired
  private CredentialService credentialService;
  @Autowired
  private ClientService clientService;
  @Autowired
  private SmsService smsService;
  @Autowired
  private CredentialDao credentialDao;

  @PostMapping
  @RequestMapping("/login/user")
  public @ResponseBody Credential validateUser(@RequestBody Credential c) {
    return credentialService.login(c.getUsername(), c.getPassword());
  }

  @GetMapping("/auth/authState")
  public @ResponseBody int authenticateUser(@RequestParam(name = "code") String hashcode)
      throws IOException, NexmoClientException {
    return credentialService.isAuthenticated(Integer.parseInt(hashcode));
  }

  @GetMapping("/logout")
  public @ResponseBody boolean logout(@RequestParam(name = "code") String hashcode) {
    return credentialService.logout(Integer.parseInt(hashcode));
  }

  @PostMapping
  @RequestMapping("/sendValidationCode")
  public SmsSubmissionResponse sendValidationCode(@RequestBody Client client) throws IOException, NexmoClientException {
    int validationCode = credentialService.getValidationCode();
    
    Client c = clientService.findClientAndCredentialAssociatedByClientId(client.getClientId());
    clientService.updateValidationCode(c, validationCode);
    String cellphone = c.getContactInfo().getCellphone();
    
    return smsService.sendValidationCode(cellphone, Integer.toString(validationCode));
  }

  @PostMapping
  @RequestMapping("/validateCode")
  public boolean validateValidationCode(@RequestBody Client data){
    int validationCode = data.getValidationCode();
    String clientId = data.getClientId();
    boolean isValidated = credentialService.isValidationCodeCorrect(validationCode, clientId);
    if(isValidated) {
      Client client = clientService.findByClientId(clientId);
      clientService.updateValidationCode(client, 0);
    }

    return isValidated;
  }

  @PostMapping
  @RequestMapping("/setCredential")
  public boolean setCredential(@RequestBody Credential data){
    String clientId = data.getRole(); // Field 'role' used to transfer 'clientId' from client-side
    Client client = clientService.findByClientId(clientId);
    if( credentialDao.findByUsername(data.getUsername()) != null ) return false;
    
    Credential credential = credentialService.setCredential(client, data.getUsername(), data.getPassword(), data.getMail());
    if (credential == null) return false;

    return true;
  }
  
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public void handleNotFound(Exception e) {
    logger.info("Not found exception, " + e.getMessage());
  }

  
}