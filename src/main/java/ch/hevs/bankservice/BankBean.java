package ch.hevs.bankservice;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ch.hevs.businessobject.Account;
import ch.hevs.businessobject.Client;

@Stateless
public class BankBean implements Bank {
	
	@PersistenceContext(name = "BankPU")
	private EntityManager em;

	public Account getAccount(String accountDescription, String lastnameOwner) {
		Query query = em.createQuery("FROM Account a WHERE a.description=:description AND a.owner.lastname=:lastname");
		query.setParameter("description", accountDescription);
		query.setParameter("lastname", lastnameOwner);

		return (Account) query.getSingleResult();
	}
	
	public List<Account> getAccountListFromClientLastname(String lastname) {
		return (List<Account>) em.createQuery("SELECT c.accounts FROM Client c where c.lastname=:lastname").setParameter("lastname", lastname).getResultList();
	}

	public void transfer(Account srcAccount, Account destAccount, int amount) {
		
		Account srcAccountFromEm = em.merge(srcAccount);
		Account destAccountFromEm = em.merge(destAccount);
		srcAccountFromEm.debit(amount);
		destAccountFromEm.credit(amount);
	}

	public List<Client> getClients() {
		return em.createQuery("FROM Client").getResultList();
	}
	
	public Client getClient(long clientid) {
		return (Client) em.createQuery("FROM Client c where c.id=:id").setParameter("id", clientid).getSingleResult();
	}
}
