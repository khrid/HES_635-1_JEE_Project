package ch.hevs.footballservice;

import ch.hevs.businessobject.League;

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

    @Override
    public List<League> getLeagues() {
        Query query = em.createQuery("FROM League l");

        return (ArrayList<League>) query.getResultList();
    }
}
