package io.mngt.controllers;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import io.mngt.dao.BankAccountDao;
import io.mngt.entity.BalanceILS;
import io.mngt.entity.BankAccount;
import io.mngt.entity.Credential;
import io.mngt.entity.StandingOrder;
import io.mngt.entity.StandingOrderFrecuency;
import io.mngt.entity.Transfer;
import io.mngt.services.AccountingService;
import io.mngt.services.CredentialService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class AccountingControllerIntegrationTest {

  @Autowired
  private AccountingService accountingService;
  @Autowired
  private CredentialService credentialService;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private BankAccountDao bankAccountDao;
  @Autowired
  private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

  @Test
  public void givenHashcode_whenGettingShortAccountBalance_thenBalanceListReturned() throws Exception {
    Credential maxiCredential = credentialService.login("maxi", "maio");
    String hashcode = Integer.toString(maxiCredential.getHashcode());
    MvcResult mvcResult =  this.mockMvc.perform(get("/getShortAccountBalances").param("code", hashcode))
      .andExpect(status().isOk())
      .andReturn();
    List<BalanceILS> balanceList = objectMapper.readValue(
      mvcResult.getResponse().getContentAsString(), new TypeReference<List<BalanceILS>>(){});
    
    BalanceILS balanceMatcher = new BalanceILS();

    balanceMatcher.setCredit(8000);
    balanceMatcher.setId(11L);
    Date date = new GregorianCalendar(2018, Calendar.JUNE, 2).getTime();
    balanceMatcher.setDate(date);
    balanceMatcher.setDescription("משכורת");
    balanceMatcher.setBalance(76279);

    assertEquals(balanceMatcher, balanceList.get(0));

  }

  @Test
  public void givenHashcode_whenGettingShortAccountBalance_thenBalanceListSize5IsReturned() throws Exception {
    Credential maxiCredential = credentialService.login("maxi", "maio");
    String hashcode = Integer.toString(maxiCredential.getHashcode());
    MvcResult mvcResult = this.mockMvc.perform(get("/getShortAccountBalances").param("code", hashcode))
        .andExpect(status().isOk()).andReturn();
    List<BalanceILS> balanceList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
        new TypeReference<List<BalanceILS>>() {
        });
    
    assertEquals(5, balanceList.size());
  }


  @Test
  public void givenHashcode_whenGettingLongAccountBalance_thenBalanceListReturned() throws Exception {
    Credential maxiCredential = credentialService.login("maxi", "maio");
    String hashcode = Integer.toString(maxiCredential.getHashcode());
    MvcResult mvcResult = this.mockMvc.perform(get("/getLongAccountBalances").param("code", hashcode))
        .andExpect(status().isOk()).andReturn();
    List<BalanceILS> balanceList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
        new TypeReference<List<BalanceILS>>() {
        });

    BalanceILS balanceMatcher = new BalanceILS();

    balanceMatcher.setCredit(8000);
    balanceMatcher.setId(11L);
    Date date = new GregorianCalendar(2018, Calendar.JUNE, 2).getTime();
    balanceMatcher.setDate(date);
    balanceMatcher.setDescription("משכורת");
    balanceMatcher.setBalance(76279);

    assertEquals(balanceMatcher, balanceList.get(0));

  }

  @Test
  public void givenHashcode_whenGettingLongAccountBalance_thenBalanceListSize11IsReturned() throws Exception {
    Credential maxiCredential = credentialService.login("maxi", "maio");
    String hashcode = Integer.toString(maxiCredential.getHashcode());
    MvcResult mvcResult = this.mockMvc.perform(get("/getLongAccountBalances").param("code", hashcode))
        .andExpect(status().isOk()).andReturn();
    List<BalanceILS> balanceList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
        new TypeReference<List<BalanceILS>>() {
        });

    assertEquals(11, balanceList.size());
  }

  @Test
  public void givenTransferData_whenIsTranferPossible_thenReturnTrue() throws Exception {
    Credential maxiCredential = credentialService.login("maxi", "maio");
    String hashcode = Integer.toString(maxiCredential.getHashcode());
    maxiCredential = credentialService.findCredentialByHashcode(maxiCredential.getHashcode());
    BankAccount bankAccount = bankAccountDao.findBankAccountByClient(maxiCredential.getClient());
    
    Transfer data = new Transfer();
    // available amount
    data.setAmount(100);
    data.setBeneficiary("999");
    data.setCommission(20);
    data.setHashcode(hashcode);
    data.setAccountNumber(bankAccount.getBankAccountNumber());
    data.setReason(0);
    byte[] content = objectMapper.writeValueAsBytes(data);

    this.mockMvc.perform(
      post("/verifyTransferFromAccount").contentType(MediaType.APPLICATION_JSON).content(content))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.transferPossible", is(true)));

  }

  @Test
  public void givenTransferData_whenIsTranferPossible_thenReturnFalse() throws Exception {
    Credential maxiCredential = credentialService.login("maxi", "maio");
    String hashcode = Integer.toString(maxiCredential.getHashcode());
    maxiCredential = credentialService.findCredentialByHashcode(maxiCredential.getHashcode());
    BankAccount bankAccount = bankAccountDao.findBankAccountByClient(maxiCredential.getClient());

    Transfer data = new Transfer();
    // not available amount
    data.setAmount(100000);
    data.setBeneficiary("999");
    data.setCommission(20);
    data.setHashcode(hashcode);
    data.setAccountNumber(bankAccount.getBankAccountNumber());
    data.setReason(0);
    byte[] content = objectMapper.writeValueAsBytes(data);

    this.mockMvc.perform(post("/verifyTransferFromAccount").contentType(MediaType.APPLICATION_JSON).content(content))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.transferPossible", is(false)));

  }

  @Test
  public void givenTransferData_whenSetTransaction_thenTransactionObjectReturned() throws Exception {
    Credential maxiCredential = credentialService.login("maxi", "maio");
    String hashcode = Integer.toString(maxiCredential.getHashcode());
    maxiCredential = credentialService.findCredentialByHashcode(maxiCredential.getHashcode());
    BankAccount bankAccount = bankAccountDao.findBankAccountByClient(maxiCredential.getClient());

    Transfer data = new Transfer();
    data.setAmount(100);
    data.setBeneficiary("999");
    data.setCommission(20);
    data.setHashcode(hashcode);
    data.setAccountNumber(bankAccount.getBankAccountNumber());
    data.setReason(0);

    byte[] content = objectMapper.writeValueAsBytes(data);
    this.mockMvc.perform(post("/setTransfer").contentType(MediaType.APPLICATION_JSON).content(content))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.status", is(0)));
  }

  @Test
  public void givenStandingOrder_whenSetStandingOrder_thenStandingOrderObjectReturned() throws Exception {
    Credential maxiCredential = credentialService.login("maxi", "maio");
    String hashcode = Integer.toString(maxiCredential.getHashcode());
    maxiCredential = credentialService.findCredentialByHashcode(maxiCredential.getHashcode());
    
    StandingOrder standingOrder = new StandingOrder();
    standingOrder.setId(1L);
    standingOrder.setAmount(10);
    standingOrder.setCompanyName("Test Company");
    standingOrder.setClient(maxiCredential.getClient());
    standingOrder.setFrecuency(StandingOrderFrecuency.WEEKLY);
    standingOrder.setDate(simpleDateFormat.parse(simpleDateFormat.format(new Date())));

    byte[] content = objectMapper.writeValueAsBytes(standingOrder);
    this.mockMvc.perform(
      post("/setStandingOrder")
      .param("code", hashcode)
      .contentType(MediaType.APPLICATION_JSON).content(content))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is(0)));
  }
  
}