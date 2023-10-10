package wilson.models;

import java.util.ArrayList;
import java.util.List;

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
	public static Category getCategory(String name)
	{
		Category category = new Category(name);
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
