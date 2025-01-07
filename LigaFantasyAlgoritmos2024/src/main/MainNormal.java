package main;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;

import entities.Team;
import structures.HashTable;
import utilities.InputManager;

public class MainNormal {
	public static FantasyLeague league;

	public static void main(String[] args) {
		league = new FantasyLeague(10);
		mainMenu();
	}

	/**
	 * Controls the main menu of the app
	 */
	public static void mainMenu() {
		String options[] = { "Insertar Equipo", "Simulación" };
		Consumer<Integer> actionHandler = (selection) -> {
			switch (selection) {
			case 1: // Insertar equipo
				addTeamPrompt();
				break;
			case 2: // Simulación
				gameSimulation();
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

	public static void addPoints(String TeamName1, String TeamName2, int goalsTeam1, int goalsTeam2) {
		if(goalsTeam1 < goalsTeam2) {
			league.addPoints(TeamName2, 3);
		}else if(goalsTeam1 > goalsTeam2) {
			league.addPoints(TeamName1, 3);
		}else if (goalsTeam1 == goalsTeam2) {
			league.addPoints(TeamName1, 1);
			league.addPoints(TeamName2, 1);
		}
		
	}
	
	public static void addGoals(String TeamName1, String TeamName2, int goalsTeam1, int goalsTeam2) {
		league.addGoalsInFavor(TeamName1, goalsTeam1);
		//league.addGoalsInFavor(TeamName2, goalsTeam2);
		
		league.addGoalsAgainst(TeamName1, goalsTeam2);
		//league.addGoalsAgainst(TeamName2, goalsTeam1);
		
	}

	public static int generateGoals() {
		Random rd = new Random();
		return rd.nextInt(7) + 1;
	}

	public static void gameSimulation() {
		HashTable<Team> auxTeams = league.getTeams();
		for (int i = 0; i < league.getTeams().getSize(); i++) {
			if (league.getTeams().getTable()[i] != null)
				auxTeams.remove(auxTeams.keyOf(auxTeams.getTable()[i].getValue()));
			for (int j = 0; j < auxTeams.getSize(); j++) {
				if (league.getTeams().getTable()[i] != null && (auxTeams.getTable()[j] != null)) {
					if (auxTeams.getTable()[j].getRegistered() != false) {
						int goalsTeam1 = generateGoals();
						int goalsTeam2 = generateGoals();
						System.out.println(league.getTeams().getTable()[i].getValue().getName() + " " + goalsTeam1
								+ " VS " + goalsTeam2 + " " + auxTeams.getTable()[j].getValue().getName());
						//Goals
						addGoals(league.getTeams().getTable()[i].getValue().getName(),
								league.getTeams().get(auxTeams.getTable()[j].getValue().getName()).getName(),
								goalsTeam1, goalsTeam2);
						addPoints(league.getTeams().getTable()[i].getValue().getName(),
								league.getTeams().get(auxTeams.getTable()[j].getValue().getName()).getName(),
								goalsTeam1, goalsTeam2);
					}
				}

			}

		}
	}

	/**
	 * THis method handles Menu loops
	 * 
	 * @param options
	 * @param actions
	 */
	public static void handleMenu(String[] options, Consumer<Integer> actions) {
		String[] optionsWithoutExit = new String[options.length];
		for (int i = 0; i < options.length; i++) {
			optionsWithoutExit[i] = options[i];
		}
		// Enetering the loop
		while (true) {
			int selection = InputManager.MultipleOptions("Selecciona una opción:", optionsWithoutExit);
			actions.accept(selection);
			if (selection == optionsWithoutExit.length) {
				break;
			}
		}
	}
}
