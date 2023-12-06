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
	 * @param writer = FileWriter object to write new category data to categories.json
	 * static method that extracts an array of categories from categories.json possessing culprit as part of their transactions,
	 * 	then depending on what the array contains either directs the user to manually enter a category to add it to, gives a list for
	 * 	user to select from, or just gives the only option before rewriting the categories.json file
	 */
	public static void addToCategory(String culprit)
	{
		Gson gson = new Gson();
		try
		{
			Category[] r = ReadFile.getCategoriesFromFile();
			Object[] a = Arrays.stream(r).filter(x -> x.getCulprits().contains(culprit)).toArray();
			Category[] tmp=Arrays.asList(a).toArray(new Category[a.length]);
			String category = "";
			if(tmp.length == 0)
			{
				category = Prompts.placeCategory(culprit);
			}
			else if(tmp.length == 1)
			{
				category = tmp[0].getName();
			}
			else
			{
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
	public static void createCategory(String name)
	{
		Gson gson = new Gson();
		try {
			Category[] cats = ReadFile.getCategoriesFromFile();
			String[] newCat = Prompts.newCategory(name);
			List<Category> upd = Arrays.asList(cats);
			upd.add(new Category(newCat[0], Double.parseDouble(newCat[1])));
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
	 * @param event = Transactions object representing the transaction to be added
	 * @param gson = Gson object for compiling data in json format
	 * @param writer = FileWriter object for writing data to transactions.json file
	 * @param events = list containing all Transactions in transactions.json
	 * extracts list from transactions.json and adds @param event input to list before rewriting list to transactions.json
	 */
	public static void addTransactions(Transactions event)
	{
		Gson gson = new Gson();
		try
		{
			List<Transactions> events = ReadFile.getTransactionsFromFile();
			Writer writer = new FileWriter(System.getProperty("user.dir") + "/transactions.json");
			events.add(event);
			gson.toJson(events, writer);
			writer.flush();
			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/*
	 * @param@return events = list of Transactions from transactions.json
	 * reads list of transactions and returns it or null if there is none
	 */
	public static List<Transactions> getTransactions()
	{
		List<Transactions> events = null;
		try
		{
			events = ReadFile.getTransactionsFromFile();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return events;
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
	 * gets list of accounts that is filtered by name to an array, the first element of which is returned
	 */
	public static Account getAccount(String name)
	{
		Account account = new Account(name);
		try {
			List<Account> accounts = ReadFile.getAccounts();
			account = (Account) accounts.stream().filter(x -> x.getName().equalsIgnoreCase(name)).toArray()[0];
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
		return account;
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
	 * @param category = FileWriter object to write data to catagories.json
	 * @param cat = empty array of Category to create blank catagories.json
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
				Writer category = new FileWriter(System.getProperty("user.dir") + "/catagories.json");
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
}
