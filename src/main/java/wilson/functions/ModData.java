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
	public static Account getAccount(String name)
	{
		Account account = new Account(name);
		try {
			List<Account> accounts = ReadFile.getAccounts();
			account = (Account) accounts.stream().filter(x -> x.getName().equalsIgnoreCase(name)).toArray()[0];
		}
		catch(IOException e)
		{
			e.printStackTrace();
			addAccount();
			account = null;
		}
		catch (Exception e) {
			e.printStackTrace();
			addAccount();
			account = null;
		}
		return account;
	}
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
	public static void emptyFiles(boolean catagories)
	{
		Gson gson = new Gson();
		try {
			Writer accounts = new FileWriter(System.getProperty("user.dir") + "/accounts.json");
			Writer transactions = new FileWriter(System.getProperty("user.dir") + "/transactions.json");
			if(catagories)
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
