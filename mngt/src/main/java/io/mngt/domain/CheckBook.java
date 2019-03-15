package io.mngt.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class CheckBook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int orderNumber;
    private String status;
    
    @ManyToOne
    @JoinColumn(name="client_id", nullable=false)
    private Client client;
    private String checkType;
    private int firstCheck;
    private int lastCheck;
    private int checkAmount;
    private String currency;

    
}