package wilson.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.google.gson.JsonIOException;

import wilson.functions.Load;
import wilson.functions.Save;
import wilson.io.Prompts;

public class Category {

	/*
	 * @param@variable name = String containing name for Category object
	 * @param@variable budget = double containing the budget allotted for Category object
	 * @param@variable culprits = List of Strings containing vendors in Category object
	 * @param@variable account = account that the category is attached to
	 */
	private String name;
	private double budget;
	private List<String> culprits;
	String account = null;
	/*
	 * @param@input name = String containing name of new Category object
	 * constructor building new Category object with @variable name of @param name
	 */
	private Category(String name)
	{
		this.name = name;
		culprits = new ArrayList<String>();
	}
	/*
	 * @param@input name = String containing @variable name for new Category object
	 * @param@input budget = double representing @variable budget of new Category object
	 * constructor creating new Category object with @variable name of @param name and @variable budget of @param budget
	 */
	public Category(String name, double budget)
	{
		this.name = name;
		this.budget = budget;
		this.culprits = new ArrayList<String>();
	}
	/*
	 * @param@input name = String containing @variable name for new Category object
	 * @param@input budget = double representing @variable budget of new Category object
	 * @param@input culprits = List of String objects to be made @variable culprits of new Category object
	 * you get the gist at this point, same as above but with the addition of @param culprits
	 */
	public Category(String name, double budget, List<String> culprits)
	{
		this.name = name; 
		this.budget = budget;
		this.culprits = culprits;
	}
	public Category(String name, String culprit)
	{
		this.name = name;
		this.culprits = new ArrayList<String>();
		this.culprits.add(culprit);
	}
	/*
	 * @param@input culprit = input String of vendor name that method is getting Category object for
	 * @param@return category = Category object containing @param name in @variable culprits
	 * @param categories = List of Category objects from categories.json retrieved with Load method getCategories()
	 * @param options = List of Category objects for if @param culprit is in multiple Category objects
	 * @param name = String containing @variable name of Category object chosen through Prompts method selectOption(Category[] 
	 * 	categories)
	 * @param cat = array of Strings containing information retrieved from Prompts method newCategory(String culprit)
	 * 	to create new Category object 
	 * obtains Category object of choice that contains @param culprit or creates a new Category object for it
	 */
	public static Category getCategory(String culprit)
	{
		System.out.println("Category.getCategory()");
		Category category = null;
		culprit = culprit.trim();
		List<Category> categories = new ArrayList<>(Load.getCategories());
		List<Category> options = new LinkedList<Category>();
		for(Category c : categories)
		{
			if(c.culprits.contains(culprit))
			{
				options.add(c);
			}
		}
		if(options.size() > 1)
		{
			System.out.println("Options:");
			options.stream().forEach(x -> System.out.println(x.getName()));
			String name = Prompts.selectOption(options.toArray(new Category[options.size()]));
			category = options.stream().filter(x -> x.getName() == name).findFirst().get();
		}
		else if(options.size() == 1)
		{
			category = options.get(0);
		}
		else
		{
			try
			{
				Save.saveToCategory(culprit);
			}
			catch(NumberFormatException e)
			{
				e.printStackTrace();
				category = new Category(culprit, 0);
			} catch (JsonIOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Category.getCategory completed");
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
	public void removeCulprit(String culprit)
	{
		culprits.remove(culprit);
	}
	public String getAccount()
	{
		if(account != null)
		{
			return account;
		}
		return "none";
	}
	public void addAccount(String account)
	{
		this.account = account;
	}
}
