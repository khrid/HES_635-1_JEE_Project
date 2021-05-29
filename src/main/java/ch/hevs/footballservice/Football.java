package ch.hevs.footballservice;

import ch.hevs.businessobject.*;

import javax.ejb.Local;
import java.util.List;

@Local
public interface Football {
    List<League> getLeagues();
    List<Player> getPlayers();
    List<Player> getPlayersByTeam(Team team);
    List<Trainer> getTrainers();
    List<Transfer> getTransfers();
    List<Team> getLeagueTeams(String targetLeague);
    Team getTeamByName(String targetTeamName);

    boolean promoteTeam(Team team);
    boolean relegateTeam(Team team);

    void transferPlayer(Player player, Team newTeam);
    int[] getLeagueStatistics(String targetLeague);
    void createNewPlayer(Player player, Team team);
    void updatePlayerInfo(Player player);
}
