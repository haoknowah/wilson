package wilson.functions;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wilson.io.ReadFile;
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
	 * same as above but allows user to input the file into the constructor instead of having to find it again if they already have it
	 */
	public DisplayData(File file)
	{
		this.file = file;
		try
		{
			pages = ReadFile.readPdfByPage(file);
		}
		catch(IOException e)
		{
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
				System.out.printf("%-30s%-15s%10.2f", event.getCulprit().strip(), event.getCategory().getName().strip(), event.getAmount());
				System.out.print("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * @param stripper = StripPdfData object for getting data from @param pages
	 * @param categories = Map object for displaying categories and their usage
	 * @param events = 
	 */
	public void showBreakdownByCategory()
	{
		StripPdfData stripper = new StripPdfData();
		//change from map to set and change double to double[] so that it can contain budget as well
		Map<String, double[]> categories = new HashMap<String, double[]>();
		try
		{
			stripper.parseFile(this.pages);
			List<Transactions> events = stripper.getEvents();
			events = categorize(events);
			for(Transactions event : events)
			{
				if(event.getCategory() == null)
				{
					ModData.addToCategory(event.getCulprit());
				}
				if(categories.containsKey(event.getCategory().getName()) == false)
				{
					double[] yub = {0.0, 0.0};
					categories.put(event.getCategory().getName(), yub);
				}
				String cat = event.getCategory().getName();
				double[] meow = categories.get(cat);
				meow[0] = meow[0] + event.getCategory().getBudget();
				meow[1] = meow[1] + event.getAmount();
				categories.put(cat, meow);
			}
			System.out.printf("%-15s%10s%10s\n", "Category", "Budget", "Total");
			categories.entrySet().stream().forEach(x -> System.out.printf("%-15s%10.2f%10.2f\n", x.getKey(), x.getValue()[0], 
					x.getValue()[1]));
			double[] temp = categories.values().stream().reduce((a, b) -> new double[] {(double) a[0] + (double) b[0], 
					(double) a[1] + (double) b[1]}).get();
			System.out.printf("%-15s%10.2f%10.2f\n", "Net Total: ", temp[0], temp[1]);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
