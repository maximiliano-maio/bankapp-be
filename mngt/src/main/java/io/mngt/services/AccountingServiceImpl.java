package io.mngt.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.mngt.domain.BalanceILS;
import io.mngt.repositories.BalanceILSRepository;
import io.mngt.repositories.CredentialRepository;

@Service
public class AccountingServiceImpl implements AccountingService {

  @Autowired
  private BalanceILSRepository balanceILSRepository;

  @Autowired
  private CredentialRepository credentialRepository;

  @Override
  public Set<BalanceILS> getLocalAccountBalance(Integer hashcode) {
    return credentialRepository.findByHashcode(hashcode).getClient().getBalance();
  }

  
}