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
			cat = Category.newCategory(tmp, name);
		}
		if(cat != null)
		{
			System.out.println("Cat == null");
			ModData.addToCategory(name);
		}
	}
	public static void saveNewAccount()
	{
		ModData.addAccount();
	}
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
	public static void saveTransactions(List<Transactions> events)
	{
		events.stream().forEach(x->ModData.addTransactions(x));
	}
}
