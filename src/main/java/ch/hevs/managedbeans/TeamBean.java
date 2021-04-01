package ch.hevs.managedbeans;

import ch.hevs.businessobject.League;
import ch.hevs.businessobject.Team;
import ch.hevs.footballservice.Football;

import javax.annotation.PostConstruct;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.List;

public class TeamBean {
    private List<Team> teams;
    private Football football;

    @PostConstruct
    public void initialize() throws NamingException {
        System.out.println("LeagueBean - initialize");
        // use JNDI to inject reference to bank EJBB
        InitialContext ctx = new InitialContext();
        football = (Football) ctx.lookup("java:global/HES_635-1_JEE_Project-1.0-SNAPSHOT/FootballBean!ch.hevs.footballservice.Football");

        teams = football.getTeams();
    }

    public List<Team> getTeams() {
        System.out.println("TeamBean - getTeams");
        return teams;
    }
}
