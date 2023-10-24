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
			if(tmp.length == 1)
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	public static Account getAccount(String name)
	{
		System.out.println("getting account");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			addAccount();
			account = null;
		}
		System.out.println("got account");
		return account;
	}
}
