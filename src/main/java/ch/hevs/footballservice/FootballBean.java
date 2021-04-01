package ch.hevs.footballservice;

import ch.hevs.businessobject.League;
import ch.hevs.businessobject.Team;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class FootballBean implements Football {

    private List<League> leagues;

    @PersistenceContext(name = "footballPU")
    private EntityManager em;

    public List<League> getLeagues() {
        Query query = em.createQuery("FROM League l");

        System.out.println("FootballBean - getLeagues");

        return (ArrayList<League>) query.getResultList();
    }

    public List<Team> getTeams() {
        Query query = em.createQuery("FROM Team t");

        System.out.println("FootballBean - getTeams");

        return (ArrayList<Team>) query.getResultList();
    }
}
