package wilson.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;

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
}
