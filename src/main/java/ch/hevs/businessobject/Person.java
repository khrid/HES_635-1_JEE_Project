package ch.hevs.businessobject;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="fk_team")
    private Team currentTeam;

    @Embedded
    @Column(name="nationality")//, nullable = false)
    private Country nationality;

    @Transient
    boolean dateValid = true;
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Person(){}

    public Person(String lastname, String firstname, LocalDate dateOfBirth/*, Team currentTeam*/, Country nationality){
        this.lastname=lastname;
        this.firstname=firstname;
        this.dateOfBirth=dateOfBirth;
        //this.currentTeam=currentTeam;
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

    public String getDateOfBirth() {
        if(dateOfBirth == null){
            return "";
        }else{
            return dateOfBirth.format(formatter);
        }
    }

    public void setDateOfBirth(String dateOfBirth) {
        try{
            this.dateOfBirth = LocalDate.parse(dateOfBirth, formatter);
            dateValid = true;
        }catch (DateTimeParseException e){
            dateValid = false;
        }

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

    public boolean isDateValid(){
        return dateValid;
    }

    public int getAge() {
        Calendar now = Calendar.getInstance();
        Calendar birth = Calendar.getInstance();
        birth.set(dateOfBirth.getYear(), dateOfBirth.getMonthValue()-1, dateOfBirth.getDayOfMonth());
        int diff = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
        if (birth.get(Calendar.MONTH) > now.get(Calendar.MONTH) ||
                (birth.get(Calendar.MONTH) == now.get(Calendar.MONTH) && birth.get(Calendar.DATE) > now.get(Calendar.DATE))) {
            diff--;
        }
        return diff;
    }
}
