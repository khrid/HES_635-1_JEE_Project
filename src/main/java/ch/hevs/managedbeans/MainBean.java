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

public class MainBean {
    private Football football;

    // LeagueBean
    private String targetLeague;
    private List<String> leagueNames;
    private List<League> leagues;
    private Team targetTeam;
    private String targetTeamName;
    private List<Team> targetLeagueTeams;
    private List<String> targetLeagueTeamsNames;
    private int[] stats;

    // PlayerBean
    private List<Player> players;
    private List<String> playerNames;
    private String targetPlayer;
    private Player targetPlayerObject;
    private Player newPlayer;
    private Player playerToUpdate;
    private int number;

    // TeamBean
    private List<Team> teams;
    private List<String> teamNames = new ArrayList<>();
    private Team teamToManage;

    // TrainerBean
    private List<Trainer> trainers;

    // TransferBean
    private List<Transfer> transfers;
    private List<Player> players2; // TODO à fusionner avec autre variable
    private List<String> playerNames2; // TODO à fusionner avec autre variable
    private String targetPlayer2; // TODO à fusionner avec autre variable
    private Player targetPlayerObject2; // TODO à fusionner avec autre variable
    private String targetLeague2; // TODO à fusionner avec autre variable
    private List<String> leagueNames2; // TODO à fusionner avec autre variable
    private List<League> leagues2; // TODO à fusionner avec autre variable
    private Team targetTeam2; // TODO à fusionner avec autre variable
    private String targetTeamName2; // TODO à fusionner avec autre variable
    private List<Team> targetLeagueTeams2; // TODO à fusionner avec autre variable
    private List<String> targetLeagueTeamsNames2; // TODO à fusionner avec autre variable
    private String message;

    @PostConstruct
    public void initialize() throws NamingException {
        System.out.println("MainBean - initialize");
        // use JNDI to inject reference to bank EJBB
        InitialContext ctx = new InitialContext();
        football = (Football) ctx.lookup("java:global/HES_635-1_JEE_Project-1.0-SNAPSHOT/FootballBean!ch.hevs.footballservice.Football");

        // LeagueBean
        leagueNames = new ArrayList<>();
        targetLeagueTeams = new ArrayList<>();

        leagues = football.getLeagues();

        for (League l :
                leagues) {
            leagueNames.add(l.getCountry().getCode() + " - " + l.getName());
        }
        Collections.sort(leagueNames);


        // PlayerBean
        refreshPlayersList();
        newPlayer = new Player();


        // TeamBean
        teams = football.getTeams();
        for (Team t :
                teams) {
            teamNames.add(t.getName());
        }


        // TrainerBean
        trainers = football.getTrainers();


        // TransferBean
        transfers = football.getTransfers();

        players2 = football.getPlayers();
        this.playerNames2 = new ArrayList<>();
        for (Player p : players2) {
            this.playerNames2.add(p.getFirstname() + " " + p.getLastname());
        }
        targetPlayerObject2 = players2.get(0);

        leagueNames2 = new ArrayList<>();
        targetLeagueTeams2 = new ArrayList<>();
        leagues2 = football.getLeagues();
        for (League l :
                leagues2) {
            leagueNames2.add(l.getCountry().getCode() + " - " + l.getName());
        }
        Collections.sort(leagueNames2);
        message = "";
    }

