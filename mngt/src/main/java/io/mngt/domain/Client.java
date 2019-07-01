package io.mngt.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
public class Client implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String clientId;
	private String firstName;
	private String lastName;
	private String maritalStatus;

	@JsonManagedReference(value = "4")
	@OneToMany( 
		mappedBy = "client", 
		fetch = FetchType.LAZY)
	private List<BalanceILS> balance = new ArrayList<>();
	
	@JsonManagedReference
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private ContactInfo contactInfo;

	@JsonManagedReference(value = "2")
	@OneToMany(
		mappedBy = "client", 
		fetch = FetchType.LAZY)
	private List<Loan> loan = new ArrayList<>();

	@JsonManagedReference(value = "1")
	@OneToOne(
		mappedBy = "client",
		fetch = FetchType.LAZY, 
		cascade = CascadeType.ALL,
		optional = false)
	private Credential credential;

	@JsonBackReference(value = "3")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
	private Set<CheckBookOrder> checkBookOrder = new HashSet<>();

	

	public Client(String clientId, String firstname, String lastname, String maritalStatus) {
		this.clientId = clientId;
		this.firstName = firstname;
		this.lastName = lastname;
		this.maritalStatus = maritalStatus;
	}

	public Client() {}

	

	
}
