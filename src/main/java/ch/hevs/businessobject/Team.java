package ch.hevs.businessobject;

import javax.persistence.*;
import java.util.List;

@Entity
public class Team {

    @Id @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="stadium")
    private String stadium;

    @Column(name="yearOfCreation")
    private int yearOfCreation;

    @ManyToOne @JoinColumn(name="fk_league")
    private League currentLeague;

    @OneToMany(mappedBy = "currentTeam")
    private List<Person> contingent;

    @OneToMany(mappedBy = "oldTeam")
    private List<Transfer> freedPlayerTransfers;

    @OneToMany(mappedBy = "newTeam")
    private List<Transfer> recrutedPlayerTransfers;

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

    public List<Person> getContingent() {
        return contingent;
    }

    public void setContingent(List<Person> contingent) {
        this.contingent = contingent;
    }

    public List<Transfer> getFreedPlayerTransfers() {
        return freedPlayerTransfers;
    }

    public void setFreedPlayerTransfers(List<Transfer> freedPlayerTransfers) {
        this.freedPlayerTransfers = freedPlayerTransfers;
    }

    public List<Transfer> getRecrutedPlayerTransfers() {
        return recrutedPlayerTransfers;
    }

    public void setRecrutedPlayerTransfers(List<Transfer> recrutedPlayerTransfers) {
        this.recrutedPlayerTransfers = recrutedPlayerTransfers;
    }


}
