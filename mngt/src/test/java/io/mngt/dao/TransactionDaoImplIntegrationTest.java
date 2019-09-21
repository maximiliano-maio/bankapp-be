package io.mngt.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import io.mngt.entity.Transaction;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class TransactionDaoImplIntegrationTest {

  @Autowired
  private TransactionDao transactionDao;

  @Rollback
  @Transactional
  @Test
  public void givenStatus_whenFindTransactionByStatus_thenReturnTransactionList(){
    Transaction transaction = new Transaction();
    transaction.setId(1L);
    transaction.setStatus(0);
    transactionDao.save(transaction);

    transaction.setId(2L);
    transactionDao.save(transaction);
    List<Transaction> list = transactionDao.findTransactionByStatus(0);
    assertThat(list).hasSize(2);
  }
  
  @Rollback
  @Transactional
  @Test
  public void givenIsExternalAccount_whenFindTransactionByExternalAccount_thenReturnTransactionList() {
    Transaction transaction = new Transaction();
    transaction.setId(1L);
    transaction.setAccountExternal(true);
    transactionDao.save(transaction);

    transaction.setId(2L);
    transaction.setAccountExternal(false);
    transactionDao.save(transaction);
    List<Transaction> list = transactionDao.findTransactionByExternalAccount(true);
    assertThat(list).hasSize(1);
  }

  @Rollback
  @Test
  public void givenTransaction_whenSave_thenReturnTransaction(){
    Transaction transaction = new Transaction();
    transaction.setId(1L);
    Transaction t = transactionDao.save(transaction);
    assertThat(t).isNotNull();
    
  }
}