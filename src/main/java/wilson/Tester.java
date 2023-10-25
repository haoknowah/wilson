package wilson;

import java.io.IOException;
import java.util.List;

import wilson.functions.ModData;
import wilson.functions.Save;
import wilson.functions.StripPdfData;
import wilson.io.ReadFile;
import wilson.models.Account;

public class Tester {

	public static void main(String[] args) throws IOException{
		List<String[]> doc = ReadFile.readPdfByPage("yub");
		StripPdfData stripper = new StripPdfData();
		stripper.parseFile(doc);
		Save.saveToCategory("XXXX");
		Account account = ModData.getAccount("yub");
	}

}
