package ch.hevs.test;

import ch.hevs.businessobject.Country;
import ch.hevs.businessobject.League;
import ch.hevs.businessobject.Team;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class TestFootball {
    @Test
    public void populate() {
        System.out.println("Populating the database");

        EntityTransaction tx = null;
        try {
            // Joyeusetés techniques pour la connexion à la base
            EntityManagerFactory emf = Persistence
                    .createEntityManagerFactory("footballPU_TU");
            EntityManager em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            // Création d'un tableau de League pour faciliter la persistence plus tard
            List<League> leagues = new ArrayList<>();

            // Création des pays
            Country switzerland = new Country("Switzerland", "CH");
            Country england = new Country("England", "EN");

            // Ajout de nouvelles League
            leagues.add(new League("Super League", 1, switzerland));
            leagues.add(new League("Challenge League", 2, switzerland));
            leagues.add(new League("Premier League", 1, england));
            leagues.add(new League("Championship", 2, england));

            // "Ecriture" des différentes League créées
            for (League l :
                    leagues) {
                em.persist(l);
            }

            // Test JQL, récupération d'une League
            League superLeagueFromEm = (League) em.createQuery("from League where name = 'Super League'").getSingleResult();

            // Création d'une nouvelle Team avec la League
            Team fcBure = new Team("FC Bure", "Stade Croix-de-Pierre", 1962, superLeagueFromEm);

            // Création d'une nouvelle Team sans League
            Team fcPorrentruy = new Team("FC Porrentruy", "Stade du Tirage", 1904);
            // Ajout de la Team depuis la League
            superLeagueFromEm.addTeam(fcPorrentruy);

            // Persistence des Teams créées
            em.persist(fcBure);
            em.persist(fcPorrentruy);

            // Commit de la transaction
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
