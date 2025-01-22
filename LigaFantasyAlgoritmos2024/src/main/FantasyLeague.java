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
		
		if (goalsIdentifier1 > goalsIdentifier2)
		{
			Team winningTeam = teams.get(identifier1);
			Team loosingTeam = teams.get(identifier2);
			
			winningTeam.setWinStreak(winningTeam.getWinStreak()+1);
			if (loosingTeam.getWinStreak() > loosingTeam.getHighestWinStreak()) loosingTeam.setHighestWinStreak(loosingTeam.getWinStreak());
			loosingTeam.setWinStreak(0);
			
			loosingTeam.setLoseStreak(loosingTeam.getLoseStreak()+1);
			if (winningTeam.getLoseStreak() > winningTeam.getHighestLoseStreak()) winningTeam.setHighestLoseStreak(winningTeam.getLoseStreak());
			winningTeam.setLoseStreak(0);
			
			winningTeam.setNumberOfWinsAsLocal(winningTeam.getNumberOfWinsAsLocal()+1);
			loosingTeam.setNumberOfLosesAsVisitant(loosingTeam.getNumberOfLosesAsVisitant()+1);
		}
		else if (goalsIdentifier1 < goalsIdentifier2)
		{
			Team winningTeam = teams.get(identifier2);
			Team loosingTeam = teams.get(identifier1);
			
			winningTeam.setWinStreak(winningTeam.getWinStreak()+1);
			if (loosingTeam.getWinStreak() > loosingTeam.getHighestWinStreak()) loosingTeam.setHighestWinStreak(loosingTeam.getWinStreak());
			loosingTeam.setWinStreak(0);
			
			loosingTeam.setLoseStreak(loosingTeam.getLoseStreak()+1);
			if (winningTeam.getLoseStreak() > winningTeam.getHighestLoseStreak()) winningTeam.setHighestLoseStreak(winningTeam.getLoseStreak());
			winningTeam.setLoseStreak(0);
			
			winningTeam.setNumberOfWinsAsVisitant(winningTeam.getNumberOfWinsAsVisitant()+1);
			loosingTeam.setNumberOfLosesAsLocal(loosingTeam.getNumberOfLosesAsLocal()+1);
		}
		else
		{
			if (teams.get(identifier2).getWinStreak() > teams.get(identifier2).getHighestWinStreak()) teams.get(identifier2).setHighestWinStreak(teams.get(identifier2).getWinStreak());
			teams.get(identifier2).setWinStreak(0);
			if (teams.get(identifier2).getLoseStreak() > teams.get(identifier2).getHighestLoseStreak()) teams.get(identifier2).setHighestLoseStreak(teams.get(identifier2).getLoseStreak());
			teams.get(identifier2).setWinStreak(0);
			
			if (teams.get(identifier1).getWinStreak() > teams.get(identifier1).getHighestWinStreak()) teams.get(identifier1).setHighestWinStreak(teams.get(identifier1).getWinStreak());
			teams.get(identifier1).setWinStreak(0);
			if (teams.get(identifier1).getLoseStreak() > teams.get(identifier1).getHighestLoseStreak()) teams.get(identifier1).setHighestLoseStreak(teams.get(identifier1).getLoseStreak());
			teams.get(identifier1).setWinStreak(0);
			
			teams.get(identifier1).setNumberOfTies(teams.get(identifier1).getNumberOfTies()+1);
			teams.get(identifier2).setNumberOfTies(teams.get(identifier2).getNumberOfTies()+1);
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
				teams.get(identifier).addGoalsAgainst(goals);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void getPuntuations() {
		Comparable<Team>[] sortedTeams = (Comparable<Team>[]) teams.toList().ToArray(new Team[0]);
		MergeSort.mergeSort(sortedTeams);
		
		System.out.println("Resultados:");
		System.out.println();
		System.out.println("Nombre\t\t\tPuntuacion\tDiferencia de goles\n");
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
	
	public void getStatistics()
	{
		/* TODO: Las siguientes estadisticas
		 * - Equipo con mayor numero de goles
		 * - Equipo con mayor numero de goles en contra
		 * - Mejor racha de victorias
		 * - Peor racha de derrotas
		 */
		
		DoubleLinkedCircularList<Team> teamsList = this.teams.toList();
		Team mostGoalsForTeam = teamsList.Get(0);
		Team mostGoalsAgainstTeam = teamsList.Get(0);
		Team highestWinStreakTeam = teamsList.Get(0);
		Team highestLoseStreakTeam = teamsList.Get(0);
		Team highestGoalDifferenceTeam = teamsList.Get(0);
		Team lowestGoalDifferenceTeam = teamsList.Get(0);
		Team mostWinsAsLocalTeam = teamsList.Get(0);
		Team mostWinsAsVisitantTeam = teamsList.Get(0);
		Team mostLosesAsLocalTeam = teamsList.Get(0);
		Team mostLosesAsVisitantTeam = teamsList.Get(0);
		
		for(Team t : teamsList)
		{
			if(t.getGoalsFor() > mostGoalsForTeam.getGoalsFor()) mostGoalsForTeam = t;
			if(t.getGoalsAgainst() > mostGoalsAgainstTeam.getGoalsAgainst()) mostGoalsAgainstTeam = t;
			if(t.getHighestWinStreak() > highestWinStreakTeam.getHighestWinStreak()) highestWinStreakTeam = t;
			if(t.getHighestLoseStreak() > highestLoseStreakTeam.getHighestLoseStreak()) highestLoseStreakTeam = t;
			if(t.getNumberOfWinsAsLocal() > mostWinsAsLocalTeam.getNumberOfWinsAsLocal()) mostWinsAsLocalTeam = t;
			if(t.getNumberOfWinsAsVisitant() > mostWinsAsVisitantTeam.getNumberOfWinsAsVisitant()) mostWinsAsVisitantTeam = t;
			if(t.getNumberOfLosesAsLocal() > mostLosesAsLocalTeam.getNumberOfLosesAsLocal()) mostLosesAsLocalTeam = t;
			if(t.getNumberOfLosesAsVisitant() > mostLosesAsVisitantTeam.getNumberOfLosesAsVisitant()) mostLosesAsVisitantTeam = t;
			if(t.getGoalsDifference() > highestGoalDifferenceTeam.getGoalsDifference()) highestGoalDifferenceTeam = t;
			if(t.getGoalsDifference() < lowestGoalDifferenceTeam.getGoalsDifference()) lowestGoalDifferenceTeam = t;
		}
		
		System.out.println("Estadísticas:\n");
		
		System.out.println("Equipo con mas goles a favor: " + mostGoalsForTeam.getName());
		System.out.println("Equipo con mas goles en contra: " + mostGoalsAgainstTeam.getName());
		System.out.println("Equipo con mayor diferencia de goles: " + highestGoalDifferenceTeam.getName());
		System.out.println("Equipo con menor diferencia de goles: " + lowestGoalDifferenceTeam.getName());
		
		System.out.println("Equipo con mayor racha de victorias: " + highestWinStreakTeam.getName());
		System.out.println("Equipo con mayor racha de derrotas: " + highestLoseStreakTeam.getName());
		
		System.out.println("Equipo con mas victorias como local: " + mostWinsAsLocalTeam.getName());
		System.out.println("Equipo con mas victorias como visitante: " + mostWinsAsVisitantTeam.getName());
		System.out.println("Equipo con mas derrotas como local: " + mostLosesAsLocalTeam.getName());
		System.out.println("Equipo con mas derrotas como visitante: " + mostLosesAsVisitantTeam.getName());
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
		return rd.nextInt(8);
	}
	
	/**
	 * @param Equipo local
	 * @param Equipo visitante
	 */
	public static int[] simulateMatchGoals(Team team1, Team team2)
	{
		int[] goals = new int[2];
		
		int budgetDifference = Math.abs(team1.getBudget()-team2.getBudget());
		
		int[] probabilities;
		int biggerTeam;
		int smallerTeam;
		
		if(team1.getBudget() >= team2.getBudget()) { biggerTeam = 0; smallerTeam = 1; }
		else { biggerTeam = 1; smallerTeam = 0; }
		
		if(budgetDifference > 20000000)
		{
			probabilities = new int[] 
			{
				15, // Goleada
				50, // Victoria del grande
				20, // Empate
				10, // Victoria del modesto
				5 // Sorpresa
			};
		}
		else if (budgetDifference >= 5000000)
		{
			probabilities = new int[] 
			{
				10,
				40,
				30,
				15,
				5
			};
		}
		else
		{
			probabilities = new int[] 
			{
				5,
				30,
				40,
				20,
				5
			};
		}
		
		Random rd = new Random();
		int randomNum = rd.nextInt(100);
		
		if(randomNum < probabilities[0]) // Probabilidad acumulativa
		{
			// Goleada
			goals[smallerTeam] = rd.nextInt(5); // De 0 a 4 goles
			goals[biggerTeam] = rd.nextInt(8 - (goals[smallerTeam] + 3)) + (goals[smallerTeam] + 3); // Minimo 3 goles mas que el otro equipo y maximo 7 goles
		}
		else if(randomNum < probabilities[0] + probabilities[1])
		{
			// Victoria del grande
			int goalsDifference = rd.nextInt(3 - 1) + 1; // Uno o dos goles de diferencia
			goals[smallerTeam] = rd.nextInt((7-goalsDifference) + 1); // Como maximo 7 goles menos la diferencia de 1 o 2
			goals[biggerTeam] = goals[smallerTeam] + goalsDifference;
		}
		else if(randomNum < probabilities[0] + probabilities[1] + probabilities[2])
		{
			// Empate
			goals[biggerTeam] = goals[smallerTeam] = rd.nextInt(8);
		}
		else if(randomNum < probabilities[0] + probabilities[1] + probabilities[2] + probabilities[3])
		{
			// Victoria del humilde
			int goalsDifference = rd.nextInt(3 - 1) + 1; // Uno o dos goles de diferencia
			goals[biggerTeam] = rd.nextInt((7-goalsDifference) + 1); // Como maximo 7 goles menos la diferencia de 1 o 2
			goals[smallerTeam] = goals[biggerTeam] + goalsDifference;
		}
		else
		{
			// Sorpresa
			goals[biggerTeam] = rd.nextInt(5); // De 0 a 4 goles
			goals[smallerTeam] = rd.nextInt(8 - (goals[biggerTeam] + 3)) + (goals[biggerTeam] + 3); // Minimo 3 goles mas que el otro equipo y maximo 7 goles
		}
		
		return goals;
	}
}
