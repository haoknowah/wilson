package wilson.functions;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import wilson.io.Prompts;
import wilson.io.ReadFile;
import wilson.models.Account;
import wilson.models.Category;
import wilson.models.Transactions;

public class Save {
	public static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	/*
	 * @param@input name = String containing name of vendor to be added to a category
	 * @method addToCatagory(String name) = adds @param name to a Category
	 * @param tmp = temporary String containing name of new Category object
	 * @method placeCategory(String name) = gets Category object for @param name to be placed in
	 * calls @method addToCategory(name) to add the vendor name to Category object in categories.json, or creates a new Category object
	 * 	and adds that if an Exception is thrown
	 */
	public static void saveToCategory(String name) throws JsonIOException, IOException
	{
		Category cat = null;
		try
		{
			System.out.println("Adding to category");
			ModData.addToCategory(name);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			String tmp = Prompts.placeCategory(name);
			cat = new Category(tmp, name);
		}
		if(cat != null)
		{
			System.out.println("Cat == null");
			ModData.addToCategory(name);
		}
	}
	/*
	 * calls addAccount() method from ModData to create a new Account object for accounts.json
	 */
	public static void saveNewAccount()
	{
		ModData.addAccount();
	}
	/*
	 * @param@input account = Account object to be saved to accounts.json
	 * @param accounts = List of Account objects containing all of the Account objects in accounts.json
	 * @method updateAccount(Account account) = takes account object and updates it on accounts.json if it already exists on there
	 * @method addAccount(Account account) = adds a new Account object to accounts.json
	 * uses methods from ModData class to save the @param account to the accounts.json file
	 */
	public static void saveAccount(Account account)
	{
		try {
			List<Account> accounts = ReadFile.getAccounts();
			if(accounts.stream().anyMatch(x -> x.getName().equalsIgnoreCase(account.getName())))
			{
				ModData.updateAccount(account);
			}
			else
			{
				System.out.println("Account does not exist. Creating new account.");
				ModData.addAccount(account);
			}
		} catch (IOException e) {
			System.out.println("save failed");
			e.printStackTrace();
		}
	}
}
