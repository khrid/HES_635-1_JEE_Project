package ch.hevs.managedbeans;

import ch.hevs.businessobject.League;
import ch.hevs.businessobject.Team;
import ch.hevs.footballservice.Football;

import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.List;

public class TeamBean {
    private List<Team> teams;
    private List<String> teamNames = new ArrayList<>();
    private Team teamToManage;
    private League targetLeague;
    private Football football;

    @PostConstruct
    public void initialize() throws NamingException {
        System.out.println("TeamBean - initialize");
        // use JNDI to inject reference to bank EJBB
        InitialContext ctx = new InitialContext();
        football = (Football) ctx.lookup("java:global/HES_635-1_JEE_Project-1.0-SNAPSHOT/FootballBean!ch.hevs.footballservice.Football");

        teams = football.getTeams();
        for (Team t :
                teams) {
            teamNames.add(t.getName());
        }
    }

    public List<Team> getTeams() {
        System.out.println("TeamBean - getTeams");
        return teams;
    }

    public Team getTeamToManage() {
        return teamToManage;
    }

    public List<String> getTeamNames() {
        return teamNames;
    }

    public void updateTeamToManage(ValueChangeEvent event) {
        teamToManage = (Team) event.getNewValue();
        System.out.println(teamToManage);
    }
}
