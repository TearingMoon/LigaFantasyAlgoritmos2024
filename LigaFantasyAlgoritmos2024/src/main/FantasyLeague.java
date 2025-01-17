package main;

import structures.DoubleLinkedCircularList;
import structures.HashTable;
import structures.HashTable.exploration;
import structures.MergeSort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

	public void addPoints(String identifier1, String identifier2, int pointsIdentifier1, int pointsIdentifier2) {
		if (pointsIdentifier1 < pointsIdentifier2) {
			this.addPoints(identifier2, 3);
		} else if (pointsIdentifier1 > pointsIdentifier2) {
			this.addPoints(identifier1, 3);
		} else if (pointsIdentifier1 == pointsIdentifier2) {
			this.addPoints(identifier1, 1);
			this.addPoints(identifier2, 1);
		}
	}

	public void addGoals(String identifier1, String identifier2, int goalsIdentifier1, int goalsIdentifier2) {
		this.addGoalsInFavor(identifier1, goalsIdentifier1);
		this.addGoalsInFavor(identifier2, goalsIdentifier2);

		this.addGoalsAgainst(identifier1, goalsIdentifier2);
		this.addGoalsAgainst(identifier2, goalsIdentifier1);
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
				teams.get(identifier).addGoalsAgainst(goals);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void getPuntuations() {
		Comparable<Team>[] sortedTeams = (Comparable<Team>[]) teams.toList().ToArray(new Team[0]);
		MergeSort.mergeSort(sortedTeams);

		System.out.println("Nombre\tPuntuacion\tDiferencia de goles\n");
		for (Comparable<Team> t : sortedTeams) {
			System.out.println(t);
		}

		if (sortedTeams.length >= 6) { // Only shows Promotions and relegations when more than 5 teams

			DoubleLinkedCircularList<String> promotions = new DoubleLinkedCircularList<String>();
			DoubleLinkedCircularList<String> relegations = new DoubleLinkedCircularList<String>();

			for (int i = 0; i < sortedTeams.length; i++) {
				if (i < 3) {
					Team team = (Team) sortedTeams[i];
					promotions.Insert(team.getName());
				}
				if (i >= sortedTeams.length - 3) {
					Team team = (Team) sortedTeams[i];
					relegations.Insert(team.getName());
				}
			}

			System.out.println("\nAscensos: " + String.join(", ", promotions));
			System.out.println("\nDescensos: " + String.join(", ", relegations));
			System.out.println("");
		}

	}

	public void clearPuntutations() {
		DoubleLinkedCircularList<Team> teamsList = this.teams.toList();
		for (int i = 0; i < teamsList.GetSize(); i++) {
			this.teams.get(teamsList.Get(i).getName()).restoreGameData();
		}
	}
	
	public boolean teamExists(String identifier) {
		return teams.contains(identifier);
	}

	public HashTable<Team> getTeams() {
		return teams;
	}

	public static int generateGoals() {
		Random rd = new Random();
		return rd.nextInt(7) + 1;
	}
}
