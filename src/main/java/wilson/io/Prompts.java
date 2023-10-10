package wilson.io;

import java.util.Scanner;

import wilson.models.Category;

public class Prompts {
	public static Scanner in = new Scanner(System.in);
	public static String placeCategory(String culprit) 
	{
		System.out.println("Where does " + culprit + " need to be categorized to?");
		String category = in.nextLine();
		return category;
	}
	public static String[] newCategory(String name)
	{
		System.out.print("Enter the budget: ");
		String budget = in.nextLine();
		String[] category = {name, budget};
		return category;
	}
}
