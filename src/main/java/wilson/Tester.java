package wilson;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;

import wilson.functions.ModData;
import wilson.functions.Save;
import wilson.functions.StripPdfData;
import wilson.io.ReadFile;
import wilson.models.Account;

public class Tester {

	public static void main(String[] args) throws IOException{
		List<String[]> doc = ReadFile.readPdfByPage();
		StripPdfData stripper = new StripPdfData();
		System.out.println("Testing stripper and file reader.");
		stripper.parseFile(doc);
		System.out.println("Testing display");
		
		System.out.println("Testing Save");
		
		System.out.println("Testing Load");
		
		System.out.println("Testing ModData");
		
		System.out.println("Testing Account methods");
		
		
		Account account = ModData.getAccount("yub");
		File file = ReadFile.findFile();
		if(file != null)
		{
			System.out.println("File found");
		}
	}
}
