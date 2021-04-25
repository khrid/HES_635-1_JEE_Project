package ch.hevs.managedbeans;

import ch.hevs.businessobject.League;
import ch.hevs.businessobject.Team;
import ch.hevs.footballservice.Football;


import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeagueBean {
    private String targetLeague;
    private List<String> leagueNames;
    private List<League> leagues;
    private Team targetTeam;
    private String targetTeamName;
    private List<Team> targetLeagueTeams;
    private List<String> targetLeagueTeamsNames;
    private Football football;
    private int[] stats;

    @PostConstruct
    public void initialize() throws NamingException {
        System.out.println("LeagueBean - initialize");
        // use JNDI to inject reference to bank EJBB
        InitialContext ctx = new InitialContext();
        football = (Football) ctx.lookup("java:global/HES_635-1_JEE_Project-1.0-SNAPSHOT/FootballBean!ch.hevs.footballservice.Football");

        leagueNames = new ArrayList<>();
        targetLeagueTeams = new ArrayList<>();

        leagues = football.getLeagues();

        for (League l :
                leagues) {
            leagueNames.add(l.getCountry().getCode() + " - " + l.getName());
        }
        Collections.sort(leagueNames);
    }

    public List<String> getLeagueNames() {
        return leagueNames;
    }

    public List<String> getTargetLeagueTeamsNames() {
        return targetLeagueTeamsNames;
    }

    public String getTargetLeague() {
        return targetLeague;
    }

    public void setTargetLeague(final String targetLeague) {
        this.targetLeague = targetLeague;
    }

    public List<League> getLeagues() {
        System.out.println("LeagueBean - getLeagues");
        return leagues;
    }

    public Team getTargetTeam() {
        return targetTeam;
    }

    public String getTargetTeamName() {
        return targetTeamName;
    }

    public void setTargetTeamName(final String targetTeamName) {
        this.targetTeamName = targetTeamName;
    }

    public List<Team> getTargetLeagueTeams() {
        return targetLeagueTeams;
    }

    public int[] getStats() {
        return stats;
    }

    public void setStats(int[] stats) {
        this.stats = stats;
    }

    public void updateTargetLeague(ValueChangeEvent event) {
        System.out.println("LeagueBean - updateTargetLeague");
        targetLeague = ((String) event.getNewValue()).split(" - ")[1];
        leagues = football.getLeagues();
        targetLeagueTeams = football.getLeagueTeams(this.targetLeague);
        this.targetLeagueTeamsNames = new ArrayList<>();
        for (Team t : targetLeagueTeams) {
            this.targetLeagueTeamsNames.add(t.getName());
        }
    }

    public String manageTeam() {
        System.out.println("LeagueBean - manageTeam");
        targetTeam = football.getTeamByName(targetTeamName);
        return "showTeamToManage";
    }

    public String promoteTeam() {
        if (targetTeam.getCurrentLeague().getDivision() > 1) {
            football.promoteTeam(targetTeam);
            leagues = football.getLeagues();
        } else {
            System.out.println("LeagueBean - manageTeam - Can't promote when already in 1st division.");
        }
        return "showTeamToManage";
    }

    public String relegateTeam() {
        football.relegateTeam(targetTeam);
        leagues = football.getLeagues();
        return "showTeamToManage";
    }

    public String getStatistics() {
        targetLeague = targetLeague.split(" - ")[1];
        System.out.println("LeagueBean - getStatistics - targetLeague=" + targetLeague);
        int[] stats = football.getLeagueStatistics(targetLeague);
        if(stats[4] == 1) {
            setStats(stats);
            return "leagueStats";
        }
        return "selectLeague";
    }
}
