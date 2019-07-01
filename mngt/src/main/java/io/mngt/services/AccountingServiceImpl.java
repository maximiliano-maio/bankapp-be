package io.mngt.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.mngt.domain.BalanceILS;
import io.mngt.domain.Client;
import io.mngt.domain.Credential;
import io.mngt.repositories.ClientRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountingServiceImpl implements AccountingService {


  @Autowired
  private CredentialService credentialService;

  @Autowired
  private ClientRepository clientRepository;

  @Override
  public List<BalanceILS> getLocalAccountBalance(int hashcode) {
    Credential credential = credentialService.getCredential(hashcode);
    if (credential == null) return null;  

    Client client = credential.getClient();
    if (client == null) return null;

    return client.getBalance();
  
  }


  public List<BalanceILS> getBalances(Client client) {
    
    return null;
    // List<BalanceILS> balanceList = new ArrayList<>(); 
    // Client c = clientRepository.findByClientId(client.getClientId());

    // if (c == null) return null;

    // c.getBalance().forEach( b -> {
    //   log.info("Each balance description: " + b.getDescription());
    //   balanceList.add(b);
    // });

    // return balanceList;
  }

}