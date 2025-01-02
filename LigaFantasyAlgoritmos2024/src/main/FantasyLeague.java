package main;

import structures.DoubleLinkedCircularList;
import structures.HashTable;
import structures.HashTable.exploration;
import entities.Team;

public class FantasyLeague {

	int numberOfTeams;
	HashTable<Team> teams;

	public FantasyLeague(int InitialNumberOfTeams) {
		numberOfTeams = 0;
		teams = new HashTable<Team>(exploration.LINEAL);
		teams.rehash(InitialNumberOfTeams);
	}

	public void addTeam(Team... teams) {
		for (Team team : teams) {
			this.teams.insert(team.getName(), team); // Sets the name as the key
			numberOfTeams++;
		}
	}

	public void addPoints(String identifier, int points) {
		if (!teams.contains(identifier))
			return;
		else {
			try {
				teams.get(identifier).addScore(points);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void addGoalsInFavor(String identifier, int goals) {
		if (!teams.contains(identifier))
			return;
		else {
			try {
				teams.get(identifier).addGoalsFor(goals);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void addGoalsAgainst(String identifier, int goals) {
		if (!teams.contains(identifier))
			return;
		else {
			try {
				teams.get(identifier).addGoalsFor(goals);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}	

	public void getPuntuations() {
		System.out.println("Nombre\tPuntuacion\tDiferencia de goles\n");
		for(Team t : (teams.toList().Sort(new Team[0])))
		{
			System.out.println(t);
		}
		DoubleLinkedCircularList<String> promotions = new DoubleLinkedCircularList<String>();
		DoubleLinkedCircularList<String> relegations = new DoubleLinkedCircularList<String>();
		
		for(int i = 0; i < teams.toList().GetSize(); i++)
		{
			if(i < 3) promotions.Insert(teams.toList().ToArray(new Team[0])[i].getName());
			if(i >= teams.toList().GetSize() - 3) relegations.Insert(teams.toList().ToArray(new Team[0])[i].getName());
		}
		
		System.out.println("\nAscensos: " + String.join(", ", promotions));
		System.out.println("\nDescensos: " + String.join(", ", relegations));
	}

	public boolean teamExists(String identifier) {
		return teams.contains(identifier);
	}
}
