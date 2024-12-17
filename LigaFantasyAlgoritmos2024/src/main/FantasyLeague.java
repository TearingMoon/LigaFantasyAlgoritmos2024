package main;
import java.util.Scanner;
import structures.HashTable;
import structures.HashTable.exploration;
import entities.Team;


public class FantasyLeague {

	int numberOfTeams;
	Scanner sc = new Scanner(System.in);
	HashTable<Team> table = new HashTable<Team>(0, exploration.LINEAL);
	
	public void addPoints(String identifier) {
		int points;
		if(false) return; //TODO: metodo hash de búsqueda
		else {
			do {
				System.out.println("Cuantos puntos ha conseguido tu equipo: ");
				 points = sc.nextInt();
				
			}while(points !=0 || points !=1 || points !=3);
			
			
		
				
			
		}
	}
}
