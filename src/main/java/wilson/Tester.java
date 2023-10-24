package wilson;

import java.io.IOException;
import java.util.List;

import wilson.functions.ModData;
import wilson.functions.Save;
import wilson.functions.StripPdfData;
import wilson.io.ReadFile;

public class Tester {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		ReadFile tst = new ReadFile();
		List<String[]> doc = tst.readPdfByPage("yub");
		StripPdfData stripper = new StripPdfData();
		stripper.parseFile(doc);
		Save.saveToCategory("XXXX");
		ModData.getAccount("yub");
	}

}
