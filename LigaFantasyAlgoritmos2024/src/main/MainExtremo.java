package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import entities.Player;
import entities.Team;
import entities.Match;
import structures.DoubleLinkedCircularList;
import utilities.InputManager;

public class MainExtremo {
	public static FantasyLeague league;

	public static void main(String[] args)
	{
		Team[] teams = new Team[20];
		try
		{
			teams = loadTeamsFromCSV();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		league = new FantasyLeague(teams.length);
		league.addTeam(teams);
		
		mainMenu();
	}
	
	/*
	 * Hecho por Sergio	
	 */
	
	public static Team[] loadTeamsFromCSV() throws Exception
	{
		DoubleLinkedCircularList<Team> teams = new DoubleLinkedCircularList<Team>();
		DoubleLinkedCircularList<Player> players = new DoubleLinkedCircularList<Player>();
		
		BufferedReader br = null;
		try
		{
			br =new BufferedReader(new FileReader("./players.csv"));
			String line = br.readLine(); // Nos saltamos la primera linea del CSV (encabezado)
			line = br.readLine();
			while (null!=line)
			{
				String [] fields = line.split(",");
				
				players.Insert(new Player(fields[0], Integer.parseInt(fields[1]), fields[2], Integer.parseInt(fields[3])));
				
				line = br.readLine();
			}
		} 
		catch (Exception e)
		{
			System.out.println("Error leyendo players.csv: " + e);
		} 
		finally { if (null!=br) { br.close(); } }
		
		try
		{
			br =new BufferedReader(new FileReader("./teams.csv"));
			String line = br.readLine(); // Nos saltamos la primera linea del CSV (encabezado)
			line = br.readLine();
			int i = 0;
			while (null!=line)
			{
				String [] fields = line.split(",");
				
				Player[] playersInTeam = new Player[11];
				
				for(int j = 11*i, k = 0; k < 11; j++, k++)
				{
					playersInTeam[k] = players.Get(j);
				}
				
				Team team = new Team(fields[0], 0, 0, 0, playersInTeam);
				
				teams.Insert(team);
				
				line = br.readLine();
				i++;
			}
		} 
		catch (Exception e)
		{
			System.out.println("Error leyendo players.csv: " + e);
		} 
		finally { if (null!=br) { br.close(); } }
		
		return teams.ToArray(new Team[0]);
	}

	public static void mainMenu() {
		String options[] = {"Simulación", "Ver equipos"};
		Consumer<Integer> actionHandler = (selection) -> {
			switch (selection) {
			case 1:
				gameSimulation();
				break;
			case 2:
				listTeams();
				break;
			default: // Para evitar errores
				break;
			}
		};
		handleMenu(options, actionHandler);
	}

	/**
	 * LO HA HECHO DAVID!!! (asistencia de escritura(- David: "Y revision de codigo") por emilly) :) <3
	 */
	public static void gameSimulation() {
		DoubleLinkedCircularList<Team> teams = league.getTeams().toList();
		DoubleLinkedCircularList<Team> auxTeams = league.getTeams().toList();
		DoubleLinkedCircularList<Match> tournament1 = new DoubleLinkedCircularList<Match>();
		DoubleLinkedCircularList<Match> tournament2 = new DoubleLinkedCircularList<Match>();
		
		for (int i = 0; i < teams.GetSize(); i++) {
			auxTeams.Remove(teams.Get(i));
			for (int j = 0; j < auxTeams.GetSize(); j++) {
				int[] goals = FantasyLeague.simulateMatchGoals(teams.Get(i), auxTeams.Get(j));
				int goalsTeam1 = goals[0];
				int goalsTeam2 = goals[1];
				Match m = new Match(teams.Get(i), auxTeams.Get(j), goalsTeam1, goalsTeam2);
				tournament1.Insert(m);
				// Goals
				league.addGoals(teams.Get(i).getName(), auxTeams.Get(j).getName(), goalsTeam1, goalsTeam2);
				// Points
				league.addPoints(teams.Get(i).getName(), auxTeams.Get(j).getName(), goalsTeam1, goalsTeam2);
			}
		}
		
		auxTeams = league.getTeams().toList();
		for (int i = 0; i < teams.GetSize(); i++) {
			auxTeams.Remove(teams.Get(i));
			for (int j = 0; j < auxTeams.GetSize(); j++) {
				int[] goals = FantasyLeague.simulateMatchGoals(auxTeams.Get(j), teams.Get(i));
				int goalsTeam1 = goals[0];
				int goalsTeam2 = goals[1];
				Match m = new Match(auxTeams.Get(j), teams.Get(i), goalsTeam1, goalsTeam2);
				tournament2.Insert(m);
				// Goals
				league.addGoals(auxTeams.Get(j).getName(), teams.Get(i).getName(), goalsTeam1, goalsTeam2);
				// Points
				league.addPoints(auxTeams.Get(j).getName(), teams.Get(i).getName(), goalsTeam1, goalsTeam2);
			}
		}
		
		/*
		 * Hecho por Sergio con ayuda de Iker
		 */
		
		Random rd = new Random();
		
		Team team1Sunday = null, team2Sunday = null;
		DoubleLinkedCircularList<Team> teamsWhoPlayedInHome = new DoubleLinkedCircularList<Team>();
		DoubleLinkedCircularList<Team> teamsWhoPlayedAway = new DoubleLinkedCircularList<Team>();
		DoubleLinkedCircularList<Team> teamsWhoPlayedInHomeAux = new DoubleLinkedCircularList<Team>();
		DoubleLinkedCircularList<Team> teamsWhoPlayedAwayAux = new DoubleLinkedCircularList<Team>();
		DoubleLinkedCircularList<Team> teamsWhoPlayed = new DoubleLinkedCircularList<Team>();
		
		System.out.println("Primera vuelta:");
		for (int i = 0; i < 19; i++) //jornadas vuelta 1
		{
			System.out.println("\nJornada " + (i+1) + ":");
			for (int j = 0; j < 4; j++)
			{
				Match matchOfTheDay;
				DoubleLinkedCircularList<Match> suitedMatches = tournament1.Copy();
				DoubleLinkedCircularList<Match> suitedMatchesAux;
				switch(j)
				{
				case 0:
					System.out.println("Jueves:");	
					
					if (team1Sunday != null && team2Sunday != null)
					{
						final var team1S = team1Sunday;
						final var team2S = team2Sunday;
						
						suitedMatchesAux = suitedMatches.Copy();
						suitedMatches = suitedMatchesAux.FindAll(
							Match -> 
							!Match.getTeam1().getName().equals(team1S.getName()) &&
							!Match.getTeam1().getName().equals(team2S.getName()) &&
							!Match.getTeam2().getName().equals(team1S.getName()) &&
							!Match.getTeam2().getName().equals(team2S.getName())
						);
						
						if (suitedMatches.IsEmpty()) suitedMatches = suitedMatchesAux.Copy();
					}
					
					if (teamsWhoPlayedInHome.GetSize() > 0)
					{
						final var teamsHome = teamsWhoPlayedInHome.Copy();
						final var teamsAway = teamsWhoPlayedAway.Copy();
						
						suitedMatchesAux = suitedMatches.Copy();
						suitedMatches = suitedMatchesAux.FindAll(
							Match -> 
							teamsHome.IndexOf(Match.getTeam1()) == -1 &&
							teamsAway.IndexOf(Match.getTeam2()) == -1
						);
						
						if (suitedMatches.IsEmpty()) suitedMatches = suitedMatchesAux.Copy();
					}
					
					matchOfTheDay = suitedMatches.Get(rd.nextInt(suitedMatches.GetSize()));
					
					teamsWhoPlayed.Insert(matchOfTheDay.getTeam1(), matchOfTheDay.getTeam2());
					teamsWhoPlayedInHomeAux.Insert(matchOfTheDay.getTeam1());
					teamsWhoPlayedAwayAux.Insert(matchOfTheDay.getTeam2());
					tournament1.Remove(matchOfTheDay);
					System.out.println(matchOfTheDay);
					break;
				case 1:
					System.out.println("Viernes:");
					
					for (int k = 0; k < 4; k++)
					{
						suitedMatches = tournament1.Copy();
						if (teamsWhoPlayed.GetSize() > 0)
						{
							final var teamsPlayed = teamsWhoPlayed.Copy();
							
							suitedMatchesAux = suitedMatches.Copy();
							suitedMatches = suitedMatchesAux.FindAll(
								Match -> 
								teamsPlayed.IndexOf(Match.getTeam1()) == -1 &&
								teamsPlayed.IndexOf(Match.getTeam2()) == -1
							);
							
							if (suitedMatches.IsEmpty()) suitedMatches = suitedMatchesAux.Copy();
						}
						
						if (teamsWhoPlayedInHome.GetSize() > 0)
						{
							final var teamsHome = teamsWhoPlayedInHome.Copy();
							final var teamsAway = teamsWhoPlayedAway.Copy();
							
							suitedMatchesAux = suitedMatches.Copy();
							suitedMatches = suitedMatchesAux.FindAll(
								Match -> 
								teamsHome.IndexOf(Match.getTeam1()) == -1 &&
								teamsAway.IndexOf(Match.getTeam2()) == -1
							);
							
							if (suitedMatches.IsEmpty()) suitedMatches = suitedMatchesAux.Copy();
						}
						
						matchOfTheDay = suitedMatches.Get(rd.nextInt(suitedMatches.GetSize()));
						
						teamsWhoPlayed.Insert(matchOfTheDay.getTeam1(), matchOfTheDay.getTeam2());
						teamsWhoPlayedInHomeAux.Insert(matchOfTheDay.getTeam1());
						teamsWhoPlayedAwayAux.Insert(matchOfTheDay.getTeam2());
						tournament1.Remove(matchOfTheDay);
						System.out.println(matchOfTheDay);
					}
					break;
				case 2:
					System.out.println("Sabado:");
					
					for (int k = 0; k < 4; k++)
					{
						suitedMatches = tournament1.Copy();
						if (teamsWhoPlayed.GetSize() > 0)
						{
							final var teamsPlayed = teamsWhoPlayed.Copy();
							
							suitedMatchesAux = suitedMatches.Copy();
							suitedMatches = suitedMatchesAux.FindAll(
								Match -> 
								teamsPlayed.IndexOf(Match.getTeam1()) == -1 &&
								teamsPlayed.IndexOf(Match.getTeam2()) == -1
							);
							
							if (suitedMatches.IsEmpty()) suitedMatches = suitedMatchesAux.Copy();
						}
						
						if (teamsWhoPlayedInHome.GetSize() > 0)
						{
							final var teamsHome = teamsWhoPlayedInHome.Copy();
							final var teamsAway = teamsWhoPlayedAway.Copy();
							
							suitedMatchesAux = suitedMatches.Copy();
							suitedMatches = suitedMatchesAux.FindAll(
								Match -> 
								teamsHome.IndexOf(Match.getTeam1()) == -1 &&
								teamsAway.IndexOf(Match.getTeam2()) == -1
							);
							
							if (suitedMatches.IsEmpty()) suitedMatches = suitedMatchesAux.Copy();
						}
						
						matchOfTheDay = suitedMatches.Get(rd.nextInt(suitedMatches.GetSize()));
						
						teamsWhoPlayed.Insert(matchOfTheDay.getTeam1(), matchOfTheDay.getTeam2());
						teamsWhoPlayedInHomeAux.Insert(matchOfTheDay.getTeam1());
						teamsWhoPlayedAwayAux.Insert(matchOfTheDay.getTeam2());
						tournament1.Remove(matchOfTheDay);
						System.out.println(matchOfTheDay);
					}
					break;
				case 3:
					System.out.println("Domingo:");
					
					if (teamsWhoPlayed.GetSize() > 0)
					{
						final var teamsPlayed = teamsWhoPlayed.Copy();
						
						suitedMatchesAux = suitedMatches.Copy();
						suitedMatches = suitedMatchesAux.FindAll(
							Match -> 
							teamsPlayed.IndexOf(Match.getTeam1()) == -1 &&
							teamsPlayed.IndexOf(Match.getTeam2()) == -1
						);
						
						if (suitedMatches.IsEmpty()) suitedMatches = suitedMatchesAux.Copy();
					}
					
					if (teamsWhoPlayedInHome.GetSize() > 0)
					{
						final var teamsHome = teamsWhoPlayedInHome.Copy();
						final var teamsAway = teamsWhoPlayedAway.Copy();
						
						suitedMatchesAux = suitedMatches.Copy();
						suitedMatches = suitedMatchesAux.FindAll(
							Match -> 
							teamsHome.IndexOf(Match.getTeam1()) == -1 &&
							teamsAway.IndexOf(Match.getTeam2()) == -1
						);
						
						if (suitedMatches.IsEmpty()) suitedMatches = suitedMatchesAux.Copy();
					}
					
					matchOfTheDay = suitedMatches.Get(rd.nextInt(suitedMatches.GetSize()));
					
					team1Sunday = matchOfTheDay.getTeam1();
					team2Sunday = matchOfTheDay.getTeam2();
					teamsWhoPlayedInHomeAux.Insert(matchOfTheDay.getTeam1());
					teamsWhoPlayedAwayAux.Insert(matchOfTheDay.getTeam2());
					tournament1.Remove(matchOfTheDay);
					System.out.println(matchOfTheDay);
					break;
				default:
					break;
				}
			}
			teamsWhoPlayed.Clear();
			teamsWhoPlayedInHome = teamsWhoPlayedInHomeAux.Copy();
			teamsWhoPlayedAway = teamsWhoPlayedAwayAux.Copy();
			teamsWhoPlayedInHomeAux.Clear();
			teamsWhoPlayedAwayAux.Clear();
		}
		
		System.out.println();
		System.out.println("Segunda vuelta:");
		for (int i = 19; i<38; i++) //jornadas vuelta 2
		{
			System.out.println("\nJornada " + (i+1) + ":");
			for (int j = 0; j < 4; j++)
			{
				Match matchOfTheDay;
				DoubleLinkedCircularList<Match> suitedMatches = tournament2.Copy();
				DoubleLinkedCircularList<Match> suitedMatchesAux;
				switch(j)
				{
				case 0:
					System.out.println("Jueves:");	
					
					if (team1Sunday != null && team2Sunday != null)
					{
						final var team1S = team1Sunday;
						final var team2S = team2Sunday;
						
						suitedMatchesAux = suitedMatches.Copy();
						suitedMatches = suitedMatchesAux.FindAll(
							Match -> 
							!Match.getTeam1().getName().equals(team1S.getName()) &&
							!Match.getTeam1().getName().equals(team2S.getName()) &&
							!Match.getTeam2().getName().equals(team1S.getName()) &&
							!Match.getTeam2().getName().equals(team2S.getName())
						);
						
						if (suitedMatches.IsEmpty()) suitedMatches = suitedMatchesAux.Copy();
					}
					
					if (teamsWhoPlayedInHome.GetSize() > 0)
					{
						final var teamsHome = teamsWhoPlayedInHome.Copy();
						final var teamsAway = teamsWhoPlayedAway.Copy();
						
						suitedMatchesAux = suitedMatches.Copy();
						suitedMatches = suitedMatchesAux.FindAll(
							Match -> 
							teamsHome.IndexOf(Match.getTeam1()) == -1 &&
							teamsAway.IndexOf(Match.getTeam2()) == -1
						);
						
						if (suitedMatches.IsEmpty()) suitedMatches = suitedMatchesAux.Copy();
					}
					
					matchOfTheDay = suitedMatches.Get(rd.nextInt(suitedMatches.GetSize()));
					
					teamsWhoPlayed.Insert(matchOfTheDay.getTeam1(), matchOfTheDay.getTeam2());
					teamsWhoPlayedInHomeAux.Insert(matchOfTheDay.getTeam1());
					teamsWhoPlayedAwayAux.Insert(matchOfTheDay.getTeam2());
					tournament2.Remove(matchOfTheDay);
					System.out.println(matchOfTheDay);
					break;
				case 1:
					System.out.println("Viernes:");
					
					for (int k = 0; k < 4; k++)
					{
						suitedMatches = tournament2.Copy();
						if (teamsWhoPlayed.GetSize() > 0)
						{
							final var teamsPlayed = teamsWhoPlayed.Copy();
							
							suitedMatchesAux = suitedMatches.Copy();
							suitedMatches = suitedMatchesAux.FindAll(
								Match -> 
								teamsPlayed.IndexOf(Match.getTeam1()) == -1 &&
								teamsPlayed.IndexOf(Match.getTeam2()) == -1
							);
							
							if (suitedMatches.IsEmpty()) suitedMatches = suitedMatchesAux.Copy();
						}
						
						if (teamsWhoPlayedInHome.GetSize() > 0)
						{
							final var teamsHome = teamsWhoPlayedInHome.Copy();
							final var teamsAway = teamsWhoPlayedAway.Copy();
							
							suitedMatchesAux = suitedMatches.Copy();
							suitedMatches = suitedMatchesAux.FindAll(
								Match -> 
								teamsHome.IndexOf(Match.getTeam1()) == -1 &&
								teamsAway.IndexOf(Match.getTeam2()) == -1
							);
							
							if (suitedMatches.IsEmpty()) suitedMatches = suitedMatchesAux.Copy();
						}
						
						matchOfTheDay = suitedMatches.Get(rd.nextInt(suitedMatches.GetSize()));
						
						teamsWhoPlayed.Insert(matchOfTheDay.getTeam1(), matchOfTheDay.getTeam2());
						teamsWhoPlayedInHomeAux.Insert(matchOfTheDay.getTeam1());
						teamsWhoPlayedAwayAux.Insert(matchOfTheDay.getTeam2());
						tournament2.Remove(matchOfTheDay);
						System.out.println(matchOfTheDay);
					}
					break;
				case 2:
					System.out.println("Sabado:");
					
					for (int k = 0; k < 4; k++)
					{
						suitedMatches = tournament2.Copy();
						if (teamsWhoPlayed.GetSize() > 0)
						{
							final var teamsPlayed = teamsWhoPlayed.Copy();
							
							suitedMatchesAux = suitedMatches.Copy();
							suitedMatches = suitedMatchesAux.FindAll(
								Match -> 
								teamsPlayed.IndexOf(Match.getTeam1()) == -1 &&
								teamsPlayed.IndexOf(Match.getTeam2()) == -1
							);
							
							if (suitedMatches.IsEmpty()) suitedMatches = suitedMatchesAux.Copy();
						}
						
						if (teamsWhoPlayedInHome.GetSize() > 0)
						{
							final var teamsHome = teamsWhoPlayedInHome.Copy();
							final var teamsAway = teamsWhoPlayedAway.Copy();
							
							suitedMatchesAux = suitedMatches.Copy();
							suitedMatches = suitedMatchesAux.FindAll(
								Match -> 
								teamsHome.IndexOf(Match.getTeam1()) == -1 &&
								teamsAway.IndexOf(Match.getTeam2()) == -1
							);
							
							if (suitedMatches.IsEmpty()) suitedMatches = suitedMatchesAux.Copy();
						}
						
						matchOfTheDay = suitedMatches.Get(rd.nextInt(suitedMatches.GetSize()));
						
						teamsWhoPlayed.Insert(matchOfTheDay.getTeam1(), matchOfTheDay.getTeam2());
						teamsWhoPlayedInHomeAux.Insert(matchOfTheDay.getTeam1());
						teamsWhoPlayedAwayAux.Insert(matchOfTheDay.getTeam2());
						tournament2.Remove(matchOfTheDay);
						System.out.println(matchOfTheDay);
					}
					break;
				case 3:
					System.out.println("Domingo:");
					
					if (teamsWhoPlayed.GetSize() > 0)
					{
						final var teamsPlayed = teamsWhoPlayed.Copy();
						
						suitedMatchesAux = suitedMatches.Copy();
						suitedMatches = suitedMatchesAux.FindAll(
							Match -> 
							teamsPlayed.IndexOf(Match.getTeam1()) == -1 &&
							teamsPlayed.IndexOf(Match.getTeam2()) == -1
						);
						
						if (suitedMatches.IsEmpty()) suitedMatches = suitedMatchesAux.Copy();
					}
					
					if (teamsWhoPlayedInHome.GetSize() > 0)
					{
						final var teamsHome = teamsWhoPlayedInHome.Copy();
						final var teamsAway = teamsWhoPlayedAway.Copy();
						
						suitedMatchesAux = suitedMatches.Copy();
						suitedMatches = suitedMatchesAux.FindAll(
							Match -> 
							teamsHome.IndexOf(Match.getTeam1()) == -1 &&
							teamsAway.IndexOf(Match.getTeam2()) == -1
						);
						
						if (suitedMatches.IsEmpty()) suitedMatches = suitedMatchesAux.Copy();
					}
					
					matchOfTheDay = suitedMatches.Get(rd.nextInt(suitedMatches.GetSize()));
					
					team1Sunday = matchOfTheDay.getTeam1();
					team2Sunday = matchOfTheDay.getTeam2();
					teamsWhoPlayedInHomeAux.Insert(matchOfTheDay.getTeam1());
					teamsWhoPlayedAwayAux.Insert(matchOfTheDay.getTeam2());
					tournament2.Remove(matchOfTheDay);
					System.out.println(matchOfTheDay);
					break;
				default:
					break;
				}
			}
			teamsWhoPlayed.Clear();
			teamsWhoPlayedInHome = teamsWhoPlayedInHomeAux.Copy();
			teamsWhoPlayedAway = teamsWhoPlayedAwayAux.Copy();
			teamsWhoPlayedInHomeAux.Clear();
			teamsWhoPlayedAwayAux.Clear();
		}
		
		System.out.println();
		league.getPuntuations();
		league.getStatistics();
		league.clearPuntutations();
		System.out.println();
	}
	
	public static void listTeams()
	{
		for(Team t : league.getTeams().toList())
		{
			System.out.println(t.getName());
			System.out.println("Presupuesto: " + t.getBudget()/1000000 + "M (€)");
			System.out.println("Alineacion:");
			
			for(Player p : t.getPlayers())
			{
				System.out.println(p);
			}
			System.out.println();
		}
	}

	public static void handleMenu(String[] options, Consumer<Integer> actions) {
		// Adding exit option
		String[] optionsWithExit = new String[options.length + 1];
		optionsWithExit[options.length] = "Salir";
		for (int i = 0; i < options.length; i++) {
			optionsWithExit[i] = options[i];
		}

		// Entering the loop
		while (true) {
			int selection = InputManager.MultipleOptions("Selecciona una opción:", optionsWithExit);
			actions.accept(selection);
			if (selection == optionsWithExit.length) {
				break;
			}
		}
	}
}
