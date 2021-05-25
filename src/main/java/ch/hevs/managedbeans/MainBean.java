package ch.hevs.managedbeans;

import ch.hevs.businessobject.*;
import ch.hevs.footballservice.Football;

import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainBean {
    private Football football;
    private List<String> messages;
    private boolean isValid = true;

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
    private int newNumber;

    // TrainerBean
    private List<Trainer> trainers;

    // TransferBean
    private List<Transfer> transfers;

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
        targetLeague = leagueNames.get(0);
        refreshLeagueTeams();


        // PlayerBean
        refreshPlayersList();
        initializeNewPlayer();


        // TrainerBean
        trainers = football.getTrainers();


        // TransferBean
        transfers = football.getTransfers();
        messages = new ArrayList<>();
    }

    private void initializeNewPlayer() {
        newPlayer = new Player();
        newPlayer.setDateOfBirth("2000-01-01");
    }

    private void refreshLeagueTeams() {
        updateLeagueTeams(targetLeague);
    }

    private void reset(){
        messages.clear();
        isValid = true;
    }

    private void checkLastname(Player p){
        if(p.getLastname().length() < 1 || p.getLastname().length() > 40){
            messages.add("Lastname must be between 1 and 40 characters");
            isValid = false;
        }
    }

    private void checkFirstname(Player p){
        if(p.getFirstname().length() < 1 || p.getFirstname().length() > 40){
            messages.add("Firstname must be between 1 and 40 characters");
            isValid = false;
        }
    }

    private void checkPosition(Player p){
        if(p.getPosition().length() < 1 || p.getPosition().length() > 40){
            messages.add("Position must be between 1 and 40 characters");
            isValid = false;
        }
    }

    private void checkTeam(){
        if(targetTeam == null){
            messages.add("No team selected");
            isValid = false;
        }
    }

    private void checkNumber(int number){
        if(number < 1 || number > 99){
            messages.add("Number must be between 1 and 99");
            isValid = false;
        }
    }

    private void checkHeight(Player p){
        if(p.getHeight() < 100 || p.getHeight() > 250){
            messages.add("Height must be between 100 and 250 (in cm)");
            isValid = false;
        }
    }

    private void checkWeight(Player p){
        if(p.getWeight() < 30 || p.getWeight() > 200){
            messages.add("Weight must be between 30 and 200 (in kg)");
            isValid = false;
        }
    }

    private void checkDateOfBirth(Player p){
        if(!p.isDateValid()){
            messages.add("Invalid date format (pattern is yyyy-mm-dd)");
            isValid = false;
        }
    }

    public List<String> getMessages(){
        return messages;
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

    public int getNewNumber() {
        return targetPlayerObject.getNumber();
    }

    public void setNewNumber(int newNumber) {
        this.newNumber = newNumber;
    }

    public void updateTargetLeague(ValueChangeEvent event) {
        System.out.println("updateTargetLeague");
        updateLeagueTeams(((String) event.getNewValue()));
    }

    private void updateLeagueTeams(String leagueName) {
        targetLeague = leagueName.split(" - ")[1];
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
        reset();
        if (targetTeam.getCurrentLeague().getDivision() > 1) {
            football.promoteTeam(targetTeam);
            leagues = football.getLeagues();
            messages.add(targetTeam.getName() + " successfully promoted");
            return "promotionRelegationSuccess.xhtml";
        } else {
            System.out.println("manageTeam - Can't promote when already in 1st division.");
            messages.add("Can't promote team because it is already in highest division");
            return "showTeamToManage.xhtml";
        }
    }

    public String relegateTeam() {
        reset();
        if(football.relegateTeam(targetTeam)){
            leagues = football.getLeagues();
            messages.add(targetTeam.getName() + " successfully relegated");
            return "promotionRelegationSuccess.xhtml";
        }else{
            messages.add("Can't relegate team because it is already in lowest division");
            return "showTeamToManage.xhtml";
        }
    }

    public String promotionRelegationDone(){
        reset();
        return "showTeamToManage.xhtml";
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

    public Player getNewPlayer() {
        return newPlayer;
    }

    public Player getPlayerToUpdate() {
        return playerToUpdate;
    }

    public void updateTargetPlayer(ValueChangeEvent event) {
        System.out.println("updateTargetPlayer");
        targetPlayer = (String) event.getNewValue();
        System.out.println(targetPlayer);
        for (Player p :
                players) {
            if((p.getFirstname() + " " + p.getLastname()).equals(targetPlayer)) {
                targetPlayerObject = p;
                System.out.println("updateTargetPlayer - player updated to "+targetPlayerObject.getLastname());
            }
        }
    }

    public String updateNumber() {
        reset();

        checkNumber(newNumber);

        if(targetPlayerObject.getNumber() == newNumber) {
            System.out.println("updateNumber - new and old number are the same");
            messages.add(targetPlayer + " number is already #" + newNumber);
            isValid = false;
        }

        if(isValid){
            targetPlayerObject.setNumber(newNumber);
            football.updatePlayerInfo(targetPlayerObject);
            messages.add(targetPlayer + " number successfully updated to #" + targetPlayerObject.getNumber());
            return "changePlayerNumberSuccess.xhtml";
        }else{
            return "";
        }
    }

    public String makeNewNumberUpdate(){
        reset();
        return "changePlayerNumber.xhtml";
        //return "index.xhtml";
    }

    public String createNewPlayer() {
        reset();
        // TODO Voir comment gérer le pays
        Country c = new Country("Switzerland", "CH");
        newPlayer.setNationality(c);

        checkLastname(newPlayer);
        checkFirstname(newPlayer);
        checkPosition(newPlayer);
        checkTeam();
        checkNumber(newPlayer.getNumber());
        checkHeight(newPlayer);
        checkWeight(newPlayer);
        checkDateOfBirth(newPlayer);

        if(isValid){
            football.createNewPlayer(newPlayer, targetTeam);
            refreshPlayersList();
            refreshLeagueTeams();
            messages.add(newPlayer.getFirstname() + " " + newPlayer.getLastname() + " successfully registred");
            return "addNewPlayerSuccess.xhtml";
        }else{
            messages.add(0,"Following errors occured :");
            return "";
        }
    }

    public String makeNewPlayerInsertion(){
        reset();
        initializeNewPlayer();
        return "addNewPlayer.xhtml";
    }

    public String updatePlayerInfo(){
        reset();

        checkLastname(playerToUpdate);
        checkFirstname(playerToUpdate);
        checkPosition(playerToUpdate);
        checkDateOfBirth(playerToUpdate);

        if(isValid){
            football.updatePlayerInfo(playerToUpdate);
            messages.add(playerToUpdate.getFirstname() + " " + playerToUpdate.getLastname() + " successfully updated");
            return "updatePlayerInfoSuccess.xhtml";
        }else{
            messages.add(0,"Following errors occured :");
            return "";
        }
    }

    public String makeNewPlayerInfoUpdate(){
        reset();
        return "updatePlayerInfo.xhtml";
    }

    public void refreshPlayersList(){
        players = football.getPlayers();
        this.playerNames = new ArrayList<>();
        for (Player p : players) {
            this.playerNames.add(p.getFirstname() + " " + p.getLastname());
        }
        targetPlayerObject = players.get(0);

        playerToUpdate = players.get(1); // TODO à changer pour prendre utilisateur connecté par wildfly
    }


    // TeamBean

    public void updateTargetTeam(ValueChangeEvent event) {
        System.out.println("updateTargetTeam");
        targetTeamName = (String) event.getNewValue();
        for (Team t :
                targetLeagueTeams) {
            if(t.getName().equals(targetTeamName)) {
                targetTeam = t;
                System.out.println("updateTargetTeam - team updated to "+ targetTeam.getName());
            }
        }
    }


    // TrainerBean

    public List<Trainer> getTrainers() {
        System.out.println("getTrainers");
        return trainers;
    }


    // TransferBean

    public List<Transfer> getTransfers() {
        System.out.println("getTransfers");
        return transfers;
    }

    public String transferPlayer(){
        reset();
        if(targetPlayerObject.getCurrentTeam().getId() == targetTeam.getId()) {
            System.out.println("transferPlayer - new and old team are the same");
            messages.add(targetPlayer + " already plays for " + targetTeamName);
            return "";
        }else {
            football.transferPlayer(targetPlayerObject, targetTeam);
            transfers = football.getTransfers();
            refreshLeagueTeams();
            messages.add(targetPlayer + " successfully transfered to " + targetTeamName);
            return "transferPlayerSuccess.xhtml";
        }
    }

    public String makeNewTransfer(){
        reset();
        return "transferPlayer.xhtml";
    }

    public void returnHome(){
        reset();
    }


}
