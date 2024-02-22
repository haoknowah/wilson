package wilson.io;

import java.util.Scanner;

import wilson.models.Account;
import wilson.models.Category;

public class Prompts {
	public static Scanner in = new Scanner(System.in);
	/*
	 * @param@return category = user input string naming the category
	 */
	public static String placeCategory(String culprit) 
	{
		System.out.println("Where does " + culprit + " need to be categorized to?");
		String category = in.nextLine();
		return category;
	}
	/*
	 * @param budget = user input string representing the budget for the new category
	 * @param@return category = string array containing name and budget for new category
	 */
	public static String[] newCategory(String name)
	{
		System.out.print("Enter the budget: ");
		String budget = in.nextLine();
		String[] category = {name, budget};
		return category;
	}
	/*
	 * @param val = boolean determining whether or not an option has been selected
	 * @param o = user input string representing chosen option
	 * @param@return sel = String containing the option selected by the user
	 */
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
	/*
	 * @param name = user input string containing name for new account
	 * @param@return account = new account with name of @param name
	 */
	public static Account createAccount()
	{
		System.out.println("Enter an account name. ");
		String name = in.nextLine();
		Account account = new Account(name);
		return account;
	}
	/*
	 * @param ans = string representing the user's answer
	 * @return boolean of true or false 
	 */
	public static boolean yesno()
	{
		String ans = in.next();
		if(ans.equalsIgnoreCase("y"))
		{
			return true;
		}
		return false;
	}
}
