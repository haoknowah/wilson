package wilson.functions;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import wilson.models.Account;
import wilson.models.Category;

public class Load {
	public static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	/*
	 * @param@input account = String containing name of Account object being retrieved
	 * returns an Account object taken from accounts.json with name of @param account using ModData method getAccount(String account)
	 */
	public static Account getAccount(String account)
	{
		return ModData.getAccount(account);
	}
	/*
	 * returns a List of Category objects from categories.json retrieved using ModData method getCategories()
	 */
	public static List<Category> getCategories()
	{
		return ModData.getCategories();
	}
}
