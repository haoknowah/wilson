package wilson.models;

import java.util.ArrayList;
import java.util.List;

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
