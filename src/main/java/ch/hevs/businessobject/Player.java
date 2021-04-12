package ch.hevs.businessobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Player extends Person {

    @Column(name="player_position") // position is a reserved keyword
    private String position;

    @Column(name="number")
    private int number;

    @Column(name="height")
    private int height;     // height in cm

    @Column(name="weight")
    private int weight;  // weight in kg


    public Player(){}

    public Player(String lastname, String firstname, Date dateOfBirth, Team currentTeam, Country nationality,
                  String position, int number, int height, int weight){
        super(lastname, firstname, dateOfBirth, currentTeam, nationality);
        this.position=position;
        this.number=number;
        this.height=height;
        this.weight=weight;
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

}
