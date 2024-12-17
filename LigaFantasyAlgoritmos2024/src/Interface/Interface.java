package Interface;

import java.util.ArrayList;
import java.util.Scanner;

public class Interface {

	private static Scanner sc = new Scanner(System.in);

	public static int selector(ArrayList<String> options) {
		for (String option : options) {
			System.out.println(options.indexOf(option) + ". " + option);
		}
		
		int selection = sc.nextInt();
		sc.nextLine();
		
		return selection;
	}
}
