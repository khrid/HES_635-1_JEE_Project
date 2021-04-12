package ch.hevs.businessobject;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Transfer {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;

    @Column(name="transfer_date", nullable = false)   // DATE is a reserved keyword
    private LocalDateTime date;

    @ManyToOne @JoinColumn(name="fk_player", nullable = false)
    private Player player;

    @ManyToOne @JoinColumn(name="fk_old_team", nullable = false)
    private Team oldTeam;

    @ManyToOne @JoinColumn(name="fk_new_team", nullable = false)
    private Team newTeam;

    public Transfer(LocalDateTime date, Player player, Team oldTeam, Team newTeam){
        this.date=date;
        this.player=player;
        this.oldTeam=oldTeam;
        this.newTeam=newTeam;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Team getOldTeam() {
        return oldTeam;
    }

    public void setOldTeam(Team oldTeam) {
        this.oldTeam = oldTeam;
    }

    public Team getNewTeam() {
        return newTeam;
    }

    public void setNewTeam(Team newTeam) {
        this.newTeam = newTeam;
    }


}
