package io.mngt.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import io.mngt.entity.BalanceILS;
import io.mngt.entity.Credential;
import io.mngt.services.CredentialService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class BalanceDaoImplIntegrationTest {

  @Autowired
  private BalanceDao balanceDao;
  @Autowired
  private CredentialService credentialService;

  @Test
  public void givenBalance_whenSave_thenBalanceStored() {
    BalanceILS balanceILS = new BalanceILS();
    balanceILS.setId(1000L);
    balanceILS.setDescription("Test");

    BalanceILS balanceStored = balanceDao.save(balanceILS);
    assertThat(balanceStored.getDescription()).isEqualTo(balanceILS.getDescription());
  }

  @Test
  public void givenClientAnd5ResultsRequired_whenFindLastBalancesByClient_thenReturnBalanceListSize5() {
    Credential credential = credentialService.login("maxi", "maio");
    credential = credentialService.findCredentialByHashcode(credential.getHashcode());

    List<BalanceILS> balance = balanceDao.findLastBalancesByClient(credential.getClient(), 5);
    assertThat(balance).hasSize(5);
  }

  @Test
  public void givenClientAnd10ResultsRequired_whenFindLastBalancesByClient_thenReturnBalanceListSize10() {
    Credential credential = credentialService.login("maxi", "maio");
    credential = credentialService.findCredentialByHashcode(credential.getHashcode());

    List<BalanceILS> balance = balanceDao.findLastBalancesByClient(credential.getClient(), 10);
    assertThat(balance).hasSize(10);
  }

  @Test
  public void givenExistingId_whenFindById_thenReturnBalance(){
    BalanceILS balance = balanceDao.findById(1L);
    assertThat(balance).isNotNull();
  }

  @Test
  public void givenNonExistingId_whenFindById_thenReturnNull() {
    BalanceILS balance = balanceDao.findById(100000L);
    assertThat(balance).isNull();
  }
  
}