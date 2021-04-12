package ch.hevs.managedbeans;

import ch.hevs.businessobject.Player;
import ch.hevs.businessobject.Trainer;
import ch.hevs.footballservice.Football;

import javax.annotation.PostConstruct;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.List;

public class TrainerBean {
    private List<Trainer> trainers;
    private Football football;

    @PostConstruct
    public void initialize() throws NamingException {
        System.out.println("TrainerBean - initialize");
        // use JNDI to inject reference to bank EJBB
        InitialContext ctx = new InitialContext();
        football = (Football) ctx.lookup("java:global/HES_635-1_JEE_Project-1.0-SNAPSHOT/FootballBean!ch.hevs.footballservice.Football");

        trainers = football.getTrainers();
    }

    public List<Trainer> getTrainers() {
        System.out.println("TrainerBean - getTrainers");
        return trainers;
    }
}
