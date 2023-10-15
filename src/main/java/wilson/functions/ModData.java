package wilson.functions;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;

import wilson.models.Category;

public class ModData {
	public static void addToCategory(String culprit, String CategoryName)
	{
		Gson gson = new Gson();
		try
		{
			Reader yub = new FileReader(System.getProperty("user.dir") + "/categories.json");
			Category[] r = gson.fromJson(yub, Category[].class);
			Arrays.stream(r).filter(x -> x.getName() == CategoryName);
			System.out.println(CategoryName);
			if(r[0].getCulprits().contains(CategoryName))
			{
				System.out.println("MEOW");
			}
			else
			{
				r[0].addCulprit(CategoryName);
				System.out.println("FUCK");
			}
			yub.close();
			System.out.println(r[0].getCulprits());
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
}
