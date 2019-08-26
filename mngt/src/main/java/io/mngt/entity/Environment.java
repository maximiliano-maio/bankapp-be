package io.mngt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Environment {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Column(unique = true, nullable = false)
  private String key;
  @Column(nullable = false)
  private String value;
  private String description;

  public Environment() {}
  
  public Environment(String key, String value, String description) {
    this.key = key;
    this.value = value;
    this.description = description;
  }
  
}