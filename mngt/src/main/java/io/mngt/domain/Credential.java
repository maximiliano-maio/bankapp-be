package io.mngt.domain;

import java.math.BigInteger;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Credential {

  @Id
  private BigInteger id;
  private String username;
  private String password;
  private String mail;
  private String role;
  private Integer validity;

  public Credential(String username, String password, String mail, String role, Integer validity) {
    this.username = username;
    this.password = password;
    this.mail = mail;
    this.role = role;
    this.validity = validity;
    
  }

  public Credential() { }

}