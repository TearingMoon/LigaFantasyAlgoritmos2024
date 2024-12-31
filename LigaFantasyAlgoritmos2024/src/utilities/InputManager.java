package utilities;

import java.util.Scanner;
import java.util.function.Predicate;

public class InputManager {
	private static Scanner input = new Scanner(System.in);

	public static int MultipleOptions(String message, String options[]) {
		System.out.println(message + "\n");
		for (int i = 0; i < options.length; i++)
			System.out.println((i + 1) + ". " + options[i]);
		System.out.println("\n");
		int returnInt = 0;
		do
			returnInt = input.nextInt();
		while (returnInt < 1 || returnInt > options.length);
		System.out.println("\n");
		return returnInt;
	}

	public static int GetInt(String message, Predicate<Integer> condition) // Example: InputManager.GetInt("", input,
																			// integer -> integer > 1 && integer <= 10)
	{
		int returnVal = 0;
		System.out.println(message);
		System.out.println("\n");
		do
			returnVal = input.nextInt();
		while (!condition.test(returnVal));
		System.out.println("\n");
		return returnVal;
	}

	public static float GetFloat(String message, Predicate<Float> condition) // Example: InputManager.GetFloat("",
																				// input, Float -> float > 1f && float
																				// <= 10f)
	{
		float returnVal = 0;
		System.out.println(message);
		System.out.println("\n");
		do
			returnVal = input.nextInt();
		while (!condition.test(returnVal));
		System.out.println("\n");
		return returnVal;
	}
}
