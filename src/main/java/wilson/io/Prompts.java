package wilson.io;

import java.util.Scanner;

import wilson.models.Account;
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
	public static String selectOption(Category[] options)
	{
		boolean val = false;
		String sel = null;
		while(val == false)
		{
			System.out.println("Select an option. ");
			String o = in.nextLine();
			for(Category c : options)
			{
				if(c.getName().equals(o))
				{
					sel = c.getName();
					val = true;
					break;
				}
			}
		}
		return sel;
	}
	public static Account createAccount()
	{
		System.out.println("Enter an account name. ");
		String name = in.nextLine();
		Account account = new Account(name);
		return account;
	}
}
