package ch.hevs.test;

import ch.hevs.businessobject.*;
import net.bytebuddy.implementation.bind.annotation.Super;
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
            League SuperLeague = (League) em.createQuery("from League where name = 'Super League'").getSingleResult();
            League ChallengeLeague = (League) em.createQuery("from League where name = 'Challenge League'").getSingleResult();
            League PremierLeague = (League) em.createQuery("from League where name = 'Premier League'").getSingleResult();
            League Championship = (League) em.createQuery("from League where name = 'Championship'").getSingleResult();
            League Bundesliga = (League) em.createQuery("from League where name = 'Bundesliga'").getSingleResult();
            League Bundesliga2 = (League) em.createQuery("from League where name = 'Bundesliga 2'").getSingleResult();



            // Création d'une nouvelle Team avec la League
            PremierLeague.addTeam(new Team("Arsenal","Emirates Stadium",1900));
            PremierLeague.addTeam(new Team("Aston Villa","myStadium",1900));
            PremierLeague.addTeam(new Team("Brighton","myStadium",1900));
            PremierLeague.addTeam(new Team("Burnley","myStadium",1900));
            PremierLeague.addTeam(new Team("Chelsea","Stamford Bridge",1900));
            PremierLeague.addTeam(new Team("Crystal Palace","myStadium",1900));
            PremierLeague.addTeam(new Team("Everton","myStadium",1900));
            PremierLeague.addTeam(new Team("Fulham","myStadium",1900));
            PremierLeague.addTeam(new Team("Leeds","myStadium",1900));
            PremierLeague.addTeam(new Team("Leicester","King Power Stadium",1900));
            PremierLeague.addTeam(new Team("Liverpool","Anfield",1900));
            PremierLeague.addTeam(new Team("Manchester City","Etihad Stadium",1900));
            PremierLeague.addTeam(new Team("Manchester United","Old Trafford",1900));
            PremierLeague.addTeam(new Team("Newcastle","myStadium",1900));
            PremierLeague.addTeam(new Team("Sheffield","myStadium",1900));
            PremierLeague.addTeam(new Team("Southampton","myStadium",1900));
            PremierLeague.addTeam(new Team("Tottenham","White Hart Lane",1900));
            PremierLeague.addTeam(new Team("West Bromwich","myStadium",1900));
            PremierLeague.addTeam(new Team("West Ham","myStadium",1900));
            PremierLeague.addTeam(new Team("Wolverhampton","myStadium",1900));
            Championship.addTeam(new Team("Norwich","myStadium",1900));
            Championship.addTeam(new Team("Watford","myStadium",1900));
            Championship.addTeam(new Team("Brentford","myStadium",1900));
            Championship.addTeam(new Team("Swansea","myStadium",1900));
            Championship.addTeam(new Team("Barnsley","myStadium",1900));
            Championship.addTeam(new Team("Bournemouth","myStadium",1900));
            Championship.addTeam(new Team("Reading","myStadium",1900));
            Championship.addTeam(new Team("Cardiff City","myStadium",1900));
            Championship.addTeam(new Team("Queens Park Rangers","myStadium",1900));
            Championship.addTeam(new Team("Middlesbrough","myStadium",1900));
            Championship.addTeam(new Team("Millwall","myStadium",1900));
            Championship.addTeam(new Team("Luton","myStadium",1900));
            Championship.addTeam(new Team("Preston","myStadium",1900));
            Championship.addTeam(new Team("Stoke","myStadium",1900));
            Championship.addTeam(new Team("Blackburn Rovers","myStadium",1900));
            Championship.addTeam(new Team("Coventry","myStadium",1900));
            Championship.addTeam(new Team("Nottingham Forest","myStadium",1900));
            Championship.addTeam(new Team("Birmingham City","myStadium",1900));
            Championship.addTeam(new Team("Bristol City","myStadium",1900));
            Championship.addTeam(new Team("Huddersfield","myStadium",1900));
            Championship.addTeam(new Team("Derby County","myStadium",1900));
            Championship.addTeam(new Team("Wycombe","myStadium",1900));
            Championship.addTeam(new Team("Rotherham","myStadium",1900));
            Championship.addTeam(new Team("Sheffield Wednesday","myStadium",1900));
            Bundesliga.addTeam(new Team("Augsburg","myStadium",1900));
            Bundesliga.addTeam(new Team("Bayern Münich","Allianz Arena",1900));
            Bundesliga.addTeam(new Team("Bielefeld","myStadium",1900));
            Bundesliga.addTeam(new Team("Brême","myStadium",1900));
            Bundesliga.addTeam(new Team("Cologne","myStadium",1900));
            Bundesliga.addTeam(new Team("Dortmund","Signal Iduna Park",1900));
            Bundesliga.addTeam(new Team("Francfort","myStadium",1900));
            Bundesliga.addTeam(new Team("Fribourg","myStadium",1900));
            Bundesliga.addTeam(new Team("Hertha Berlin","myStadium",1900));
            Bundesliga.addTeam(new Team("Hoffenheim","myStadium",1900));
            Bundesliga.addTeam(new Team("Leipzig","myStadium",1900));
            Bundesliga.addTeam(new Team("Leverkusen","myStadium",1900));
            Bundesliga.addTeam(new Team("Mayence","myStadium",1900));
            Bundesliga.addTeam(new Team("Mönchengladbach","myStadium",1900));
            Bundesliga.addTeam(new Team("Schalke 04","myStadium",1900));
            Bundesliga.addTeam(new Team("Stuttgart","myStadium",1900));
            Bundesliga.addTeam(new Team("Union Berlin","myStadium",1900));
            Bundesliga.addTeam(new Team("Wolfsburg","myStadium",1900));
            Bundesliga2.addTeam(new Team("Aue","myStadium",1900));
            Bundesliga2.addTeam(new Team("Bochum","myStadium",1900));
            Bundesliga2.addTeam(new Team("Braunschweig","myStadium",1900));
            Bundesliga2.addTeam(new Team("Darmstadt","myStadium",1900));
            Bundesliga2.addTeam(new Team("Düsseldorf","myStadium",1900));
            Bundesliga2.addTeam(new Team("Fürth","myStadium",1900));
            Bundesliga2.addTeam(new Team("Hamburg","myStadium",1900));
            Bundesliga2.addTeam(new Team("Hanovre","myStadium",1900));
            Bundesliga2.addTeam(new Team("Heidenheim","myStadium",1900));
            Bundesliga2.addTeam(new Team("Karlsruhe","myStadium",1900));
            Bundesliga2.addTeam(new Team("Kiel","myStadium",1900));
            Bundesliga2.addTeam(new Team("Nuremberg","myStadium",1900));
            Bundesliga2.addTeam(new Team("Osnabrück","myStadium",1900));
            Bundesliga2.addTeam(new Team("Paderborn","myStadium",1900));
            Bundesliga2.addTeam(new Team("Regensburg","myStadium",1900));
            Bundesliga2.addTeam(new Team("Sandhausen","myStadium",1900));
            Bundesliga2.addTeam(new Team("St-Pauli","myStadium",1900));
            Bundesliga2.addTeam(new Team("Würzburg","myStadium",1900));
            SuperLeague.addTeam(new Team("Bâle","Stade Saint-Jacques",1900));
            SuperLeague.addTeam(new Team("Lausanne","myStadium",1900));
            SuperLeague.addTeam(new Team("Lucerne","myStadium",1900));
            SuperLeague.addTeam(new Team("Lugano","myStadium",1900));
            SuperLeague.addTeam(new Team("Saint-Gall","myStadium",1900));
            SuperLeague.addTeam(new Team("Servette","La Praille",1900));
            SuperLeague.addTeam(new Team("Sion","Tourbillon",1900));
            SuperLeague.addTeam(new Team("Vaduz","myStadium",1900));
            SuperLeague.addTeam(new Team("Young Boys","Wankdorf",1900));
            SuperLeague.addTeam(new Team("Zurich","myStadium",1900));
            ChallengeLeague.addTeam(new Team("Grasshopper","myStadium",1900));
            ChallengeLeague.addTeam(new Team("Thoune","myStadium",1900));
            ChallengeLeague.addTeam(new Team("Stade Lausanne","myStadium",1900));
            ChallengeLeague.addTeam(new Team("Schaffhouse","myStadium",1900));
            ChallengeLeague.addTeam(new Team("Aarau","myStadium",1900));
            ChallengeLeague.addTeam(new Team("Winterthour","myStadium",1900));
            ChallengeLeague.addTeam(new Team("Wil","myStadium",1900));
            ChallengeLeague.addTeam(new Team("Kriens","myStadium",1900));
            ChallengeLeague.addTeam(new Team("Xamax","La Maladière",1900));
            ChallengeLeague.addTeam(new Team("Chiasso","myStadium",1900));

            // Persistence des Teams et des Trainers créées
            for (Team t :
                    SuperLeague.getTeams()) {
                em.persist(t);
            }
            for (Team t :
                    ChallengeLeague.getTeams()) {
                em.persist(t);
            }
            for (Team t :
                    PremierLeague.getTeams()) {
                em.persist(t);
            }
            for (Team t :
                    Championship.getTeams()) {
                em.persist(t);
            }
            for (Team t :
                    Bundesliga.getTeams()) {
                em.persist(t);
            }
            for (Team t :
                    Bundesliga2.getTeams()) {
                em.persist(t);
            }


            // Test JQL, récupération d'une Team
            Team fcSion = (Team) em.createQuery("from Team where name = 'Sion'").getSingleResult();

            // Création d'un nouveau Player
            Player crittinDavid = new Player("Crittin", "David", LocalDate.of(1992, 1, 8), fcSion, switzerland, "Attaquant", 9, 180, 85);

            // Création d'un nouveau Player
            Player meyerSylvain = new Player("Meyer", "Sylvain", LocalDate.of(1994, 06, 22), fcSion, switzerland, "Défenseur", 5, 187, 75);

            Team fcSaintGall = (Team) em.createQuery("from Team where name = 'Saint-Gall'").getSingleResult();
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

            // Création d'un nouveau Transfer
            Transfer transfer = new Transfer(LocalDateTime.now(), crittinDavid, crittinDavid.getCurrentTeam(), fcSaintGall);
            crittinDavid.setCurrentTeam(fcSaintGall);

            // Persistence du Transfer créé et du Player transféré
            em.persist(transfer);
            em.persist(crittinDavid);

            // Création d'un nouveau Trainer
            Trainer constantinChristian = new Trainer("Constantin", "Christian", LocalDate.of(1957, 7, 1), fcSion, switzerland, "FIFA Pro", "Principal");
            Trainer zeidlerPeter = new Trainer("Zeidler", "Peter", LocalDate.of(1962, 8, 8), fcSaintGall, germany, "FIFA Pro", "Principal");

            // Persistence du Trainer créé
            em.persist(constantinChristian);
            em.persist(zeidlerPeter);

            // Commit de la transaction
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
