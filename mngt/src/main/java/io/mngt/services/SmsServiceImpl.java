package io.mngt.services;

import java.io.IOException;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.sms.SmsSubmissionResponse;
import com.nexmo.client.sms.SmsSubmissionResponseMessage;
import com.nexmo.client.sms.messages.TextMessage;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

  @Override
  public SmsSubmissionResponse sendSMS(TextMessage message) throws IOException, NexmoClientException {
    NexmoClient client = new NexmoClient.Builder().apiKey("29253252").apiSecret("okVNhWcbJbaU8Nek").build();
    SmsSubmissionResponse response = client.getSmsClient().submitMessage(message); 
    for (SmsSubmissionResponseMessage responseMessage : response.getMessages()) {
      log.info("SMS Service: " + responseMessage.toString());
    }  
    return response;
  }

  @Override
  public SmsSubmissionResponse sendSMS_Test() throws IOException, NexmoClientException {
    NexmoClient client = new NexmoClient.Builder().apiKey("29253252").apiSecret("okVNhWcbJbaU8Nek").build();
    TextMessage message = new TextMessage("Nexmo", "972515819763", "Hello from Nexmo");
    SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);
    for (SmsSubmissionResponseMessage responseMessage : response.getMessages()) {
      log.info("SMS Service: " + responseMessage.toString());
    }
    return response;
  }
  

  
  
  

  
}