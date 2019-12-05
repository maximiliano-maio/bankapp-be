package io.mngt.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

@Entity
public class Client implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String clientId;
	private String firstName;
	private String lastName;
	private String maritalStatus;
	private int validationCode;

	@JsonManagedReference(value = "client_balanceils")
	@OneToMany( 
		mappedBy = "client", 
		fetch = FetchType.LAZY)
	private List<BalanceILS> balance = new ArrayList<>();
	
	@JsonManagedReference(value = "client_bankaccount")
	@OneToMany(
		mappedBy = "client",
		fetch = FetchType.LAZY)
	private Set<BankAccount> bankAccount = new HashSet<>();

	@JsonManagedReference
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private ContactInfo contactInfo;

	@JsonManagedReference(value = "client_loan")
	@OneToMany(
		mappedBy = "client", 
		fetch = FetchType.LAZY)
	private List<Loan> loan = new ArrayList<>();

	@JsonManagedReference(value = "client_credential")
	@OneToOne(
		mappedBy = "client",
		fetch = FetchType.LAZY, 
		cascade = CascadeType.ALL,
		optional = false)
	private Credential credential;

	@JsonBackReference(value = "client_checkbookorder")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
	private Set<CheckBookOrder> checkBookOrder = new HashSet<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
	private List<ClientOperationsLog> log;

	public Client() {
		super();
	}

	public Client(String clientId, String firstname, String lastname, String maritalStatus) {
		this.clientId = clientId;
		this.firstName = firstname;
		this.lastName = lastname;
		this.maritalStatus = maritalStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public int getValidationCode() {
		return validationCode;
	}

	public void setValidationCode(int validationCode) {
		this.validationCode = validationCode;
	}

	public List<BalanceILS> getBalance() {
		return balance;
	}

	public void setBalance(List<BalanceILS> balance) {
		this.balance = balance;
	}

	public Set<BankAccount> getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(Set<BankAccount> bankAccount) {
		this.bankAccount = bankAccount;
	}

	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	public List<Loan> getLoan() {
		return loan;
	}

	public void setLoan(List<Loan> loan) {
		this.loan = loan;
	}

	public Credential getCredential() {
		return credential;
	}

	public void setCredential(Credential credential) {
		this.credential = credential;
	}

	public Set<CheckBookOrder> getCheckBookOrder() {
		return checkBookOrder;
	}

	public void setCheckBookOrder(Set<CheckBookOrder> checkBookOrder) {
		this.checkBookOrder = checkBookOrder;
	}

	public List<ClientOperationsLog> getLog() {
		return log;
	}

	public void setLog(List<ClientOperationsLog> log) {
		this.log = log;
	}

	

	
}
