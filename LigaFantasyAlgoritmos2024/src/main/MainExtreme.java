package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Consumer;

import entities.Team;
import structures.DoubleLinkedCircularList;
import utilities.InputManager;

public class MainExtreme {
	public static FantasyLeague league;

	public static void main(String[] args) {
		league = new FantasyLeague(10);
		mainMenu();
	}

	public static void mainMenu() {
		String options[] = { "Simulación", "Resetear datos" };
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

	public static void loadData() throws FileNotFoundException, IOException {
		String name = InputManager.GetString("Introduce la ruta del archivo CSV: (Ruta completa)");
		try (BufferedReader br = new BufferedReader(new FileReader(name))) {
			String line;
			while ((line = br.readLine()) != null) {
				Team team = new Team(line, 0, 0, 0);
				league.addTeam(team);
			}
		}

	}

	public static void gameSimulation() {

		try {
			loadData();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return;
		}

		DoubleLinkedCircularList<Team> teams = league.getTeams().toList();
		DoubleLinkedCircularList<Team> auxTeams = league.getTeams().toList();

		for (int i = 0; i < teams.GetSize(); i++) {
			auxTeams.Remove(teams.Get(i));
			for (int j = 0; j < auxTeams.GetSize(); j++) {
				int goalsTeam1 = FantasyLeague.generateGoals();
				int goalsTeam2 = FantasyLeague.generateGoals();
				System.out.println(teams.Get(i).getName() + " " + goalsTeam1 + " VS " + goalsTeam2 + " "
						+ auxTeams.Get(j).getName());
				// Goals
				league.addGoals(teams.Get(i).getName(), auxTeams.Get(j).getName(), goalsTeam1, goalsTeam2);
				// Points
				league.addPoints(teams.Get(i).getName(), auxTeams.Get(j).getName(), goalsTeam1, goalsTeam2);
			}
		}
		league.getPuntuations();
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
