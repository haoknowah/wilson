package wilson.functions;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import wilson.io.Prompts;
import wilson.io.ReadFile;
import wilson.models.Account;
import wilson.models.Category;
import wilson.models.Transactions;

public class ModData {
	/*
	 * @param culprit = input string representing the transaction to be added to a category
	 * @param gson = Gson object for extracting json data
	 * @param r = array of Category containing the categories from categories.json
	 * @param a = array of Object containing only Category with the input transaction name
	 * @param tmp = temporary array of Category containing array a to determine category selections
	 * @param category = string containing selected category name
	 * @param cat = final string of @param category for use in Arrays.stream(x).filter
	 * @param newCat = String array for creating new Category
	 * @param yub = new Category object to be added
	 * @param writer = FileWriter object to write new category data to categories.json
	 * static method that extracts an array of categories from categories.json possessing culprit as part of their transactions,
	 * 	then depending on what the array contains either directs the user to manually enter a category to add it to, gives a list for
	 * 	user to select from, or just gives the only option before rewriting the categories.json file
	 */
	public static void addToCategory(String culprit)
	{
		//need to change code so that this method is only called when a new culprit is added to a Category
		Gson gson = new Gson();
		try
		{
			Category[] r = ReadFile.getCategoriesFromFile();
			Object[] a = Arrays.stream(r).filter(x -> x.getCulprits().contains(culprit)).toArray();
			Category[] tmp=Arrays.asList(a).toArray(new Category[a.length]);
			String category = "";
			if(tmp.length == 0)
			{
				System.out.println("addToCategory no options found");
				category = Prompts.placeCategory(culprit);
				final String cat = category;
				if(Arrays.stream(r).filter(x -> x.getName().equals(cat)).toArray().length == 0)
				{
					String[] newCat = Prompts.newCategory(cat);
					Category yub = new Category(newCat[0], Double.parseDouble(newCat[1]));
					createCategory(yub);
					r = ReadFile.getCategoriesFromFile();
				}
			}
			else if(tmp.length == 1)
			{
				category = tmp[0].getName();
			}
			else
			{
				System.out.println("Options: ");
				for(Category c : tmp)
				{
					System.out.println(c.getName());
				}
				category = Prompts.selectOption(tmp);
			}
			for(Category c : r)
			{
				if(c.getName().equals(category))
				{
					c.addCulprit(culprit);
					System.out.println("Added to category");
				}
			}
			Writer writer = new FileWriter(System.getProperty("user.dir") + "/categories.json");
			gson.toJson(r, writer);
			writer.flush();
			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Problem in ModData.addToCategory");
		}
	}
	/*
	 * @param name = string containing name of new Category
	 * @param gson = Gson object for extracting json data
	 * @param cats = array of Category containing all categories read from categories.json
	 * @param newCat = array of strings representing the constructor input of the new Category
	 * @param upd = list representation of @param cats
	 * @param writer = FileWriter object for writing data back to categories.json
	 * retrieves array of categories from categories.json and creates a new updated list with a new Category that is then rewritten
	 * 	to categories.json
	 */
	public static void createCategory(Category cat)
	{
		System.out.println("Starting create Category");
		Gson gson = new Gson();
		try {
			Category[] cats = ReadFile.getCategoriesFromFile();
			List<Category> upd = new ArrayList<>(Arrays.asList(cats));
			upd.add(cat);
			Writer writer = new FileWriter(System.getProperty("user.dir") + "/categories.json");
			cats = upd.toArray(new Category[upd.size()]);
			gson.toJson(cats, writer);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * @param@return categories = array of categories from categories.json
	 * returns a list of all categories in categories.json
	 */
	public static List<Category> getCategories()
	{
		try
		{
			Category[] catagories = ReadFile.getCategoriesFromFile();
			return Arrays.asList(catagories);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	/*
	 * @param account = Account object holding data for new Account
	 * @param gson = Gson object for compiling data in json format
	 * @param writer = FileWriter object for writing data to accounts.json
	 * @param accounts = list of Account containing data from accounts.json
	 * @param nw = array of Account representing data of @param accounts
	 * retrieves list of accounts and adds new account to list before converting to array and writing back to accounts.json
	 */
	public static void addAccount()
	{
		Account account = Prompts.createAccount();
		Gson gson = new Gson();
		try
		{
			Writer writer = new FileWriter(System.getProperty("user.dir") + "/accounts.json");
			List<Account> accounts = ReadFile.getAccounts();
			accounts.add(account);
			System.out.println("A");
			Account[] nw = accounts.toArray(new Account[accounts.size()]);
			gson.toJson(nw, writer);
			writer.flush();
			writer.close();
		}
		catch(FileNotFoundException e)
		{
			/*
			 * @param writer = FileWriter object for writing data to accounts.json
			 * @param accounts = array of Account containing single new account
			 * if file was not found, creates new accounts.json file and adds account to it
			 */
			System.out.println("File not found");
			try {
				Writer writer = new FileWriter(System.getProperty("user.dir") + "/accounts.json");
				Account[] accounts = {account};
				gson.toJson(accounts, writer);
				writer.flush();
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			/*
			 * @param writer = FileWriter object for writing data to accounts.json
			 * @param accounts = array of Account containing new account data
			 * creates and writes new account to new accounts.json file
			 */
			try {
				Writer writer = new FileWriter(System.getProperty("user.dir") + "/accounts.json");
				Account[] accounts = {account};
				gson.toJson(accounts, writer);
				writer.flush();
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	/*
	 * @param account = Account object holding data for new Account
	 * @param gson = Gson object for compiling data in json format
	 * @param writer = FileWriter object for writing data to accounts.json
	 * @param accounts = list of Account containing data from accounts.json
	 * @param nw = array of Account representing data of @param accounts
	 * retrieves list of accounts and adds new account to list before converting to array and writing back to accounts.json,
	 * 	overload of previous method in case user passes in @param account object
	 */
	public static void addAccount(Account account)
	{
		Gson gson = new Gson();
		try
		{
			Writer writer = new FileWriter(System.getProperty("user.dir") + "/accounts.json");
			List<Account> accounts = ReadFile.getAccounts();
			accounts.add(account);
			Account[] nw = accounts.toArray(new Account[accounts.size()]);
			gson.toJson(nw, writer);
			writer.flush();
			writer.close();
		}
		catch(FileNotFoundException e)
		{
			try {
				Writer writer = new FileWriter(System.getProperty("user.dir") + "/accounts.json");
				Account[] accounts = {account};
				gson.toJson(accounts, writer);
				writer.flush();
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			try {
				Writer writer = new FileWriter(System.getProperty("user.dir") + "/accounts.json");
				Account[] accounts = {account};
				gson.toJson(accounts, writer);
				writer.flush();
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	/*
	 * @param name = string containing name of Account being searched for
	 * @param@return account = Account object containing data for searched account of @param name
	 * @param accounts = list of Account containing all accounts from accounts.json
	 * @param yub = boolean determining if a new account is getting created
	 * gets list of accounts that is filtered by name to an array, the first element of which is returned
	 */
	public static Account getAccount(String name)
	{
		Account account = new Account(name);
		try {
			List<Account> accounts = ReadFile.getAccounts();
			account = (Account) accounts.stream().filter(x -> x.getName().equalsIgnoreCase(name)).toArray()[0];
			if(account.getName() == name)
			{
				System.out.println("MEOW");
			}
		}
		/*
		 * creates new accounts.json if no accounts.json file is found
		 */
		catch(IOException e)
		{
			e.printStackTrace();
			addAccount();
			account = null;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Account does not exist. Create a new account? Y/N");
			boolean yub = Prompts.yesno();
			if(yub)
			{
				account = Prompts.createAccount();
				addAccount(account);
			}
			else
			{
				account = null;
			}
		}
		return account;
	}
	/*
	 * @param name = name of account to be removed
	 * @param accounts = list of accounts from accounts.json
	 * @param accFilt = list of accounts that have name of @param name
	 * removes account if it exists or creates it if it doesn't
	 */
	public static void removeAccount(String name)
	{
		try
		{
			List<Account> accounts = ReadFile.getAccounts();
			List<Account> accFilt = accounts.stream().filter(x -> x.getName().equalsIgnoreCase(name)).toList();
			if(accFilt.size() > 0)
			{
				for(Account yub : accFilt)
				{
					accounts.remove(yub);
				}
				System.out.println("Account(s) removed");
			}
			else
			{
				addAccount(new Account(name));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/*
	 * @input@param account = input Account object to be updated
	 * @param accounts = list of Account containing all Account data from and to accounts.json
	 * @param old = Account object with same name as @input account
	 * @param ind = integer of the @param accounts index containing @param old
	 * @param writer = FileWriter object to write updated @param accounts to accounts.json
	 * @param gson = Gson object for writing data to json file
	 * retrieves a list of accounts from accounts.json and replaces the account sharing the name of the input with the input account
	 * 	and rewrites it all back to accounts.json
	 */
	public static void updateAccount(Account account)
	{
		List<Account> accounts;
		try {
			accounts = ReadFile.getAccounts();
			Account old = (Account) accounts.stream().filter(x -> x.getName().equalsIgnoreCase(account.getName())).toArray()[0];
			int ind = accounts.indexOf(old);
			accounts.remove(ind);
			accounts.add(ind, account);
			Writer writer = new FileWriter(System.getProperty("user.dir") + "/accounts.json");
			Gson gson = new Gson();
			gson.toJson(accounts, writer);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to save");
		}
	}
	/*
	 * @param@input categories = boolean representing whether or not categories.json needs to be emptied
	 * @param gson = Gson object for writing data to json file
	 * @param accounts = FileWriter object to write data to accounts.json
	 * @param transactions = FileWriter object to write data to transactions.json
	 * @param category = FileWriter object to write data to categories.json
	 * @param cat = empty array of Category to create blank categories.json
	 * @param acc = empty array of Account to create blank accounts.json
	 * @param tran = empty array of Transaction to create blank transactions.json
	 * creates empty arrays or Account and Transaction and/or Category and writes them to respective json file to empty it of data
	 */
	public static void emptyFiles(boolean categories)
	{
		Gson gson = new Gson();
		try {
			Writer accounts = new FileWriter(System.getProperty("user.dir") + "/accounts.json");
			Writer transactions = new FileWriter(System.getProperty("user.dir") + "/transactions.json");
			if(categories)
			{
				Writer category = new FileWriter(System.getProperty("user.dir") + "/categories.json");
				Category[] cat = {};
				gson.toJson(cat, category);
				category.flush();
				category.close();
			}
			Account[] acc = {};
			Transactions[] tran = {};
			gson.toJson(acc, accounts);
			gson.toJson(tran, transactions);
			accounts.flush();
			transactions.flush();
			accounts.close();
			transactions.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 /*
	  * @param gson= Gson object for writing data to json file
	  * @param category = Writer object for to write data to categories.json
	  * @param catagories = array containing data from categories.json
	  * removes a category from categories.json
	  */
	public static void removeCategory(String name)
	{
		Gson gson = new Gson();
		try
		{
			Writer category = new FileWriter(System.getProperty("user.dir") + "/categories.json");
			Category[] catagories = ReadFile.getCategoriesFromFile();
			catagories = Arrays.stream(catagories).filter(x -> x.getName() != name).toList().toArray(new Category[catagories.length - 1]);
			gson.toJson(catagories, category);
			category.flush();
			category.close();
		}
		catch(IOException e)
		{
			System.out.println("Category not found");
			e.printStackTrace();
		}
	}
}
