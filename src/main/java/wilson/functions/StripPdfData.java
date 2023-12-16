package wilson.functions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import wilson.models.Transactions;

public class StripPdfData {

	/*
	 * @param events = List of Transactions objects stripped by StripPdfData object
	 * @param year = integer representing year of read account summary
	 */
	private List<Transactions> events;
	private int year;
	public StripPdfData()
	{
		events = new ArrayList<>();
		year = 0;
	}
	//you know what this is
	public List<Transactions> getEvents()
	{
		return this.events;
	}
	public int getYear()
	{
		return this.year;
	}
	/*
	 * @param@input doc = List of string arrays input by user representing lines of each page extracted from file
	 * @param full = List of strings from each page trimmed of excess data by @method trim(String[] page)
	 * @method trim = removes header information from each page
	 * @param lines = array of Strings representing each
	 * @method useable = determines if each line contains data for a Transactions object
	 * @method objectify =  turns each line into a Transaction object
	 * goes through file and filter out lines with irrelevant 
	 */
	public void parseFile(List<String[]> doc) throws IOException
	{
		year = Integer.parseInt(doc.get(0)[0].split("/")[2].substring(0, 2));
		List<String> full = new ArrayList<String>();
		for(String[] page : doc)
		{
			String[] lines = trim(page);
			Arrays.stream(lines).forEach(x -> full.add(x));
		}
		String[] lines = full.toArray(new String[full.size()]);
		System.out.println("parsing");
		List<String> filteredLines = new ArrayList<String>();
		for(int i = 0; i < lines.length; i++)
		{
			if(lines[i].contains("C#"))
			{
				lines[i] = lines[i].substring(0, lines[i].indexOf("C#"));
			}
			if((lines[i].trim().charAt(1) == '/' || lines[i].trim().charAt(2) == '/') && useable(lines[i]))
			{
				filteredLines.add(lines[i]);
			}
			else if(filteredLines.size() == 0)
			{
				//Empty for filter and testing purposes
				//System.out.println(lines[i]);
			}
			else if(lines[i].contains("As of"))
			{
				break;
			}
			else if(useable(lines[i]))
			{
				filteredLines.set(filteredLines.size() - 1, filteredLines.get(filteredLines.size()-1) + " | " + lines[i].trim());
			}
		}
		objectify(filteredLines);
	}
	/*
	 * @param@input filteredLines = List of Strings containing the lines filtered to contain only data for Transactions object
	 * @param dec = integer representing decimal place of amount in each Transactions line
	 * @param amount = String containing the amount as listed on @param filteredLines
	 * @param d = double representing @param amount
	 * @param monday = String representing the Date of the transaction
	 * @param date = Date object representing the date of the transaction
	 * @param id = String containing unique information identifying the transaction
	 * @param culprit = String containing info as to the vendor responsible for the transaction
	 * @param event = Transactions object representing the transaction performed in the line of @param filteredLines
	 * @return events = List of Transactions objects returned for all Transactions found in @param filteredLines
	 * takes each line from @param filteredLines and turns it into a Transactions object that is stored in @return events
	 */
	private List<Transactions> objectify(List<String> filteredLines)
	{
		int dec = filteredLines.get(0).indexOf(".");
		for(String entry : filteredLines)
		{
			//isolate the number and get rid of commas
			String amount = entry.substring(dec - 6, dec + 4);
			amount = amount.replaceAll(",", "");
			double d = 0;
			if(amount.contains("-"))
			{
				amount = amount.substring(0, amount.length()-1);
				d = Double.parseDouble(amount) * -1;
			}
			else
			{
				d = Double.parseDouble(amount);
			}
			//get date
			String monday = entry.substring(0, 5);
			@SuppressWarnings("deprecation")
			Date date = new Date(year, Integer.parseInt(monday.split("/")[0]), Integer.parseInt(monday.split("/")[1].trim()));
			//get id
			String id = null;
			if(!(entry.split("\\|").length > 1))
			{
				id = entry.split("\\|")[0].substring(3, dec - 6);
			}
			//get culprit
			String culprit = "";
			if(entry.split("\\|").length > 1)
			{
				culprit = entry.split("\\|")[1];
			}
			else
			{
				culprit = entry.substring(4, dec - 6);
			}
			Transactions event = new Transactions(culprit, date, d);
			if(id != null)
			{
				event.setId(id);
			}
			events.add(event);
		}
		return events;
	}
	/*
	 * @param@input@return page = array of Strings containing text on a page
	 * @param skipHead = List of Strings containing data for each page without the header information
	 * gets rid of header information from each page
	 */
	private String[] trim(String[] page)
	{
		for(int i = 0; i < page.length; i++)
		{
			page[i] = page[i].trim();
		}
		List<String> skipHead = Arrays.asList(page);
		skipHead = skipHead.subList(6, skipHead.size());
		page = skipHead.toArray(new String[skipHead.size()]);
		return page;
	}
	/*
	 * @param@input line = String containing line of text from document
	 * @param@return use = Boolean telling whether or not the @param line contains data for Transactions object
	 * determines whether or not @param line contains data for a Transactions object
	 */
	private Boolean useable(String line)
	{
		Boolean use = true;
		if(line.contains("REWARDS INFORMATION") || (line.split("\\.").length > 3 && line.split("/").length > 2) ||
				line.contains("Subtractions") || (line.contains("Date") && line.contains("Description")))
		{
			use = false;
		}
		return use;
	}
}