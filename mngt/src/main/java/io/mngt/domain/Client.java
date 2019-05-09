package io.mngt.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
public class Client {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToMany(targetEntity= CheckBook.class, mappedBy="client")
	private Set<CheckBook> checkBookSet;
	private String clientId;
	private String firstName;
	private String lastName;
	private String maritalStatus;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "contact_info", referencedColumnName="id")
	@JsonBackReference
	private ContactInfo contactInfo;

	public Client(String clientId, String firstname, String lastname, String maritalStatus) {
		this.clientId = clientId;
		this.firstName = firstname;
		this.lastName = lastname;
		this.maritalStatus = maritalStatus;
	}

	public Client() {}

	
}
