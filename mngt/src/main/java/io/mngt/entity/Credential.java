package io.mngt.entity;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Credential {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  
  @Column(unique = true)
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

  public Credential() { }

  public Credential(Client client, String username, String password, String mail) {
    this.client = client;
    this.username = username;
    this.password = password;
    this.mail = mail;
    this.role = "user";
    this.validity = 90;
    this.status = 1;
  }

  public Credential(String username, String password, String mail, String role, int status) {
    this.username = username;
    this.password = password;
    this.mail = mail;
    this.role = role;
    this.status = status;
  }

  @JsonIgnore
  public Client getClient() {
    return client;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public Integer getValidity() {
    return validity;
  }

  public void setValidity(Integer validity) {
    this.validity = validity;
  }

  public int getHashcode() {
    return hashcode;
  }

  public void setHashcode(int hashcode) {
    this.hashcode = hashcode;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  


}