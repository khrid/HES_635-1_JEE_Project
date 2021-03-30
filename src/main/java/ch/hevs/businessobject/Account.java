package ch.hevs.businessobject;

import javax.persistence.Entity;
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Column;


@Entity
@Table(name="Compte")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(name="numero")
	private String number;
	@Column(name="solde")
	private long saldo;	
	private String description;
	
	// relations
	@ManyToOne
	@JoinColumn(name = "FK_CLIENT")
	private Client owner;

	// id 
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	// number
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	// saldo	
	public long getSaldo() {
		return saldo;
	}
	public void setSaldo(long saldo) {
		this.saldo = saldo;
	}
	
	// description
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	// owner (from Client)
	public Client getOwner() {
		return owner;
	}
	public void setOwner(Client owner) {
		this.owner = owner;
	}
	
	// methods
	public void debit(int amount) {
		long newAmount = getSaldo() - amount;
		setSaldo(newAmount);
	}
	
	public void credit(int amount) {
		setSaldo(getSaldo() + amount);
	}

	// constructors
	public Account() {
	}
	public Account(String number, long saldo, Client owner,
			String description) {
		this.number = number;
		this.saldo = saldo;
		this.owner = owner;
		this.description = description;
	}
	
	@PostPersist
	public void acknowledgePersist() {
		System.out.println("account persisted!!!");
	}
}