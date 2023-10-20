package wilson.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.google.gson.Gson;

import wilson.models.Account;
import wilson.models.Category;
import wilson.models.Transactions;

public class ReadFile {
	public ReadFile()
	{
		System.out.println("Meow");
	}
	public static String readPdf() throws IOException
	{
		File file = new File("E:/Documents/ProjectWorkspace/wilson/Statement.pdf");
		PDDocument doc = Loader.loadPDF(file);
		PDFTextStripper stripper = new PDFTextStripper();
		String text = stripper.getText(doc);
		doc.close();
		return text;
	}
	public static List<String[]> readPdfByPage(String loc) throws IOException
	{
		File file = new File("E:/Documents/ProjectWorkspace/wilson/Statement.pdf");
		PDDocument doc = Loader.loadPDF(file);
		List<String[]> pages = new ArrayList<String[]>();
		for(int i = 0; i < doc.getNumberOfPages(); i++)
		{
			PDFTextStripper stripper = new PDFTextStripper();
			PDDocument x = new PDDocument();
			x.addPage(doc.getPage(i));
			pages.add(stripper.getText(x).split("\n"));
		}
		doc.close();
		return pages;
	}
	public void readExcel()
	{
		
	}
	public static Category[] getCategoriesFromFile() throws IOException
	{
		Category[] categories = null;
		Reader file = new FileReader(System.getProperty("user.dir") + "/categories.json");
		Gson gson = new Gson();
		categories = gson.fromJson(file, Category[].class);
		Arrays.stream(categories).forEach(x->System.out.println(x));
		file.close();
		return categories;
	}
	public static List<Transactions> getTransactionsFromFile() throws IOException
	{
		List<Transactions> events = new ArrayList<Transactions>();
		Reader reader = new FileReader(System.getProperty("user.dir") + "/transactions.json");
		Gson gson = new Gson();
		events = Arrays.asList(gson.fromJson(reader, Transactions[].class));
		reader.close();
		return events;
	}
	public static List<Account> getAccounts() throws IOException
	{
		List<Account> accounts= new ArrayList<Account>();
		Reader reader = new FileReader(System.getProperty("user.dir") + "/accounts.json");
		Gson gson = new Gson();
		accounts = Arrays.asList(gson.fromJson(reader, Account[].class));
		reader.close();
		return accounts;
	}
}
