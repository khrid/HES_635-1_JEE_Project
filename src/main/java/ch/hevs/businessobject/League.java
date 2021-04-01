package ch.hevs.businessobject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class League {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="division")
    private int division;

    @Embedded
    @Column(name="country")
    private Country country;

    @OneToMany(mappedBy = "currentLeague")
    private List<Team> teams;

    // getter and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getDivision() { return division; }
    public void setDivision(int division) { this.division = division; }

    public Country getCountry() { return country; }
    public void setCountry(Country country) { this.country = country; }

    public List<Team> getTeams() { return teams; }
    public void setTeams(List<Team> teams) { this.teams = teams; }


    // constructors
    public League() {

    }

    public League(String name, int division, Country country) {
        this.name = name;
        this.division = division;
        this.country = country;
        this.teams = new ArrayList<>();
    }

    public void addTeam(Team team) {
        teams.add(team);
        team.setCurrentLeague(this);
    }

    @Override
    public String toString() {
        return "League{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", division=" + division +
                ", country=" + country +
                '}';
    }
}
