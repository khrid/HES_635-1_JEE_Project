package ch.hevs.footballservice;

import ch.hevs.businessobject.*;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class FootballBean implements Football {

    private List<League> leagues;

    @PersistenceContext(name = "footballPU")
    private EntityManager em;

    @Override
    public List<League> getLeagues() {
        Query query = em.createQuery("FROM League l order by name");

        System.out.println("FootballBean - getLeagues");

        return (ArrayList<League>) query.getResultList();
    }

    @Override
    public List<Team> getTeams() {
        Query query = em.createQuery("FROM Team t order by name");

        System.out.println("FootballBean - getTeams");

        return (ArrayList<Team>) query.getResultList();
    }

    @Override
    public List<Player> getPlayers() {
        System.out.println("FootballBean - getPlayers");
        Query query = em.createQuery("FROM Player p order by lastname, firstname");


        return (ArrayList<Player>) query.getResultList();
    }

    @Override
    public List<Trainer> getTrainers() {

        System.out.println("FootballBean - getTrainers");
        Query query = em.createQuery("FROM Trainer t order by lastname, firstname");

        return (ArrayList<Trainer>) query.getResultList();
    }

    @Override
    public List<Transfer> getTransfers() {
        System.out.println("FootballBean - getTransfers");
        Query query = em.createQuery("FROM Transfer t order by date desc");

        return (ArrayList<Transfer>) query.getResultList();
    }

    @Override
    public List<Team> getLeagueTeams(String targetLeague) {
        System.out.println("FootballBean - getLeagueTeams for " + targetLeague);
        Query query = em.createQuery("SELECT teams FROM League l WHERE l.name = '" + targetLeague + "'");
        return (List<Team>) query.getResultList();
    }

    @Override
    public Team getTeamByName(String targetTeamName) {
        System.out.println("FootballBean - getTeamByName for " + targetTeamName);
        Query query = em.createQuery("FROM Team t WHERE t.name = '" + targetTeamName + "'");
        return (Team) query.getSingleResult();
    }

    @Override
    public void promoteTeam(Team team) {
        System.out.println("FootballBean - promoteTeam");
        String country = team.getCurrentLeague().getCountry().getName();
        int division = team.getCurrentLeague().getDivision();

        Query query = em.createQuery("FROM League l WHERE l.country.name = '" + country + "' and l.division = " + (division - 1) + "");
        League newLeague = (League) query.getSingleResult();
        newLeague.addTeam(team);
        em.persist(newLeague);
        em.merge(team);
    }

    @Override
    public void relegateTeam(Team team) {
        System.out.println("FootballBean - relegateTeam");
        String country = team.getCurrentLeague().getCountry().getName();
        int division = team.getCurrentLeague().getDivision();

        Query query = em.createQuery("FROM League l WHERE l.country.name = '" + country + "' and l.division = " + (division + 1) + "");

        try {
            League newLeague = (League) query.getSingleResult();
            newLeague.addTeam(team);
            System.out.println(team);
            System.out.println(newLeague);
            em.persist(newLeague);
            em.merge(team);
        } catch (NoResultException nre) {
            System.out.println("FootballBean - relegateTeam - No league under current league.");
        }
    }

    @Override
    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public void transferPlayer(Player player, Team newTeam) {
        Transfer transfer = new Transfer(LocalDateTime.now(), player, player.getCurrentTeam(), newTeam);
        em.persist(transfer);
        player.setCurrentTeam(newTeam);
        em.merge(player);
    }

    @Override
    public void updateNumber(Player player, int newNumber) {
        player.setNumber(newNumber);
        em.merge(player);
    }

    @Override
    public int[] getLeagueStatistics(String targetLeague) {
        int[] stats = {0, 0, 0, 0, 0};
        System.out.println("FootballBean - getLeagueTeams for " + targetLeague);
        Query query = em.createQuery("SELECT team.contingent FROM League l, in(l.teams) team  WHERE l.name = '" + targetLeague + "'");
        //Query query = em.createQuery("SELECT t.contingent FROM Team t WHERE t.currentLeague.name = '"+targetLeague+"'");
        ArrayList<Player> playersInLeague = (ArrayList<Player>) query.getResultList();
        if (playersInLeague.size() > 0) {
            int totalPlayersInLeague = playersInLeague.size();
            int[] totaux = {totalPlayersInLeague, 0, 0, 0};
            for (Player p :
                    playersInLeague) {
                totaux[1] += p.getAge();
                totaux[2] += p.getHeight();
                totaux[3] += p.getWeight();
            }

            stats[0] = totaux[0];
            stats[1] = totaux[1] / totalPlayersInLeague;
            stats[2] = totaux[2] / totalPlayersInLeague;
            stats[3] = totaux[3] / totalPlayersInLeague;
            stats[4] = 1;

            System.out.println(totalPlayersInLeague + " players in " + targetLeague);
            System.out.println("Average age is " + stats[0] + "yo");
            System.out.println("Average height is " + stats[1] + "cm");
            System.out.println("Average weight is " + stats[2] + "kg");
        }
        return stats;
    }

    @Override
    public void createNewPlayer(Player player) {
        //em.merge(player.getCurrentTeam());
        em.persist(player);

    }

    @Override
    public void updatePlayerInfo(Player player){
        em.merge(player);
    }

}
