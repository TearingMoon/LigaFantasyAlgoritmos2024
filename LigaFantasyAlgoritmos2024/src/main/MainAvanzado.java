package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import entities.Team;
import entities.Match;
import structures.DoubleLinkedCircularList;
import utilities.InputManager;

public class MainAvanzado {
	public static FantasyLeague league;

	public static void main(String[] args) {
		league = new FantasyLeague(10);
		mainMenu();
	}

	public static void mainMenu() {
		String options[] = {"Insertar Equipo", "Simulación", "Resetear datos" };
		Consumer<Integer> actionHandler = (selection) -> {
			switch (selection) {
			case 1:
				addTeamPrompt();
				break;
			case 2:
				gameSimulation();
				break;
			case 3:
				league.clearPuntutations();
				break;
			default: // Para evitar errores
				break;
			}
		};
		handleMenu(options, actionHandler);
	}

	public static void addTeamPrompt() {
		// TODO: Check if it works correctly
		String name = InputManager.GetString("Introduce el nombre del equipo:");
		if (league.teamExists(name)) {
			System.out.println(name + " ya existe y no ha sido creado.");
			return;
		}
		;
		Team newTeam = new Team(name, 0, 0, 0);
		league.addTeam(newTeam);
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
				int goalsTeam1 = FantasyLeague.generateGoals();
				int goalsTeam2 = FantasyLeague.generateGoals();
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
				int goalsTeam1 = FantasyLeague.generateGoals();
				int goalsTeam2 = FantasyLeague.generateGoals();
				Match t = new Match(auxTeams.Get(j), teams.Get(i), goalsTeam1, goalsTeam2);
				tournament2.add(t);
				// Goals
				league.addGoals(auxTeams.Get(j).getName(), teams.Get(i).getName(), goalsTeam1, goalsTeam2);
				// Points
				league.addPoints(auxTeams.Get(j).getName(), teams.Get(i).getName(), goalsTeam1, goalsTeam2);

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
		System.out.println();
		league.getStatistics();
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

	public static void handleMenu(String[] options, Consumer<Integer> actions) {
		// Adding exit option
		String[] optionsWithExit = new String[options.length + 1];
		optionsWithExit[options.length] = "Salir";
		for (int i = 0; i < options.length; i++) {
			optionsWithExit[i] = options[i];
		}

		// Enetering the loop
		while (true) {
			int selection = InputManager.MultipleOptions("Selecciona una opción:", optionsWithExit);
			actions.accept(selection);
			if (selection == optionsWithExit.length) {
				break;
			}
		}
	}
}
