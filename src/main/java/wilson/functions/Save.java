package wilson.functions;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import wilson.io.Prompts;
import wilson.models.Category;
import wilson.models.Transactions;

public class Save {
	public static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	public static void saveToCategory(String name) throws JsonIOException, IOException
	{
		Category cat = null;
		try
		{
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
			ModData.addToCategory(name);
		}
	}
	public static void saveToAccount(List<Category> categories)
	{
		
	}
	public static void saveAccount()
	{
		
	}
	public static void saveTransactions(List<Transactions> events)
	{
		events.stream().forEach(x->ModData.addTransactions(x));
	}
}
