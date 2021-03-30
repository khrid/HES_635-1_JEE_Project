package ch.hevs.businessobject;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Client")
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	@Column(name="nom")
	private String lastname;
	@Column(name="prenom")
	private String firstname;

	// relations
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)//@JoinColumn(name = "FK_CLIENT")
	private List<Account> accounts;
	
	
	// id
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	// firstname
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	// lastname
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	// accounts (From Account)
	public List<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	
	// constructors
	public Client() {
	}
	public Client(String firstname, String lastname) {
		this.lastname = lastname;
		this.firstname = firstname;
	}
	
	@Override
	public String toString() {
		String result = id + "-" + lastname + "-" + firstname;
		return result;
	}
}
