package wilson.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import wilson.functions.Load;
import wilson.io.Prompts;

public class Category {

	private String name;
	private double budget;
	private List<String> culprits;
	private Category(String name)
	{
		this.name = name;
		culprits = new ArrayList<String>();
	}
	public Category(String name, double budget)
	{
		this.name = name;
		this.budget = budget;
		this.culprits = new ArrayList<String>();
	}
	public Category(String name, double budget, List<String> culprits)
	{
		this.name = name; 
		this.budget = budget;
		this.culprits = culprits;
	}
	public static Category getCategory(String culprit)
	{
		Category category = null;
		List<Category> categories = Load.getCategories();
		List<Category> options = new LinkedList<Category>();
		for(Category c : categories)
		{
			if(c.culprits.contains(culprit))
			{
				options.add(c);
			}
		}
		System.out.println("ABC");
		if(options.size() > 1)
		{
			String name = Prompts.selectOption(options.toArray(new Category[options.size()]));
			category = options.stream().filter(x -> x.getName() == name).findFirst().get();
		}
		else
		{
			try
			{
				System.out.println("options.size() == 1");
				String[] cat = Prompts.newCategory(culprit);
				category = new Category(cat[0], Double.parseDouble(cat[1]));

			}
			catch(NumberFormatException e)
			{
				e.printStackTrace();
				category = new Category(culprit, 0);
			}
		}
		return category;
	}
	public static Category newCategory(String name, String culprit)
	{
		String[] cat = Prompts.newCategory(name);
		Category category = new Category(cat[0], Double.parseDouble(cat[1]));
		category.addCulprit(culprit);
		return category;
	}
	public String getName() {
		return name;
	}
	
	public double getBudget() {
		return budget;
	}
	
	public List<String> getCulprits() {
		return culprits;
	}
	public void addCulprit(String culprit)
	{
		culprits.add(culprit);
	}
}
