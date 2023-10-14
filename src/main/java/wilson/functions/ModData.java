package wilson.functions;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;


import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;

import wilson.models.Category;

public class ModData {
	public static void addToCategory(String culprit, String CategoryName)
	{
		JSONParser par = new JSONParser();
		Gson gson = new Gson();
		try
		{
			Object obj = par.parse(new FileReader(System.getProperty("user.dir") + "/categories.json"));
			System.out.println(obj.toString());
			System.out.println(par.parse(new FileReader(System.getProperty("user.dir") + "/categories.json")));
			Reader yub = new FileReader(System.getProperty("user.dir") + "/categories.json");
			List<Category> cats = new ArrayList<Category>();
			Category r = gson.fromJson(yub, Category.class);
			if(r.getCulprits().contains(CategoryName))
			{
				System.out.println("MEOW");
			}
			else
			{
				System.out.println("FUCK");
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
