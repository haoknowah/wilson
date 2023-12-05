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
		System.out.println("MEOW");
		stripper.parseFile(doc);
		Scanner in = new Scanner(System.in);
		String yub = in.next();
		System.out.println("B");
		Save.saveToCategory(yub);
		Account account = ModData.getAccount("yub");
		System.out.println("A");
		File file = ReadFile.findFile();
		if(file != null)
		{
			System.out.println("File found");
		}
	}
}
