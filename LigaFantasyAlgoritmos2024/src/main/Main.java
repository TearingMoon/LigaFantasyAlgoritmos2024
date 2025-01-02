package main;

import java.util.function.Consumer;
import java.util.function.Predicate;

import utilities.InputManager;
import entities.Team;

public class Main {

	public static FantasyLeague league;

	public static void main(String[] args) {
		league = new FantasyLeague(10);
		mainMenu();
	}

	/**
	 * Controls the main menu of the app
	 */
	public static void mainMenu() {
		String options[] = { "Insertar Equipo", "Añadir puntos de equipo", "Añadir goles a favor",
				"Añadir goles en contra", "Ver puntuaciones" };
		Consumer<Integer> actionHandler = (selection) -> {
			switch (selection) {
			case 1: // Insertar equipo
				addTeamPrompt();
				break;
			case 2: // Añadir puntos
				addPointsPrompt();
				break;
			case 3: // Añadir goles a favor
				addGoalsInFavorPrompt();
				break;
			case 4: // Añadir goles en contra
				addGoalsAgainstPrompt();
				break;
			case 5: // Ver puntuaciones
				showPuntuation();
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

	public static void addPointsPrompt() {
		// TODO: Check if it works correctly
		// Select team
		String name = InputManager.GetString("Introduce el nombre del equipo:");
		if (!league.teamExists(name)) {
			System.out.println(name + " no existe.");
			return;
		}
		// Select point ammount
		Predicate<Integer> pointLimit = i -> (i == 0 || i == 1 || i == 3);
		int points = InputManager.GetInt("Introduce los puntos a añadir: (0, 1 o 3)", pointLimit);
		league.addPoints(name, points);
	}

	public static void addGoalsInFavorPrompt() {
		// TODO: Check if it works correctly
		// Select team
		String name = InputManager.GetString("Introduce el nombre del equipo:");
		if (!league.teamExists(name)) {
			System.out.println(name + " no existe.");
			return;
		}
		// Select point ammount
		Predicate<Integer> goalLimit = i -> (i >= 0 && i <= 7);
		int goals = InputManager.GetInt("Introduce los goles a favor: (0-7)", goalLimit);
		league.addGoalsInFavor(name, goals);
	}

	public static void addGoalsAgainstPrompt() {
		// TODO: Check if it works correctly
		// Select team
		String name = InputManager.GetString("Introduce el nombre del equipo:");
		if (!league.teamExists(name)) {
			System.out.println(name + " no existe.");
			return;
		}
		// Select point ammount
		Predicate<Integer> goalLimit = i -> (i >= 0 && i <= 7);
		int goals = InputManager.GetInt("Introduce los goles en contra: (0-7)", goalLimit);
		league.addGoalsAgainst(name, goals);
	}

	public static void showPuntuation() {
		league.getPuntuations();
	}

	/**
	 * THis method handles Menu loops
	 * 
	 * @param options
	 * @param actions
	 */
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
