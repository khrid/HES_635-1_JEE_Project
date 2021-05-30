package ch.hevs.footballservice;

import ch.hevs.businessobject.*;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
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

    @Resource
    private SessionContext ctx;

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
    public List<Player> getPlayers() {
        System.out.println("FootballBean - getPlayers");
        Query query = em.createQuery("FROM Player p order by lastname, firstname");


        return (ArrayList<Player>) query.getResultList();
    }

    @Override
    public List<Player> getPlayersByTeam(Team team){
        System.out.println("FootballBean - getPlayersByTeam");
        Query query = em.createQuery("SELECT p FROM Team t, IN (t.contingent) p WHERE t.id = " + team.getId() + " ORDER BY p.lastname, p.firstname");


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
        Query query = em.createQuery("SELECT teams FROM League l WHERE l.name = '" + targetLeague + "' order by name");
        return (List<Team>) query.getResultList();
    }

    @Override
    public Team getTeamByName(String targetTeamName) {
        System.out.println("FootballBean - getTeamByName for " + targetTeamName);
        Query query = em.createQuery("FROM Team t WHERE t.name = '" + targetTeamName + "'");
        return (Team) query.getSingleResult();
    }

    @Override
    public boolean promoteTeam(Team team) {
        System.out.println("FootballBean - promoteTeam");
        String country = team.getCurrentLeague().getCountry().getName();
        int division = team.getCurrentLeague().getDivision();

        Query query = em.createQuery("FROM League l WHERE l.country.name = '" + country + "' and l.division = " + (division - 1) + "");

        try {
            League newLeague = (League) query.getSingleResult();
            newLeague.addTeam(team);
            System.out.println(team);
            System.out.println(newLeague);
            em.persist(newLeague);
            em.merge(team);
            return true;
        } catch (NoResultException nre) {
            System.out.println("FootballBean - promoteTeam - No league above current league.");
            return false;
        }
    }

    @Override
    public boolean relegateTeam(Team team) {
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
            return true;
        } catch (NoResultException nre) {
            System.out.println("FootballBean - relegateTeam - No league under current league.");
            return false;
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
    public List<Player> getLeagueStatistics(String targetLeague) {
        System.out.println("FootballBean - getLeagueTeams for " + targetLeague);
        Query query = em.createQuery("SELECT team.contingent FROM League l, in(l.teams) team  WHERE l.name = '" + targetLeague + "'");
        return (ArrayList<Player>) query.getResultList();
    }

    @Override
    public void createNewPlayer(Player player, Team team) {
        Query query = em.createQuery("FROM Team t WHERE t.id =" + team.getId());
        Team t = (Team) query.getSingleResult();
        t.addPlayer(player);
        em.persist(player);
        em.merge(t);
    }

    @Override
    public void updatePlayerInfo(Player player){
        em.merge(player);
    }

}
