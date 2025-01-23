package main;

import java.util.function.Consumer;

import entities.Team;
import structures.DoubleLinkedCircularList;
import utilities.InputManager;

public class MainNormal {
	public static FantasyLeague league;

	public static void main(String[] args) {
		league = new FantasyLeague(10);
		mainMenu();
	}

	public static void mainMenu() {
		String options[] = { "Insertar Equipo", "Simulación", "Resetear datos" };
		Consumer<Integer> actionHandler = (selection) -> {
			switch (selection) {
			case 1: // Insertar equipo
				addTeamPrompt();
				break;
			case 2: // Simulación
				gameSimulation();
				break;
			case 3:
				league.clearPuntutations();
			default: // Para evitar errores
				break;
			}
		};
		handleMenu(options, actionHandler);
	}

	public static void addTeamPrompt() {
		String name = InputManager.GetString("Introduce el nombre del equipo:");
		if (league.teamExists(name)) {
			System.out.println(name + " ya existe y no ha sido creado.");
			return;
		}
		;
		Team newTeam = new Team(name, 0, 0, 0);
		league.addTeam(newTeam);
	}

	public static void gameSimulation() {

		DoubleLinkedCircularList<Team> teams = league.getTeams().toList();
		DoubleLinkedCircularList<Team> auxTeams = league.getTeams().toList();
		int count = 1;

		/*
		 * for (int i = 0; i < teams.GetSize(); i++) { auxTeams.Remove(teams.Get(i));
		 * System.out.println("Jornada " + count); count++; for (int j = 0; j <
		 * auxTeams.GetSize(); j++) { int goalsTeam1 = FantasyLeague.generateGoals();
		 * int goalsTeam2 = FantasyLeague.generateGoals();
		 * System.out.println(teams.Get(i).getName() + " " + goalsTeam1 + " VS " +
		 * goalsTeam2 + " " + auxTeams.Get(j).getName()); // Goals
		 * league.addGoals(teams.Get(i).getName(), auxTeams.Get(j).getName(),
		 * goalsTeam1, goalsTeam2); // Points league.addPoints(teams.Get(i).getName(),
		 * auxTeams.Get(j).getName(), goalsTeam1, goalsTeam2); } }
		 */
		scheduller(teams);
		league.getPuntuations();
	}

	public static void scheduller(DoubleLinkedCircularList<Team> teams) {
		DoubleLinkedCircularList<Team> totalTeams = new DoubleLinkedCircularList<Team>();
		int numOfTeams = teams.GetSize();
		int k = 0;
		if (numOfTeams % 2 == 0) { // If the number of teams is even then games will be able to run parallel
			for (k = 0; k < numOfTeams - 1; k++)
				totalTeams.Insert(teams.Get(k + 1));
		} else { // If is odd on each round one team won't play a game in that round
			for (k = 0; k < numOfTeams - 1; k++)
				totalTeams.Insert(teams.Get(k + 1));
			totalTeams.Insert(null, numOfTeams);
		}
		
		int teamsSize = totalTeams.GetSize(); // it is even number
		int total = ((teamsSize + 1) - 1); // rounds needed to complete tournament
		int halfSize = (teamsSize + 1) / 2;
		int count = 0;
		for (int round = total - 1; round >= 0; round--) {
			System.out.println("---- JORNADA " + (++count));
			int teamIdx = round % teamsSize;
			int goalsTeam1ini = FantasyLeague.generateGoals();
			int goalsTeam2ini = FantasyLeague.generateGoals();
			if (!totalTeams.Get(teamIdx).equals(null)) {
				System.out.println(teams.Get(0).getName() + " " + goalsTeam1ini + " vs. " + goalsTeam2ini + " "
						+ totalTeams.Get(teamIdx).getName());
				// Goals
				league.addGoals(teams.Get(0).getName(), totalTeams.Get(teamIdx).getName(), goalsTeam1ini, goalsTeam2ini);
				// Points
				league.addPoints(teams.Get(0).getName(), totalTeams.Get(teamIdx).getName(), goalsTeam1ini, goalsTeam2ini);
			}
			for (int i = 1; i < halfSize; i++) {
				int goalsTeam1 = FantasyLeague.generateGoals();
				int goalsTeam2 = FantasyLeague.generateGoals();
				int firstTeam = (round + i) % teamsSize;
				int secondTeam = (round + teamsSize - i) % teamsSize;
				if (!totalTeams.Get(firstTeam).equals(null) && !totalTeams.Get(secondTeam).equals(null)) {
					System.out.println(totalTeams.Get(firstTeam).getName() + " " + goalsTeam1 + " vs. " + goalsTeam2
							+ " " + totalTeams.Get(secondTeam).getName());
					// Goals
					league.addGoals(teams.Get(firstTeam).getName(), totalTeams.Get(secondTeam).getName(), goalsTeam1,
							goalsTeam2);
					// Points
					league.addPoints(teams.Get(firstTeam).getName(), totalTeams.Get(secondTeam).getName(), goalsTeam1,
							goalsTeam2);
				}
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
