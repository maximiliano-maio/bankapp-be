package io.mngt.entity;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Credential {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String username;
  private String password;
  private String mail;
  private String role;
  private Integer validity;
  private int hashcode;
  private int status;

  @JsonBackReference(value = "client_credential")
  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "client_id")
  private Client client;

  public Credential(String username, String password, String mail, String role, Integer validity) {
    this.username = username;
    this.password = password;
    this.mail = mail;
    this.role = role;
    this.validity = validity;
    this.status = 0;
    
  }

  public Credential() { }

  @JsonIgnore
  public Client getClient() {
    return client;
  }


}