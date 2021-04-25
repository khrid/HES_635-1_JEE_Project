package ch.hevs.businessobject;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Inheritance (strategy = InheritanceType.TABLE_PER_CLASS)
public class Person {

    @Id @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;

    @Column(name="lastname", nullable = false)
    private String lastname;

    @Column(name="firstname", nullable = false)
    private String firstname;

    @Column(name="dateOfBirth")
    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name="fk_team")
    private Team currentTeam;

    @Embedded
    @Column(name="nationality", nullable = false)
    private Country nationality;


    public Person(){}

    public Person(String lastname, String firstname, LocalDate dateOfBirth, Team currentTeam, Country nationality){
        this.lastname=lastname;
        this.firstname=firstname;
        this.dateOfBirth=dateOfBirth;
        this.currentTeam=currentTeam;
        this.nationality=nationality;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Team getCurrentTeam() {
        return currentTeam;
    }

    public void setCurrentTeam(Team currentTeam) {
        this.currentTeam = currentTeam;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }


}