package wilson;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;

import wilson.functions.DisplayData;
import wilson.functions.ModData;
import wilson.functions.Save;
import wilson.functions.StripPdfData;
import wilson.io.ReadFile;
import wilson.models.Account;

public class Tester {

	public static void main(String[] args) throws IOException{
		System.out.println("Testing display");
		DisplayData dis = new DisplayData();
		dis.showBreakdown();
		dis.showBreakdownByCategory();
		System.out.println("Testing Account methods");
		Account acc = new Account("Goku");
		Account account = ModData.getAccount("yub");
		System.out.println(acc.getBalance());
		File file = ReadFile.findFile();
		if(file != null)
		{
			System.out.println("File found");
		}
	}
}
