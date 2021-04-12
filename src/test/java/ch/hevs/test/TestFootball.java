package ch.hevs.test;

import ch.hevs.businessobject.*;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Date;
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
            Country germany = new Country("Germany", "DE");

            // Ajout de nouvelles League
            leagues.add(new League("Super League", 1, switzerland));
            leagues.add(new League("Challenge League", 2, switzerland));
            leagues.add(new League("Premier League", 1, england));
            leagues.add(new League("Championship", 2, england));
            leagues.add(new League("Bundesliga", 1, germany));
            leagues.add(new League("Bundesliga 2", 2, germany));

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

            // Test JQL, récupération d'une Team
            Team fcBureFromEm = (Team) em.createQuery("from Team where name = 'FC Bure'").getSingleResult();

            // Création d'un nouveau Player
            Player crittinDavid = new Player("Crittin", "David", new Date(1990,01,01),fcBureFromEm,switzerland,"Avant-centre", 9, 180, 85);

            // Création d'un nouveau Player
            Player meyerSylvain = new Player("Meyer", "Sylvain", new Date(1994,06,22),fcBureFromEm,switzerland,"Défenseur", 5, 187, 75);

            // Persistence des Players créés
            em.persist(crittinDavid);
            em.persist(meyerSylvain);

            // Création d'un nouveau Transfer (les deux opérations seront faites par une méthode transactionnelle)
            Transfer transfer = new Transfer(new Date(), crittinDavid, crittinDavid.getCurrentTeam(), fcPorrentruy);
            crittinDavid.setCurrentTeam(fcPorrentruy);

            // Persistence du Transfer créé et du Player transféré
            em.persist(transfer);
            em.persist(crittinDavid);


            // Test JQL, récupération d'une Team
            Team fcPorrentruyFromEm = (Team) em.createQuery("from Team where name = 'FC Porrentruy'").getSingleResult();

            // Création d'un nouveau Trainer
            Trainer depeursingeAdrien = new Trainer("Depeursinge", "Adrien", new Date(1980,01,01), fcPorrentruyFromEm, switzerland, "FIFA Pro", "Principal");

            // Persistence du Trainer créé
            em.persist(depeursingeAdrien);

            // Commit de la transaction
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
