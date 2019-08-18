package io.mngt.business;

import org.springframework.stereotype.Component;

@Component
public class ValidationCodeGenerator {

  public int generateCode(int digits) {
    return (int) (Math.random() * Math.pow(10.0, (double) digits));
  }
  
}