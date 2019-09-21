package io.mngt.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import io.mngt.entity.Client;
import io.mngt.entity.Credential;
import io.mngt.entity.StandingOrder;
import io.mngt.entity.StandingOrderFrecuency;
import io.mngt.services.CredentialService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class StandingOrderDaoImplIntegrationTest {

  @Autowired
  private StandingOrderDao standingOrderDao;
  @Autowired
  private CredentialService credentialService;
  @Autowired
  private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

  @Transactional
  @Test
  public void givenStandingOrder_whenSave_thenReturnStandingOrder() throws Exception {
    Credential credential = credentialService.login("maxi", "maio");
    Client client = credentialService.findCredentialByHashcode(credential.getHashcode()).getClient();

    StandingOrder standingOrder = new StandingOrder();
    standingOrder.setClient(client);
    standingOrder.setAmount(200);
    standingOrder.setCompanyName("CompanyTest");
    standingOrder.setDate(new Date());
    standingOrder.setFrecuency(StandingOrderFrecuency.YEARLY);
    standingOrder.setStatus(0);
    StandingOrder standingOrderStored = standingOrderDao.save(standingOrder);
    assertThat(standingOrderStored.getAmount()).isEqualTo(200);
  }

  @Transactional
  @Test
  public void givenDate_whenFindAllStandingOrdersByDate_thenReturnStandingOrderList() throws Exception {
    Credential credential = credentialService.login("maxi", "maio");
    Client client = credentialService.findCredentialByHashcode(credential.getHashcode()).getClient();

    StandingOrder standingOrder = new StandingOrder();
    standingOrder.setClient(client);
    standingOrder.setAmount(100);
    standingOrder.setCompanyName("CompanyTest");
    standingOrder.setDate(simpleDateFormat.parse(simpleDateFormat.format(new Date())));
    standingOrder.setFrecuency(StandingOrderFrecuency.WEEKLY);
    standingOrder.setStatus(0);
    standingOrderDao.save(standingOrder);
    
    Date date = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
    List<StandingOrder> list = standingOrderDao.findAllStandingOrdersByDate(date);
    assertThat(list).hasSize(1);
  }

  @Transactional
  @Test
  public void givenDateAndStatus_whenFindAllStandingOrdersByDate_thenReturnStandingOrderList() throws Exception {
    Credential credential = credentialService.login("maxi", "maio");
    Client client = credentialService.findCredentialByHashcode(credential.getHashcode()).getClient();

    StandingOrder standingOrder = new StandingOrder();
    standingOrder.setClient(client);
    standingOrder.setAmount(100);
    standingOrder.setCompanyName("CompanyTest");
    standingOrder.setDate(simpleDateFormat.parse(simpleDateFormat.format(new Date())));
    standingOrder.setFrecuency(StandingOrderFrecuency.WEEKLY);
    standingOrder.setStatus(0);
    standingOrderDao.save(standingOrder);

    Date date = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
    int status = 0;
    List<StandingOrder> list = standingOrderDao.findAllStandingOrdersByDateAndStatus(date, status);
    assertThat(list).hasSize(1);
  }

  
}