    // LeagueBean

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
        System.out.println("getLeagues");
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
        System.out.println("updateTargetLeague");
        targetLeague = ((String) event.getNewValue()).split(" - ")[1];
        leagues = football.getLeagues();
        targetLeagueTeams = football.getLeagueTeams(this.targetLeague);
        this.targetLeagueTeamsNames = new ArrayList<>();
        for (Team t : targetLeagueTeams) {
            this.targetLeagueTeamsNames.add(t.getName());
        }
    }

    public String manageTeam() {
        System.out.println("manageTeam");
        targetTeam = football.getTeamByName(targetTeamName);
        return "showTeamToManage";
    }

    public String promoteTeam() {
        if (targetTeam.getCurrentLeague().getDivision() > 1) {
            football.promoteTeam(targetTeam);
            leagues = football.getLeagues();
        } else {
            System.out.println("manageTeam - Can't promote when already in 1st division.");
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
        System.out.println("getStatistics - targetLeague=" + targetLeague);
        int[] stats = football.getLeagueStatistics(targetLeague);
        if(stats[4] == 1) {
            setStats(stats);
            return "leagueStats";
        }
        return "selectLeague";
    }


    // PlayerBean

    public List<Player> getPlayers() {
        System.out.println("getPlayers");
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Player getNewPlayer() { return newPlayer; }

    public void setNewPlayer(Player newPlayer) { this.newPlayer = newPlayer; }

    public Player getPlayerToUpdate() { return playerToUpdate; }

    public void setPlayerToUpdate(Player playerToUpdate) { this.playerToUpdate = playerToUpdate; }

    public void updateTargetPlayer(ValueChangeEvent event) {
        System.out.println("updateTargetPlayer");
        targetPlayer = (String) event.getNewValue();
        System.out.println(targetPlayer);
        for (Player p :
                players) {
            if((p.getFirstname() + " " + p.getLastname()).equals(targetPlayer)) {
                targetPlayerObject = p;
                number = targetPlayerObject.getNumber();
                System.out.println("updateTargetPlayer - player updated to "+targetPlayerObject.getLastname());
            }
        }
    }

    public void updateNumber() {
        if(targetPlayerObject.getNumber() == number) {
            System.out.println("updateNumber - new and old number are the same");
        }
        football.updateNumber(targetPlayerObject, number);
    }

    public void createNewPlayer() {
        football.createNewPlayer(newPlayer);
    }

    public void updatePlayerInfo(){
        football.updatePlayerInfo(playerToUpdate);
    }

    public void refreshPlayersList(){
        players=football.getPlayers();
        players = football.getPlayers();
        this.playerNames = new ArrayList<>();
        for (Player p : players) {
            this.playerNames.add(p.getFirstname() + " " + p.getLastname());
        }
        targetPlayerObject = players.get(0);

        playerToUpdate = players.get(1); // TODO à changer pour prendre utilisateur connecté par wildfly
    }


    // TeamBean

    public List<Team> getTeams() {
        System.out.println("getTeams");
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


    // TrainerBean
    public List<Trainer> getTrainers() {
        System.out.println("getTrainers");
        return trainers;
    }


    // TransferBean
    public List<Player> getPlayers2() {
        System.out.println("getPlayers");
        return players2;
    }

    public String getTargetPlayer2() {
        return targetPlayer2;
    }

    public void setTargetPlayer2(String targetPlayer2) {
        this.targetPlayer2 = targetPlayer2;
    }

    public Player getTargetPlayerObject2() {
        return targetPlayerObject2;
    }

    public void setTargetPlayerObject2(Player targetPlayerObject2) {
        this.targetPlayerObject2 = targetPlayerObject2;
    }

    public List<String> getPlayerNames2() {
        return playerNames2;
    }

    public List<String> getLeagueNames2() {
        return leagueNames2;
    }
    public List<String> getTargetLeagueTeamsNames2() {
        return targetLeagueTeamsNames2;
    }

    public String getTargetLeague2() {
        return targetLeague2;
    }
    public void setTargetLeague2(final String targetLeague2) {
        this.targetLeague2 = targetLeague2;
    }

    public List<League> getLeagues2() {
        System.out.println("TransferBean - getLeagues");
        return leagues2;
    }

    public Team getTargetTeam2() {
        return targetTeam2;
    }

    public String getTargetTeamName2() {
        return targetTeamName2;
    }
    public void setTargetTeamName2(final String targetTeamName2) {
        this.targetTeamName2 = targetTeamName2;
    }

    public List<Team> getTargetLeagueTeams2() {
        return targetLeagueTeams2;
    }

    public String getMessage(){
        return message;
    }

    public void updateTargetPlayer2(ValueChangeEvent event) {
        System.out.println("TransferBean - updateTargetPlayer");
        targetPlayer2 = (String) event.getNewValue();
        System.out.println(targetPlayer2);
        for (Player p :
                players2) {
            if((p.getFirstname() + " " + p.getLastname()).equals(targetPlayer2)) {
                targetPlayerObject2 = p;
                System.out.println("TransferBean - updateTargetPlayer - player updated to "+ targetPlayerObject2.getLastname());
            }
        }
    }

    public void updateTargetLeague2(ValueChangeEvent event) {
        System.out.println("TransferBean - updateTargetLeague");
        targetLeague2 = ((String) event.getNewValue()).split(" - ")[1];
        leagues2 = football.getLeagues();
        targetLeagueTeams2 = football.getLeagueTeams(this.targetLeague2);
        this.targetLeagueTeamsNames2 = new ArrayList<>();
        for (Team t : targetLeagueTeams2) {
            this.targetLeagueTeamsNames2.add(t.getName());
        }
    }

    public void updateTargetTeam2(ValueChangeEvent event) {
        System.out.println("TransferBean - updateTargetTeam");
        targetTeamName2 = (String) event.getNewValue();
        for (Team t :
                targetLeagueTeams2) {
            if(t.getName().equals(targetTeamName2)) {
                targetTeam2 = t;
                System.out.println("TransferBean - updateTargetTeam - team updated to "+ targetTeam2.getName());
            }
        }
    }

    public List<Transfer> getTransfers() {
        System.out.println("TransferBean - getTransfers");
        return transfers;
    }

    public String transferPlayer(){
        if(targetPlayerObject2.getCurrentTeam().getId() == targetTeam2.getId()) {
            System.out.println("TransferBean - transferPlayer - new and old team are the same");
            message = "Oups, " + targetPlayer2 + " already plays for " + targetTeamName2;
            return "";
        }else {
            football.transferPlayer(targetPlayerObject2, targetTeam2);
            transfers = football.getTransfers();
            message = targetPlayer2 + " successfully transfered to " + targetTeamName2;
            return "transferPlayerSuccess.xhtml";
        }
    }

    public void reset(){
        message = "";
    }

    public String makeNewTransfer(){
        reset();
        return "transferPlayer.xhtml";
    }


}
