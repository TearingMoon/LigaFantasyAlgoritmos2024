package main;
import java.util.Scanner;
import structures.HashTable;
import structures.HashTable.exploration;
import entities.Team;
import utilities.InputManager;


public class FantasyLeague {

	int numberOfTeams;
	Scanner sc;
	HashTable<Team> teams;
	
	public FantasyLeague(int InitialNumberOfTeams)
	{
		numberOfTeams = 0;
		teams = new HashTable<Team>(0, exploration.LINEAL);
		teams.rehash(InitialNumberOfTeams);
		sc = new Scanner(System.in);
	}
	
	public void addTeam(Team... teams)
	{
		for(Team team : teams)
		{
			this.teams.insert(team.getName(), team);
			numberOfTeams++;
		}
	}
	
	public void addPoints(String identifier) {
		int points;
		if(!teams.contains(identifier)) return;
		else 
		{
			points = InputManager.GetInt("Cuantos puntos quieres añadir? (0, 1 o 3): ", sc, integer -> integer==3 || integer==0 || integer==1);
			try { teams.get(identifier).addScore(points); } catch (Exception e) { e.printStackTrace(); }
		}
	}
}
