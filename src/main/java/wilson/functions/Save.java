package wilson.functions;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import wilson.models.Category;

public class Save {
	public static Gson gson = new Gson();
	public static void saveToCategory(String name, String categoryName) throws JsonIOException, IOException
	{
		System.out.println(System.getProperty("user.dir"));
		gson.toJson(name, new FileWriter(System.getProperty("user.dir") + "/categories.json"));
	}
	public static void saveToAccount(List<Category> categories)
	{
		
	}
	public static void saveAccount()
	{
		
	}
}
