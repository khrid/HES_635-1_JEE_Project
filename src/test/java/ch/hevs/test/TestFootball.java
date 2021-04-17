package ch.hevs.test;

import ch.hevs.businessobject.*;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

            Team fcSaintGall = new Team("FC Saint-Gall", "Kybunpark", 1879, superLeagueFromEm);

            // Création d'une nouvelle Team sans League
            Team fcPorrentruy = new Team("FC Porrentruy", "Stade du Tirage", 1904);
            // Ajout de la Team depuis la League
            superLeagueFromEm.addTeam(fcPorrentruy);

            // Persistence des Teams créées
            em.persist(fcBure);
            em.persist(fcPorrentruy);
            em.persist(fcSaintGall);

            // Test JQL, récupération d'une Team
            Team fcBureFromEm = (Team) em.createQuery("from Team where name = 'FC Bure'").getSingleResult();

            // Création d'un nouveau Player
            Player crittinDavid = new Player("Crittin", "David", LocalDate.of(1992, 1, 8), fcBureFromEm, switzerland, "Avant-centre", 9, 180, 85);

            // Création d'un nouveau Player
            Player meyerSylvain = new Player("Meyer", "Sylvain", LocalDate.of(1994, 06, 22), fcBureFromEm, switzerland, "Défenseur", 5, 187, 75);

            List<Player> playersStGall = new ArrayList<>();
            playersStGall.add(new Player("Guillemenot", "Jérémy", LocalDate.of(1998, 1, 6), fcSaintGall, switzerland, "Attaquant", 43, 180, 70));
            playersStGall.add(new Player("Rapp", "Simone", LocalDate.of(1992, 10,1), fcSaintGall, switzerland, "Attaquant", 27, 180, 70));
            playersStGall.add(new Player("Lopar", "Daniel", LocalDate.of(1985, 4, 19), fcSaintGall, switzerland, "Gardien", 1, 180, 70));
            playersStGall.add(new Player("Rüfli", "Vincent", LocalDate.of(1988, 1, 22), fcSaintGall, switzerland, "Défenseur", 99, 180, 70));
            playersStGall.add(new Player("Barnetta", "Tranquillo", LocalDate.of(1985, 5, 22), fcSaintGall, switzerland, "Milieu", 10, 180, 70));

            for (Player p :
                    playersStGall) {
                em.persist(p);
            }

            // Persistence des Players créés
            em.persist(crittinDavid);
            em.persist(meyerSylvain);

            // Création d'un nouveau Transfer (les deux opérations seront faites par une méthode transactionnelle)
            Transfer transfer = new Transfer(LocalDateTime.now(), crittinDavid, crittinDavid.getCurrentTeam(), fcPorrentruy);
            crittinDavid.setCurrentTeam(fcPorrentruy);

            // Persistence du Transfer créé et du Player transféré
            em.persist(transfer);
            em.persist(crittinDavid);


            // Test JQL, récupération d'une Team
            Team fcPorrentruyFromEm = (Team) em.createQuery("from Team where name = 'FC Porrentruy'").getSingleResult();

            // Création d'un nouveau Trainer
            Trainer depeursingeAdrien = new Trainer("Depeursinge", "Adrien", LocalDate.of(1980, 1, 1), fcPorrentruyFromEm, switzerland, "FIFA Pro", "Principal");
            Trainer constantinChristian = new Trainer("Constantin", "Christian", LocalDate.of(1957, 7, 1), fcBure, switzerland, "FIFA Pro", "Principal");
            Trainer zeidlerPeter = new Trainer("Zeidler", "Peter", LocalDate.of(1962, 8, 8), fcSaintGall, germany, "FIFA Pro", "Principal");

            // Persistence du Trainer créé
            em.persist(depeursingeAdrien);
            em.persist(constantinChristian);
            em.persist(zeidlerPeter);


            // Commit de la transaction
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
