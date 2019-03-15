package io.mngt.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class ContactInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(mappedBy="contactInfo")
	private Client client;
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
