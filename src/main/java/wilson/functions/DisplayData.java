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
	private List<Transactions> categorize(List<Transactions> events)
	{
		events.stream().filter(x -> x.getCategory() != null);
		return events;
	}
	public void showBreakdown()
	{
		StripPdfData stripper = new StripPdfData();
		try {
			stripper.parseFile(this.pages);
			List<Transactions> events = stripper.getEvents();
			events = categorize(events);
			Map<String, Double> breakdown = new HashMap<String, Double>();
			List<Category> categories = Load.getCategories();
			for(Transactions event : events)
			{
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
