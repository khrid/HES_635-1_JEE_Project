package ch.hevs.businessobject;

import javax.persistence.*;
import java.util.ArrayList;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="fk_league")
    private League currentLeague;

    @OneToOne
    @JoinColumn(name="fk_trainer")
    private Trainer trainer;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "currentTeam", cascade = CascadeType.ALL )
    private List<Player> contingent;

    @OneToMany(mappedBy = "oldTeam", cascade = CascadeType.ALL )
    private List<Transfer> freedPlayerTransfers;

    @OneToMany(mappedBy = "newTeam", cascade = CascadeType.ALL )
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
        this.contingent = new ArrayList<>();
        this.freedPlayerTransfers = new ArrayList<>();
        this.recrutedPlayerTransfers = new ArrayList<>();
    }

    public Team(String name, String stadium, int yearOfCreation, League currentLeague) {
        this.name = name;
        this.stadium = stadium;
        this.yearOfCreation = yearOfCreation;
        this.currentLeague = currentLeague;
        this.contingent = new ArrayList<>();
        this.freedPlayerTransfers = new ArrayList<>();
        this.recrutedPlayerTransfers = new ArrayList<>();
    }

    public String getCountryAndDivisionInfo() {
        return this.currentLeague.getCountry().getCode() + " division #" + this.currentLeague.getDivision();
    }

    public List<Player> getContingent() {
        return contingent;
    }

    public void setContingent(List<Player> contingent) {
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

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer t) {
        this.trainer = t;
        t.setCurrentTeam(this);
    }

    public void addPlayer(Player p) {
        this.contingent.add(p);
        p.setCurrentTeam(this);
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stadium='" + stadium + '\'' +
                ", yearOfCreation=" + yearOfCreation +
                ", currentLeague=" + currentLeague +
                //", contingent=" + contingent +
                //", freedPlayerTransfers=" + freedPlayerTransfers +
                //", recrutedPlayerTransfers=" + recrutedPlayerTransfers +
                '}';
    }
}
