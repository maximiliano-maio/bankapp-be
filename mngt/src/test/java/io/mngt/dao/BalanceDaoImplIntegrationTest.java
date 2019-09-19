package io.mngt.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.mngt.entity.BalanceILS;

// @SpringBootTest
// @TestPropertySource(locations = "classpath:application-integrationtest.properties")
@RunWith(SpringRunner.class)
@DataJpaTest
public class BalanceDaoImplIntegrationTest {
  
  @Autowired
  private BalanceDao balanceDao;

  @Test
  public void givenBalance_whenPersist_thenBalanceStored() {
    BalanceILS balanceILS = new BalanceILS();
    balanceILS.setId(1L);
    balanceILS.setDescription("Test");

    balanceDao.persist(balanceILS);
    BalanceILS balanceStored = balanceDao.findById(1L);
    assertThat(balanceStored.getDescription()).isEqualTo(balanceILS.getDescription());

  }

  @Test
  public void givenBalance_whenSave_thenBalanceStored() {
    BalanceILS balanceILS = new BalanceILS();
    balanceILS.setId(1L);
    balanceILS.setDescription("Test");

    BalanceILS balanceStored = balanceDao.save(balanceILS);
    assertThat(balanceStored.getDescription()).isEqualTo(balanceILS.getDescription());

  }


  
}