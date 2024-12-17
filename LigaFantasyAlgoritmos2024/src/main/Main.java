package main;

import java.util.Scanner;

import utilities.InputManager;

public class Main {

	public static void main(String[] args) {
		String options[] = {"Insertar Equipo", "Añadir puntos de equipo",
				"Añadir goles a favor", "Añadir goles en contra", "Ver puntuaciones", "Salir"};
		Scanner sc = new Scanner(System.in);
		System.out.println(Integer.toString(InputManager.MultipleOptions("", sc, options)));
	}

}
