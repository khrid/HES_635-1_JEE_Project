package ch.hevs.test;

import ch.hevs.businessobject.Country;
import ch.hevs.businessobject.League;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TestFootball {

    @Test
    public void test() {
        System.out.println("Test");

        EntityTransaction tx = null;
        try {


            EntityManagerFactory emf = Persistence
                    .createEntityManagerFactory("footballPU_TU");
            EntityManager em = emf.createEntityManager();

            tx = em.getTransaction();
            tx.begin();

            Country switzerland = new Country("Switzerland");
            League league = new League("LNA", 1, switzerland);
            League leagueB = new League("LNB", 2, switzerland);

            em.persist(league);
            em.persist(leagueB);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
