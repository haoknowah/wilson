package wilson;

import java.io.File;
import java.io.IOException;
import java.util.List;
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
		stripper.parseFile(doc);
		Save.saveToCategory("XXXX");
		Account account = ModData.getAccount("yub");
		File file = ReadFile.findFile();
		if(file != null)
		{
			
		}
	}
}
