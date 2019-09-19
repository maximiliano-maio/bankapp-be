package io.mngt.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import io.mngt.entity.BalanceILS;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class BalanceDaoImplIntegrationTest {

  @Autowired
  private BalanceDao balanceDao;

  @Test
  public void givenBalance_whenSave_thenBalanceStored() {
    BalanceILS balanceILS = new BalanceILS();
    balanceILS.setId(1000L);
    balanceILS.setDescription("Test");

    BalanceILS balanceStored = balanceDao.save(balanceILS);
    assertThat(balanceStored.getDescription()).isEqualTo(balanceILS.getDescription());

  }


  
}