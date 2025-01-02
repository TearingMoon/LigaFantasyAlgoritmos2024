package main;

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
		for(Team t : teams.toList().Sort(new Team[0]))
		{
			System.out.println(t);
		}
		Team[] promotions = new Team[3];
		Team[] relegations = new Team[3];
		
		for(int i = 0, j = 0; i < teams.toList().GetSize(); i++)
		{
			if(i < 3) promotions[i] = teams.toList().ToArray(new Team[0])[i];
			if(i >= teams.toList().GetSize() - 3) { relegations[j] = teams.toList().ToArray(new Team[0])[i]; j++; }
		}
		
		System.out.println("\nAscensos:" + promotions[1].getName() + ", " + promotions[2] + ", " + promotions[3]);
		System.out.println("\nDescensos:" + relegations[1].getName() + ", " + relegations[2] + ", " + relegations[3]);
	}

	public boolean teamExists(String identifier) {
		return teams.contains(identifier);
	}
}
