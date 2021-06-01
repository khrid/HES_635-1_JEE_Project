package ch.hevs.managedbeans;

import ch.hevs.businessobject.*;
import ch.hevs.footballservice.Football;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainBean {
    // General
    private Football football;
    private List<String> messages;
    private boolean isValid = true;
    private String foo;
    private String currentURL;
    private boolean clearState = false;
    public static final String NOT_AUTHORIZED_MESSAGE = "You are not authorized to perform this operation";

    // League
    private String targetLeague;
    private List<String> leagueNames;
    private List<League> leagues;
    private Team targetTeam;
    private String targetTeamName;
    private List<Team> targetLeagueTeams;
    private List<String> targetLeagueTeamsNames;
    private int[] stats;

    // Player
    private List<Player> players;
    private List<Player> playersToManage;
    private List<String> playerNames;
    private String targetPlayer;
    private Player targetPlayerObject;
    private Player newPlayer;
    private Player playerToUpdate;
    private int newNumber;
    private List<String> positions;
    private String targetPosition;

    // Trainer
    private List<Trainer> trainers;
    private Trainer connectedTrainer;

    // Transfer
    private List<Transfer> transfers;

    @PostConstruct
    public void initialize() throws NamingException {
        // General
        System.out.println("MainBean - initialize");
        // use JNDI to inject reference to football EJBB
        InitialContext ctx = new InitialContext();
        football = (Football) ctx.lookup("java:global/HES_635-1_JEE_Project-1.0-SNAPSHOT/FootballBean!ch.hevs.footballservice.Football");

        messages = new ArrayList<>();
        currentURL = "";

        // Trainer
        trainers = football.getTrainers();
        getConnectedTrainer();

        // League
        refreshLeagues();
        refreshLeagueTeams();


        // Player
        initializeNewPlayer();
        initializePositions();
        refreshPlayersList();

        // Transfer
        transfers = football.getTransfers();
    }

    /********************
    ******GENERAL********
    ********************/

    private void initializeNewPlayer() {
        newPlayer = new Player();
        newPlayer.setDateOfBirth("2000-01-01");
    }

    private void refreshLeagues() {
        leagueNames = new ArrayList<>();
        targetLeagueTeams = new ArrayList<>();

        leagues = football.getLeagues();

        for (League l :
                leagues) {
            leagueNames.add(l.getCountry().getCode() + " - " + l.getName());
        }
        Collections.sort(leagueNames);
        targetLeague = leagueNames.get(0);
    }

    private void refreshLeagueTeams() {
        if(FacesContext.getCurrentInstance().getExternalContext().isUserInRole("trainer")) {
            targetTeamName = connectedTrainer.getCurrentTeam().getName();
            updateLeagueTeams(" - " + connectedTrainer.getCurrentTeam().getCurrentLeague().getName());
            bindTeamObject(targetTeamName);
        }else{
            updateLeagueTeams(targetLeague);
        }
    }

    private void refreshPlayersList(){
        players = football.getPlayers();
        if(FacesContext.getCurrentInstance().getExternalContext().isUserInRole("trainer")) {
            playersToManage = football.getPlayersByTeam(connectedTrainer.getCurrentTeam());
        }else{
            playersToManage = players;
        }
        this.playerNames = new ArrayList<>();
        for (Player p : playersToManage) {
            this.playerNames.add(p.getFirstname() + " " + p.getLastname());
        }

        targetPlayerObject = playersToManage.get(0);
        targetPlayer = playersToManage.get(0).getLastname() + " " + playersToManage.get(0).getFirstname();

        if(FacesContext.getCurrentInstance().getExternalContext().isUserInRole("player")) {
            String info = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
            String prenom = info.split("\\.")[0];
            String nom = info.split("\\.")[1];
            for (Player p :
                    playersToManage) {
                if(p.getFirstname().equalsIgnoreCase(prenom) && p.getLastname().equalsIgnoreCase(nom)) {
                    playerToUpdate = p;
                    break;
                }
            }
        }else{
            playerToUpdate = playersToManage.get(0);
        }

        targetPosition = playerToUpdate.getPosition();
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

    private void initializePositions(){
        positions = new ArrayList<>();
        positions.add("Défenseur");
        positions.add("Milieu");
        positions.add("Attaquant");
    }

    public List<String> getMessages(){
        return messages;
    }

    public String getFoo(){
        return foo;
    }

    public void setFoo(String foo){
        this.foo = foo;
    }

    public void reset(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if(!currentURL.equals(request.getRequestURL().toString()) || clearState) {
            messages.clear();
            refreshLeagues();
            refreshLeagueTeams();
            refreshPlayersList();
            clearState = false;
        }
        currentURL = request.getRequestURL().toString();
    }

    /********************
     ******LEAGUE********
     ********************/

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
        messages.clear();

        if(FacesContext.getCurrentInstance().getExternalContext().isUserInRole("administrator") ||
                FacesContext.getCurrentInstance().getExternalContext().isUserInRole("leagueManager")){
            if(football.promoteTeam(targetTeam)){
                leagues = football.getLeagues();
                messages.add(targetTeam.getName() + " successfully promoted");
                clearState = true;
                return "promotionRelegationSuccess";
            }else{
                messages.add("Can't promote team because it is already in highest division");
            }
        }else{
            messages.add(NOT_AUTHORIZED_MESSAGE);
        }
        return "";
    }

    public String relegateTeam() {
        messages.clear();

        if(FacesContext.getCurrentInstance().getExternalContext().isUserInRole("administrator") ||
                FacesContext.getCurrentInstance().getExternalContext().isUserInRole("leagueManager")){
            if(football.relegateTeam(targetTeam)){
                leagues = football.getLeagues();
                messages.add(targetTeam.getName() + " successfully relegated");
                clearState = true;
                return "promotionRelegationSuccess";
            }else{
                messages.add("Can't relegate team because it is already in lowest division");
            }
        }else{
            messages.add(NOT_AUTHORIZED_MESSAGE);
        }
        return "";
    }

    public String promotionRelegationDone(){
        messages.clear();
        return "showTeamToManage";
    }

    public String getStatistics() {
        if(FacesContext.getCurrentInstance().getExternalContext().isUserInRole("administrator") ||
                FacesContext.getCurrentInstance().getExternalContext().isUserInRole("leagueManager")){
            targetLeague = targetLeague.split(" - ")[1];
            System.out.println("getStatistics - targetLeague=" + targetLeague);
            ArrayList<Player> playersInLeague = (ArrayList<Player>) football.getLeagueStatistics(targetLeague);

            if (playersInLeague.size() > 0) {
                int[] stats = new int[]{0, 0, 0, 0, 0};
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

                setStats(stats);
                return "leagueStats";
            }
        }else{
            messages.add(NOT_AUTHORIZED_MESSAGE);
        }

        return "selectLeague";
    }


    /********************
     ******PLAYER********
     ********************/

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

    public String getTargetPosition(){
        return targetPosition;
    }

    public void setTargetPosition(String targetPosition){
        this.targetPosition = targetPosition;
    }

    public List<String> getPositions(){
        return positions;
    }

    public void updateTargetPlayer(ValueChangeEvent event) {
        System.out.println("updateTargetPlayer");
        targetPlayer = (String) event.getNewValue();
        System.out.println(targetPlayer);
        for (Player p :
                playersToManage) {
            if((p.getFirstname() + " " + p.getLastname()).equals(targetPlayer)) {
                targetPlayerObject = p;
                playerToUpdate = p;
                System.out.println(playerToUpdate);
                System.out.println("updateTargetPlayer - player updated to "+targetPlayerObject.getLastname());
            }
        }
    }

    public String updateNumber() {
        messages.clear();

        if(FacesContext.getCurrentInstance().getExternalContext().isUserInRole("administrator") ||
                FacesContext.getCurrentInstance().getExternalContext().isUserInRole("trainer")){
            isValid = true;

            checkNumber(newNumber);

            if(targetPlayerObject.getNumber() == newNumber) {
                System.out.println("updateNumber - new and old number are the same");
                messages.add(targetPlayer + " number is already #" + newNumber);
                isValid = false;
            }

            if(isValid) {
                targetPlayerObject.setNumber(newNumber);
                football.updatePlayerInfo(targetPlayerObject);
                messages.add(targetPlayer + " number successfully updated to #" + targetPlayerObject.getNumber());
                clearState = true;
                return "changePlayerNumberSuccess";
            }
        }else{
                messages.add(NOT_AUTHORIZED_MESSAGE);
            }

        return "";
    }

    public String makeNewNumberUpdate(){
        messages.clear();
        return "changePlayerNumber";
    }

    public String createNewPlayer() {
        messages.clear();

        if(FacesContext.getCurrentInstance().getExternalContext().isUserInRole("administrator") ||
                FacesContext.getCurrentInstance().getExternalContext().isUserInRole("trainer")){
            isValid = true;

            // Limitation actuelle : on ne créé que des joueurs Suisse
            Country c = new Country("Switzerland", "CH");
            newPlayer.setNationality(c);

            newPlayer.setPosition(targetPosition);

            checkLastname(newPlayer);
            checkFirstname(newPlayer);
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
                initializeNewPlayer();
                clearState = true;
                return "addNewPlayerSuccess";
            }else{
                messages.add(0,"Following errors occured :");
            }
        }else{
            messages.add(NOT_AUTHORIZED_MESSAGE);
        }

        return "";
    }

    public String makeNewPlayerInsertion(){
        messages.clear();
        return "addNewPlayer";
    }

    public String updatePlayerInfo(){
        messages.clear();

        if (FacesContext.getCurrentInstance().getExternalContext().isUserInRole("administrator") ||
                FacesContext.getCurrentInstance().getExternalContext().isUserInRole("player")) {
            isValid = true;

            playerToUpdate.setPosition(targetPosition);

            checkLastname(playerToUpdate);
            checkFirstname(playerToUpdate);
            checkDateOfBirth(playerToUpdate);

            if(isValid){
                football.updatePlayerInfo(playerToUpdate);
                messages.add(playerToUpdate.getFirstname() + " " + playerToUpdate.getLastname() + " successfully updated");
                clearState = true;
                return "updatePlayerInfoSuccess";
            }else{
                messages.add(0,"Following errors occured :");
            }
        }else{
            messages.add(NOT_AUTHORIZED_MESSAGE);
        }
        return "";
    }

    public String makeNewPlayerInfoUpdate(){
        messages.clear();
        return "updatePlayerInfo";
    }




    /********************
     *******TEAM*********
     ********************/

    public void updateTargetTeam(ValueChangeEvent event) {
        System.out.println("updateTargetTeam");
        targetTeamName = (String) event.getNewValue();
        bindTeamObject(targetTeamName);
    }

    private void bindTeamObject(String targetTeamName) {
        for (Team t :
                targetLeagueTeams) {
            if(t.getName().equals(targetTeamName)) {
                targetTeam = t;
                System.out.println("updateTargetTeam - team updated to "+ targetTeam.getName());
            }
        }
    }


    /********************
     ******TRAINER*******
     ********************/

    public List<Trainer> getTrainers() {
        System.out.println("getTrainers");
        return trainers;
    }

    private void getConnectedTrainer() {
        if (FacesContext.getCurrentInstance().getExternalContext().isUserInRole("trainer")) {
            String info = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
            String prenom = info.split("\\.")[0];
            String nom = info.split("\\.")[1];
            for (Trainer t :
                    trainers) {
                if (t.getFirstname().equalsIgnoreCase(prenom) && t.getLastname().equalsIgnoreCase(nom)) {
                    connectedTrainer = t;
                    break;
                }
            }
        }
    }


    /********************
     *****TRANSFER*******
     ********************/

    public List<Transfer> getTransfers() {
        System.out.println("getTransfers");
        return transfers;
    }

    public String transferPlayer(){
        messages.clear();

        if (FacesContext.getCurrentInstance().getExternalContext().isUserInRole("administrator")) {
            if(targetPlayerObject.getCurrentTeam().getId() == targetTeam.getId()) {
                System.out.println("transferPlayer - new and old team are the same");
                messages.add(targetPlayer + " already plays for " + targetTeamName);
            }else {
                football.transferPlayer(targetPlayerObject, targetTeam);
                transfers = football.getTransfers();
                refreshLeagueTeams();
                messages.add(targetPlayer + " successfully transfered to " + targetTeamName);
                clearState = true;
                return "transferPlayerSuccess";
            }
        }else{
            messages.add(NOT_AUTHORIZED_MESSAGE);
        }

        return "";
    }

    public String makeNewTransfer(){
        messages.clear();
        return "transferPlayer";
    }
}
