package wilson.models;

import java.util.ArrayList;
import java.util.List;

public class Account {

	/*
	 * @variable balance = double containing balance of Account object
	 * @variable name = String containing name of Account object
	 * @variable events = List of Transactions objects associated with this Account object
	 * @variable categories = List of Category objects associated with this Account object
	 */
	private double balance;
	private String name;
	private List<Transactions> events;
	private List<Category> categories;
	public Account(double balance, String name)
	{
		this.balance = balance;
		this.name = name;
		events = new ArrayList<Transactions>();
		this.categories = new ArrayList<Category>();
	}
	public Account(String name)
	{
		this.name = name;
		this.balance = 0;
		this.events = new ArrayList<Transactions>();
		this.categories = new ArrayList<Category>();
	}
	public Account(double balance, String name, List<Transactions> events, List<Category> categories) {
		this.balance = balance;
		this.name = name;
		this.events = events;
		this.categories = categories;
	}

	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Transactions> getEvents() {
		return events;
	}
	public void setEvents(List<Transactions> events) {
		this.events = events;
	}
	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
}
