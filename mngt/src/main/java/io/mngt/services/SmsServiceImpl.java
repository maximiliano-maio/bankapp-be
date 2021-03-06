package io.mngt.services;

import java.io.IOException;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.sms.SmsSubmissionResponse;
import com.nexmo.client.sms.SmsSubmissionResponseMessage;
import com.nexmo.client.sms.messages.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class SmsServiceImpl implements SmsService {

  private final static Logger logger = LoggerFactory.getLogger("SmsServiceImpl.class");

  @Value("${nexmo.creds.api-key}")
  String nexmoApiKey;

  @Value("${nexmo.creds.secret}")
  String nexmoSecret;

  @Override
  public SmsSubmissionResponse sendSMS(TextMessage message) throws IOException, NexmoClientException {
    NexmoClient client = new NexmoClient.Builder().apiKey(nexmoApiKey).apiSecret(nexmoSecret).build();
    SmsSubmissionResponse response = client.getSmsClient().submitMessage(message); 
    for (SmsSubmissionResponseMessage responseMessage : response.getMessages()) {
      logger.info("SMS Service: " + responseMessage.toString());
    }  
    return response;
  }

  @Override
  public SmsSubmissionResponse sendValidationCode(String cellphone, String validationCode) throws IOException, NexmoClientException {
    
    String israeliNumber = "972";
    israeliNumber = israeliNumber.concat(Integer.toString(Integer.parseInt(cellphone)));
    NexmoClient client = new NexmoClient.Builder().apiKey(nexmoApiKey).apiSecret(nexmoSecret).build();
    TextMessage message = new TextMessage("Nexmo", israeliNumber, "קוד אימות:" + validationCode + "\n", true);
    
    SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);
    for (SmsSubmissionResponseMessage responseMessage : response.getMessages()) {
      logger.info("SMS Service: " + responseMessage.toString());
    }
    return response;
  }

  @Override
  public SmsSubmissionResponse sendSMS_Test() throws IOException, NexmoClientException {
    NexmoClient client = new NexmoClient.Builder().apiKey(nexmoApiKey).apiSecret(nexmoSecret).build();
    TextMessage message = new TextMessage("Nexmo", "972515819763", "Hello from Nexmo");
    SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);
    for (SmsSubmissionResponseMessage responseMessage : response.getMessages()) {
      logger.info("SMS Service: " + responseMessage.toString());
    }
    return response;
  }
  

  
  
  

  
}