package io.mngt.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.Persistent;

import lombok.Data;

@Data
@Entity
public class ContactInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Persistent
	@JsonBackReference
	@OneToOne(
		mappedBy="contactInfo", 
		fetch = FetchType.LAZY,
		optional = false)
	private Client clientId;
	
	private String telephone;
	private String cellphone;
	private String email;
	private int distributionAgreement;
	private String street;
	private String building;
	private String entrance;
	private String apartment;
	private String postalCode;
	private String postalBox;
	private String city;
		
	
}
