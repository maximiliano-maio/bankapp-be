package io.mngt.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import io.mngt.entity.BankAccount;
import io.mngt.entity.Client;
import io.mngt.entity.Credential;
import io.mngt.services.CredentialService;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class BankAccountDaoImplIntegrationTest {

  @Autowired
  private BankAccountDao bankAccountDao;
  @Autowired
  private CredentialService credentialService;

  @Test
  public void givenClient_whenFindBankAccountByClient_thenReturnBankAccount(){
    Credential credential = credentialService.login("maxi", "maio");
    Client client = credentialService.findCredentialByHashcode(credential.getHashcode()).getClient();

    BankAccount bankAccount = bankAccountDao.findBankAccountByClient(client);
    assertThat(bankAccount.getBankAccountNumber()).isEqualTo(100200);
  }

  @Test
  public void givenExistentBankAccountNumber_whenFindBankAccountByAccountNumber_thenReturnBankAccount() {
    int bankAccountNumber = 100200;
    BankAccount bankAccount = bankAccountDao.findBankAccountByAccountNumber(bankAccountNumber);
    assertThat(bankAccount.getClient().getClientId()).isEqualTo("338016777");
  }
  
  @Test
  public void givenNonExistentBankAccountNumber_whenFindBankAccountByAccountNumber_thenReturnNull() {
    int bankAccountNumber = 66666;
    BankAccount bankAccount = bankAccountDao.findBankAccountByAccountNumber(bankAccountNumber);
    assertThat(bankAccount).isNull();
  }
}