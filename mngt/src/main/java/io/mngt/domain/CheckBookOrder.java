package io.mngt.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
public class CheckBookOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int orderNumber;
    private String status;
    
    @ManyToOne
    @JoinColumn(name="client_id", nullable=false)
    @JsonBackReference
    private Client client;
    private String checkType;
    private int firstCheck;
    private int lastCheck;
    private int checkAmount;
    private String currency;

    public CheckBookOrder() { }

    public CheckBookOrder(int orderNum, String status, String checkType, int firstCheck, int lastCheck, int checkAmount, String currency ) {
        this.orderNumber = orderNum;
        this.status = status;
        this.checkType = checkType;
        this.firstCheck = firstCheck;
        this.lastCheck = lastCheck;
        this.checkAmount = checkAmount;
        this.currency = currency;
    }

    
}