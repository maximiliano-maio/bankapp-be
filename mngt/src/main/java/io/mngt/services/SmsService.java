package io.mngt.services;

import java.io.IOException;

import com.nexmo.client.NexmoClientException;
import com.nexmo.client.sms.SmsSubmissionResponse;
import com.nexmo.client.sms.messages.TextMessage;

public interface SmsService {
  SmsSubmissionResponse sendSMS(TextMessage message) throws IOException, NexmoClientException;
  SmsSubmissionResponse sendValidationCode(String cellphone, String validationCode) throws IOException, NexmoClientException;

  SmsSubmissionResponse sendSMS_Test() throws IOException, NexmoClientException;
  
}