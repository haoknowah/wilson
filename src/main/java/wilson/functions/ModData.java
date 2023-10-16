package wilson.functions;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;



import com.google.gson.Gson;

import wilson.io.Prompts;
import wilson.models.Category;

public class ModData {
	public static void addToCategory(String culprit)
	{
		Gson gson = new Gson();
		try
		{
			Reader yub = new FileReader(System.getProperty("user.dir") + "/categories.json");
			Category[] r = gson.fromJson(yub, Category[].class);
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
			yub.close();
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
			Reader reader = new FileReader(System.getProperty("user.dir") + "/categories.json");
			Category[] cats = gson.fromJson(reader, Category[].class);
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
}
