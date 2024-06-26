package wilson.models;

import java.util.Date;

public class Transactions {

	/*
	 * @variable id = String containing unique identifier for the transaction
	 * @variable culprit = String containing name of vendor responsible for transaction
	 * @variable date = Date object representing date of transaction
	 * @variable amount = double containing the amount of money in the transaction
	 * @variable category = Category object that transaction might specifically belong to
	 */
	private String id;
	private String culprit;
	private Date date;
	private double amount;
	private Category category;
	public Transactions(String culprit, Date date, double amount)
	{
		this.culprit = culprit;
		this.date = date;
		this.amount = amount;
		this.category = Category.getCategory(culprit);
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCulprit() {
		return culprit;
	}
	public void setCulprit(String culprit) {
		this.culprit = culprit;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
}
