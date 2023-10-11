package wilson.functions;

import java.io.FileReader;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

public class ModData {
	public static void addToCategory(String culprit, String CategoryName)
	{
		JSONParser par = new JSONParser();
		try
		{
			Object obj = par.parse(new FileReader(System.getProperty("user.dir") + "/categories.json"));
			JSONObject json = (JSONObject) obj;
			if(json.has(CategoryName))
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
