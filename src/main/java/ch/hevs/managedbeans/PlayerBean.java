package ch.hevs.managedbeans;

import ch.hevs.businessobject.League;
import ch.hevs.businessobject.Player;
import ch.hevs.businessobject.Team;
import ch.hevs.footballservice.Football;

import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.List;

public class PlayerBean {
    private List<Player> players;
    private List<String> playerNames;
    private String targetPlayer;
    private Player targetPlayerObject;
    private Player newPlayer;
    private Player playerToUpdate;
    private int number;
    private Football football;

    @PostConstruct
    public void initialize() throws NamingException {
        System.out.println("PlayerBean - initialize");
        // use JNDI to inject reference to bank EJBB
        InitialContext ctx = new InitialContext();
        football = (Football) ctx.lookup("java:global/HES_635-1_JEE_Project-1.0-SNAPSHOT/FootballBean!ch.hevs.footballservice.Football");

        refreshPlayersList();

        newPlayer = new Player();
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
        System.out.println("PlayerBean - updateTargetPlayer");
        targetPlayer = (String) event.getNewValue();
        System.out.println(targetPlayer);
        for (Player p :
                players) {
            if((p.getFirstname() + " " + p.getLastname()).equals(targetPlayer)) {
                targetPlayerObject = p;
                number = targetPlayerObject.getNumber();
                System.out.println("PlayerBean - updateTargetPlayer - player updated to "+targetPlayerObject.getLastname());
            }
        }
    }

    public void updateNumber() {
        if(targetPlayerObject.getNumber() == number) {
            System.out.println("PlayerBean - updateNumber - new and old number are the same");
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

}
