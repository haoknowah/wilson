package wilson.functions;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import wilson.models.Account;
import wilson.models.Category;
import wilson.models.Transactions;

public class Load {
	public static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	public static List<Transactions> getTransactions()
	{
		return ModData.getTransactions();
	}
	public static Account getAccount(String account)
	{
		return ModData.getAccount(account);
	}
	public static List<Category> getCategories()
	{
		return ModData.getCategories();
	}
}
