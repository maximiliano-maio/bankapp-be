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

import lombok.Data;

@Data
@Entity
public class Client {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToMany(targetEntity= CheckBook.class, mappedBy="client", cascade=CascadeType.ALL)
	private Set<CheckBook> checkBookSet;
	private int clientId;
	private String firstName;
	private String lastName;
	private int maritalStatus;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "contact_info", referencedColumnName="id")
	private ContactInfo contactInfo;

	
}
