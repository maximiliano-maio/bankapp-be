package io.mngt.soapws;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ClassUtils;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import io.mngt.services.transactions.GetTransactionsRequest;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TransactionClientIntegrationTest extends WebServiceGatewaySupport {

  private Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

  @LocalServerPort
  private int port = 0;

  @Before
  public void init() throws Exception {
    marshaller.setPackagesToScan(ClassUtils.getPackageName(GetTransactionsRequest.class));
    marshaller.afterPropertiesSet();
  }

  @Test
  public void givenTransactionResquest_whenGetTransaction_thenTransactionResponseReturned() throws IOException {
    WebServiceTemplate ws = new WebServiceTemplate(marshaller);
    GetTransactionsRequest request = new GetTransactionsRequest();
    request.setName("novalue");

    assertThat(ws.marshalSendAndReceive("http://localhost:" + port + "/ws", request)).isNotNull();
    
  }
}