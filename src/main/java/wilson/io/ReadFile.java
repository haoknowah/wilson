package wilson.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.google.gson.Gson;

import wilson.models.Category;

public class ReadFile {
	public ReadFile()
	{
		System.out.println("Meow");
	}
	public String readPdf() throws IOException
	{
		File file = new File("E:/Documents/ProjectWorkspace/wilson/Statement.pdf");
		PDDocument doc = Loader.loadPDF(file);
		PDFTextStripper stripper = new PDFTextStripper();
		String text = stripper.getText(doc);
		doc.close();
		return text;
	}
	public List<String[]> readPdfByPage(String loc) throws IOException
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
	public Category[] getCategoriesFromFile() throws FileNotFoundException
	{
		Category[] categories = null;
		Reader file = new FileReader(System.getProperty("user.dir" + "/categories.json"));
		Gson gson = new Gson();
		categories = gson.fromJson(file, Category[].class);
		return categories;
	}
}
