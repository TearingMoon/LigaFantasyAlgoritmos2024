package Main;

import java.util.ArrayList;
import java.util.Arrays;

import Interface.Interface;

public class Main {

	public static void main(String[] args) {
		ArrayList<String> options = new ArrayList<>(Arrays.asList("Insertar Equipo", "Añadir puntos de equipo",
				"Añadir goles a favor", "Añadir goles en contra", "Ver puntuaciones", "Salir"));
		System.out.println(options.get(Interface.selector(options)));
	}

}
