package main;

import java.util.function.Consumer;

import utilities.InputManager;

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
		//TODO: Implement interface
		
		//Prompt name
		
		//Check if exists
		
		//Add if it doesnt exist
	}

	public static void addPointsPrompt() {
		//TODO: Implement interface
		
		//Select team
		
		//Select point ammount
	}

	public static void addGoalsInFavorPrompt() {
		//TODO: Implement interface
		
		//Select team;
		
		//Select goal ammount;
	}

	public static void addGoalsAgainstPrompt() {
		//TODO: Implement interface
		
		//Select team
		
		//Select goal ammount
	}

	public static void showPuntuation() {
		//TODO: Implement interface
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
