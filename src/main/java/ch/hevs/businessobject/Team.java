package ch.hevs.businessobject;

import javax.persistence.*;

@Entity
public class Team {

    @Id @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="stadium")
    private String stadium;

    @Column(name="yearOfCreation")
    private int yearOfCreation;

    @ManyToOne @JoinColumn(name="fk_league")
    private League currentLeague;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStadium() { return stadium; }
    public void setStadium(String stadium) { this.stadium = stadium; }

    public int getYearOfCreation() { return yearOfCreation; }
    public void setYearOfCreation(int yearOfCreation) { this.yearOfCreation = yearOfCreation; }

    public League getCurrentLeague() { return currentLeague; }
    public void setCurrentLeague(League currentLeague) { this.currentLeague = currentLeague; }

    public Team() {

    }

    public Team(String name, String stadium, int yearOfCreation) {
        this.name = name;
        this.stadium = stadium;
        this.yearOfCreation = yearOfCreation;
    }

    public Team(String name, String stadium, int yearOfCreation, League currentLeague) {
        this.name = name;
        this.stadium = stadium;
        this.yearOfCreation = yearOfCreation;
        this.currentLeague = currentLeague;
    }

    public String getCountryAndDivisionInfo() {
        return this.currentLeague.getCountry().getCode() + " division #" + this.currentLeague.getDivision();
    }

}
