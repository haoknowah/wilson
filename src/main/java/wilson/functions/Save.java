package wilson.functions;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;

import wilson.models.Category;

public class Save {
	public static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	public static void saveToCategory(String name, String categoryName, Category meow) throws JsonIOException, IOException
	{
		Writer writer = new FileWriter(System.getProperty("user.dir") + "/categories.json");
		gson.toJson(meow, writer);
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
