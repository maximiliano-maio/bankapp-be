package io.mngt.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.*;
import io.mngt.entity.ContactInfo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class ContactInfoDaoImplIntegrationTest {

  @TestConfiguration
  static class ContactInfoTestContextConfiguration{
    
    @Bean
    public ContactInfo contactInfo(){
      return new ContactInfo();
    }
  }

  @Autowired
  private ContactInfoDao contactInfoDao;
  @Autowired
  private ContactInfo newContactInfo;

  @Rollback(value = true)
  @Test
  public void givenContactInfo_whenSave_thenReturnContactInfo(){
    newContactInfo.setCellphone("0515819763");
    ContactInfo contactInfoStored = contactInfoDao.save(newContactInfo);
    assertThat(contactInfoStored.getCellphone()).isEqualTo(newContactInfo.getCellphone());
  }
  
}