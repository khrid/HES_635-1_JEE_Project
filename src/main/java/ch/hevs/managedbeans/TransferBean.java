package ch.hevs.managedbeans;

import ch.hevs.businessobject.*;
import ch.hevs.footballservice.Football;

import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransferBean {
    private List<Transfer> transfers;

    private List<Player> players;
    private List<String> playerNames;
    private String targetPlayer;
    private Player targetPlayerObject;

    private String targetLeague;
    private List<String> leagueNames;
    private List<League> leagues;
    private Team targetTeam;
    private String targetTeamName;
    private List<Team> targetLeagueTeams;
    private List<String> targetLeagueTeamsNames;


    private Football football;

    @PostConstruct
    public void initialize() throws NamingException {
        System.out.println("TransferBean - initialize");
        // use JNDI to inject reference to bank EJBB
        InitialContext ctx = new InitialContext();
        football = (Football) ctx.lookup("java:global/HES_635-1_JEE_Project-1.0-SNAPSHOT/FootballBean!ch.hevs.footballservice.Football");

        transfers = football.getTransfers();

        players = football.getPlayers();
        this.playerNames = new ArrayList<>();
        for (Player p : players) {
            this.playerNames.add(p.getFirstname() + " " + p.getLastname());
        }
        targetPlayerObject = players.get(0);

        leagueNames = new ArrayList<>();
        targetLeagueTeams = new ArrayList<>();
        leagues = football.getLeagues();
        for (League l :
                leagues) {
            leagueNames.add(l.getCountry().getCode() + " - " + l.getName());
        }
        Collections.sort(leagueNames);
    }

    public List<Player> getPlayers() {
        System.out.println("PlayerBean - getPlayers");
        return players;
    }

    public String getTargetPlayer() {
        return targetPlayer;
    }

    public void setTargetPlayer(String targetPlayer) {
        this.targetPlayer = targetPlayer;
    }

    public Player getTargetPlayerObject() {
        return targetPlayerObject;
    }

    public void setTargetPlayerObject(Player targetPlayerObject) {
        this.targetPlayerObject = targetPlayerObject;
    }

    public List<String> getPlayerNames() {
        return playerNames;
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
    public void setTargetLeague (final String targetLeague) {
        this.targetLeague=targetLeague;
    }

    public List<League> getLeagues() {
        System.out.println("TransferBean - getLeagues");
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

    public void updateTargetPlayer(ValueChangeEvent event) {
        System.out.println("TransferBean - updateTargetPlayer");
        targetPlayer = (String) event.getNewValue();
        System.out.println(targetPlayer);
        for (Player p :
                players) {
            if((p.getFirstname() + " " + p.getLastname()).equals(targetPlayer)) {
                targetPlayerObject = p;
                System.out.println("TransferBean - updateTargetPlayer - player updated to "+targetPlayerObject.getLastname());
            }
        }
    }

    public void updateTargetLeague(ValueChangeEvent event) {
        System.out.println("TransferBean - updateTargetLeague");
        targetLeague = ((String) event.getNewValue()).split(" - ")[1];
        leagues = football.getLeagues();
        targetLeagueTeams = football.getLeagueTeams(this.targetLeague);
        this.targetLeagueTeamsNames = new ArrayList<>();
        for (Team t : targetLeagueTeams) {
            this.targetLeagueTeamsNames.add(t.getName());
        }
    }

    public void updateTargetTeam(ValueChangeEvent event) {
        System.out.println("TransferBean - updateTargetTeam");
        targetTeamName = (String) event.getNewValue();
        for (Team t :
                targetLeagueTeams) {
            if(t.getName().equals(targetTeamName)) {
                targetTeam = t;
                System.out.println("TransferBean - updateTargetTeam - team updated to "+targetTeam.getName());
            }
        }
    }

    public List<Transfer> getTransfers() {
        System.out.println("TransferBean - getTransfers");
        return transfers;
    }

    public void transferPlayer(){
        if(targetPlayerObject.getCurrentTeam().getId() == targetTeam.getId()) {
            System.out.println("TransferBean - transferPlayer - new and old team are the same");
        }else {
            football.transferPlayer(targetPlayerObject, targetTeam);
            transfers = football.getTransfers();
        }
    }

}
