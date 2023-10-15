package wilson.functions;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import wilson.io.Prompts;
import wilson.models.Category;

public class Save {
	public static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	public static void saveToCategory(String name, String categoryName) throws JsonIOException, IOException
	{
		Category cat = null;
		try
		{
			ModData.addToCategory(name, categoryName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			String tmp = Prompts.placeCategory(name);
			cat = Category.newCategory(tmp, name);
		}
		Writer writer = new FileWriter(System.getProperty("user.dir") + "/categories.json");
		Category[] catt = {new Category("tester", 100), new Category("testing", 100)};
		gson.toJson(catt, writer);
		writer.flush();
		writer.close();
	}
	public static void saveToAccount(List<Category> categories)
	{
		
	}
	public static void saveAccount()
	{
		
	}
}
