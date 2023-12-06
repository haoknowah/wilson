package wilson.functions;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wilson.io.ReadFile;
import wilson.models.Category;
import wilson.models.Transactions;

public class DisplayData {
	/*
	 * @param file = file object to selected by user to be read
	 * @param pages = List of String arrays containing the data on each page of @param file
	 */
	private File file;
	private List<String[]> pages;
	public DisplayData()
	{
		this.file = ReadFile.findFile();
		try {
			pages = ReadFile.readPdfByPage(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * if you don't know what these few methods are then why are you even looking at this
	 */
	public File getFile()
	{
		return file;
	}
	public void setFile(File file)
	{
		this.file = file;
	}
	public void setFile()
	{
		this.file = ReadFile.findFile();
	}
	public void resetPages() throws IOException
	{
		pages = ReadFile.readPdfByPage(this.file);
	}
	public void setPages() throws IOException
	{
		this.pages = ReadFile.readPdfByPage();
	}
	public List<String[]> getPages()
	{
		return this.pages;
	}
	/*
	 * @param@input@return events = list of Transactions objects input by user to get only those with Category
	 * takes input list of events then returns a filtered version containing only Transactions objects with a Category variable
	 */
	private List<Transactions> categorize(List<Transactions> events)
	{
		events.stream().filter(x -> x.getCategory() != null);
		return events;
	}
	/*
	 * @param stripper = StripPdfData object for getting text data from @param pages
	 * @param events = list of Transactions objects containing events from extracted by @param stripper
	 * prints every transaction recorded and the amount and category associated with it
	 */
	public void showBreakdown()
	{
		StripPdfData stripper = new StripPdfData();
		try {
			stripper.parseFile(this.pages);
			List<Transactions> events = stripper.getEvents();
			events = categorize(events);
			for(Transactions event : events)
			{
				if(event.getCategory() == null)
				{
					ModData.addToCategory(event.getCulprit());
				}
				System.out.printf("%20s%15s%10f", event.getCulprit(), event.getCategory().getName(), event.getAmount());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void showFullBreakdown()
	{
		
	}
}
