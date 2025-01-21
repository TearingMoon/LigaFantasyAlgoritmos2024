package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
		String options[] = {"Simulación", "Resetear datos" };
		Consumer<Integer> actionHandler = (selection) -> {
			switch (selection) {
			case 1:
				gameSimulation();
				break;
			case 2:
				league.clearPuntutations();
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
		List<Match> tournament1 = new ArrayList<Match>();
		List<Match> tournament2 = new ArrayList<Match>();
		
		for (int i = 0; i < teams.GetSize(); i++) {
			auxTeams.Remove(teams.Get(i));
			for (int j = 0; j < auxTeams.GetSize(); j++) {
				int[] goals = FantasyLeague.simulateMatchGoals(teams.Get(i), auxTeams.Get(j));
				int goalsTeam1 = goals[0];
				int goalsTeam2 = goals[1];
				Match t = new Match(teams.Get(i), auxTeams.Get(j), goalsTeam1, goalsTeam2);
				tournament1.add(t);
				// Goals
				league.addGoals(teams.Get(i).getName(), auxTeams.Get(j).getName(), goalsTeam1, goalsTeam2);
				// Points
				league.addPoints(teams.Get(i).getName(), auxTeams.Get(j).getName(), goalsTeam1, goalsTeam2);
			}
		}
		Collections.shuffle(tournament1);
		System.out.println("Primera Vuelta: ");
		for (Match match : tournament1) {			
			System.out.println(match);
		}
		
		auxTeams = league.getTeams().toList();
		for (int i = 0; i < teams.GetSize(); i++) {
			auxTeams.Remove(teams.Get(i));
			for (int j = 0; j < auxTeams.GetSize(); j++) {
				int[] goals = FantasyLeague.simulateMatchGoals(teams.Get(i), auxTeams.Get(j));
				int goalsTeam1 = goals[0];
				int goalsTeam2 = goals[1];
				Match t = new Match(teams.Get(i), auxTeams.Get(j), goalsTeam1, goalsTeam2);
				tournament2.add(t);
				// Goals
				league.addGoals(teams.Get(i).getName(), auxTeams.Get(j).getName(), goalsTeam1, goalsTeam2);
				// Points
				league.addPoints(teams.Get(i).getName(), auxTeams.Get(j).getName(), goalsTeam1, goalsTeam2);

			}
		}
		
		while (isSameSequence(tournament1, tournament2) || isInverse(tournament1, tournament2)) {
			Collections.shuffle(tournament2);
		}
		
		System.out.println("Segunda Vuelta: ");
		for (Match tour : tournament2) {			
			System.out.println(tour);
		}
		System.out.println();
		league.getPuntuations();
	}
	
	/**
	 * LO HA HECHO DAVID!!! (asistencia de escritura(- David: "Y revision de codigo") por emilly) :) <3
	 */
	public static boolean isSameSequence(List<Match> tour1, List<Match> tour2) {
		return tour1.equals(tour2);
	}
	
	/**
	 * LO HA HECHO DAVID!!! (asistencia de escritura(- David: "Y revision de codigo") por emilly) :) <3
	 */
	public static boolean isInverse(List<Match> tour1, List<Match> tour2) {
		List<Match> listInverseTour = new ArrayList<Match>(tour2);
		Collections.reverse(listInverseTour);
		return tour1.equals(listInverseTour);
	}
	
	/**
	 * LO HA HECHO DAVID!!! (asistencia de escritura(- David: "Y revision de codigo") por emilly) :) <3
	 */
	public static void generateSequence() {
		
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
