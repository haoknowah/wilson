package wilson.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;

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
		System.out.println("Meow.");
	}
	/*
	 * @param file = file being read
	 * @param doc = PDDocument object containing the loaded pdf file
	 * @param stripper = PDFTextStripper object to get String of data from doc
	 * @param text = String containing all info from file
	 * Takes the file found with findFile() method and loads it to PDDocument object with Loader.loadPDF() and returns the text from the 
	 * file after it is extracted using PDFTextStripper object
	 */
	public static String readPdf() throws IOException
	{
		File file = findFile();
		PDDocument doc = Loader.loadPDF(file);
		PDFTextStripper stripper = new PDFTextStripper();
		String text = stripper.getText(doc);
		doc.close();
		return text;
	}
	/*
	 * @param file = file being read
	 * @param doc = PDDocument object containing the loaded pdf file
	 * @param @return pages = ArrayList containing all the text per page
	 * @param stripper = PDFTextStripper object to get String of data from each page in doc
	 * @param x = PDDocument object used to contain String data from doc
	 * Takes selected file and adds each individual page into an arraylist as a string before returning it
	 */
	public static List<String[]> readPdfByPage() throws IOException
	{
		File file = findFile();
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
	/*
	 * @input = file = file to be input and read by the method
	 * @param doc = PDDocument containing data from file
	 * @param @return pages = ArrayList containing all the text per page
	 * @param stripper = PDFTextStripper object to get String of data from each page in doc
	 * @param x = PDDocument object used to contain String data from doc
	 * Takes input file and adds each individual page into an arraylist as a string before returning it
	 */
	public static List<String[]> readPdfByPage(File file) throws IOException
	{
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
	/*
	 * @param@return categories = array of categories being returned by the method
	 * @param file = json file containing all of the categories
	 * @param gson = Gson object to extract Json data from file
	 * returns an array containing all the different categories from categories.json
	 */
	public static Category[] getCategoriesFromFile() throws IOException
	{
		Category[] categories = null;
		Reader file = new FileReader(System.getProperty("user.dir") + "/categories.json");
		Gson gson = new Gson();
		categories = gson.fromJson(file, Category[].class);
		file.close();
		return categories;
	}
	/*
	 * @param@return events = List object containing all of the Transactions that were stored
	 * @param reader = Reader for file containing transactions
	 * @param gson = Gson object to extract json data from reader
	 * returns a list of transactions that are stored in the transactions.json file
	 */
	public static List<Transactions> getTransactionsFromFile() throws IOException
	{
		List<Transactions> events = new ArrayList<Transactions>();
		Reader reader = new FileReader(System.getProperty("user.dir") + "/transactions.json");
		Gson gson = new Gson();
		events = Arrays.asList(gson.fromJson(reader, Transactions[].class));
		reader.close();
		return events;
	}
	/*
	 * @param@return accounts = list object to contain all of the Accounts
	 * @param reader = Reader for file containing Account data 
	 * @param gson = Gson object to extract json data
	 * returns a list containing all of the accounts stored in accounts.json
	 */
	public static List<Account> getAccounts() throws IOException
	{
		List<Account> accounts= new ArrayList<Account>();
		Reader reader = new FileReader(System.getProperty("user.dir") + "/accounts.json");
		Gson gson = new Gson();
		accounts = Arrays.asList(gson.fromJson(reader, Account[].class));
		reader.close();
		return accounts;
	}
	/*
	 * @param find = JFileChooser object to allow user to navigate file explorer
	 * @param result = integer indicating if the selection was valid
	 * @param@return file = file selected by user
	 * uses JFileChooser to allow user to select a file and then return the file if it is valid or null if it is not
	 */
	public static File findFile()
	{
		JFileChooser find = new JFileChooser();
		find.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int result = find.showOpenDialog(find);
		if(JFileChooser.APPROVE_OPTION == result)
		{
			File file = find.getSelectedFile();
			return file;
		}
		else
		{
			return null;
		}
	}
}
