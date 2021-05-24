package ch.hevs.businessobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Player extends Person {

    @Column(name = "player_position")//, nullable = false) // POSITION is a reserved keyword
    private String position;

    @Column(name = "number")//, nullable = false)
    private int number;

    @Column(name = "height")
    private int height;     // height in cm

    @Column(name = "weight")
    private int weight;  // weight in kg

    @OneToMany(mappedBy = "player")
    private List<Transfer> transfers;


    public Player() {
    }

    public Player(String lastname, String firstname, LocalDate dateOfBirth, Team currentTeam, Country nationality,
                  String position, int number, int height, int weight) {
        super(lastname, firstname, dateOfBirth, nationality);
        this.position = position;
        this.number = number;
        this.height = height;
        this.weight = weight;
        this.transfers = new ArrayList<>();
        currentTeam.addPlayer(this);
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<Transfer> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<Transfer> transfers) {
        this.transfers = transfers;
    }

    @Override
    public String toString() {
        return "Player{" +
                "position='" + position + '\'' +
                ", number=" + number +
                ", height=" + height +
                ", weight=" + weight +
                //", transfers=" + transfers +
                '}';
    }
}
