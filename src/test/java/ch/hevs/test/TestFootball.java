package ch.hevs.test;

import ch.hevs.businessobject.Country;
import ch.hevs.businessobject.League;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class TestFootball {

    @Test
    public void updateTest() {
        EntityTransaction tx = null;
        try {
            EntityManagerFactory emf = Persistence
                    .createEntityManagerFactory("footballPU_TU");
            EntityManager em = emf.createEntityManager();

            League l = em.find(League.class, 1L);
            em.getTransaction().begin();
            l.setName("Super league");
            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

            List<League> leagues = new ArrayList<>();
            Country switzerland = new Country("Switzerland");
            Country england = new Country("England");

            leagues.add(new League("Super League", 1, switzerland));
            leagues.add(new League("Challenge League", 2, switzerland));
            leagues.add(new League("Premier League", 1, england));
            leagues.add(new League("Championship", 2, england));

            for (League l :
                    leagues) {
                em.persist(l);
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
