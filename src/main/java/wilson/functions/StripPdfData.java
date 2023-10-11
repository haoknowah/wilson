package wilson.functions;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import wilson.models.Transactions;

public class StripPdfData {

	private List<Transactions> events;
	private int year;
	public StripPdfData()
	{
		events = new ArrayList<>();
		year = 0;
	}
	public List<Transactions> getEvents()
	{
		return this.events;
	}
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