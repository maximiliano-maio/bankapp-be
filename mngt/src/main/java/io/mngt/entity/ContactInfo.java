package io.mngt.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.springframework.data.annotation.Persistent;


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

	public Client getClientId() {
		return clientId;
	}

	public void setClientId(Client clientId) {
		this.clientId = clientId;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getDistributionAgreement() {
		return distributionAgreement;
	}

	public void setDistributionAgreement(int distributionAgreement) {
		this.distributionAgreement = distributionAgreement;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getEntrance() {
		return entrance;
	}

	public void setEntrance(String entrance) {
		this.entrance = entrance;
	}

	public String getApartment() {
		return apartment;
	}

	public void setApartment(String apartment) {
		this.apartment = apartment;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPostalBox() {
		return postalBox;
	}

	public void setPostalBox(String postalBox) {
		this.postalBox = postalBox;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
		
	
}
