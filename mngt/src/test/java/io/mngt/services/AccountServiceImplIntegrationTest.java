package io.mngt.services;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isNotNull;

import io.mngt.entity.BalanceILS;
import io.mngt.entity.Credential;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class AccountServiceImplIntegrationTest {

  @TestConfiguration
  static class AccountingServiceImplTestContextConfiguration {

    @Bean
    public AccountingService accountingService() {
      return new AccountingServiceImpl();
    }
  }

  @Autowired
  private AccountingService accountingService;
  @Autowired
  private CredentialService credentialService;

  @Test
  public void givenHashcode_whenFindBalanceIlsListByHashcode_thenReturnBalanceILSList(){
    Credential credential = credentialService.login("maxi", "maio");
    int hashcode = credential.getHashcode();

    List<BalanceILS> list = accountingService.findBalanceIlsListByHashcode(hashcode);
    assertThat(list).isNotNull();

  }
}