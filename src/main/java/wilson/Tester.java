package wilson;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;


import wilson.functions.DisplayData;
import wilson.functions.ModData;
import wilson.functions.Save;
import wilson.functions.StripPdfData;
import wilson.io.Prompts;
import wilson.io.ReadFile;
import wilson.models.Account;
import wilson.models.Category;

public class Tester {

	public static void main(String[] args) throws IOException{
		Scanner in = Prompts.in;
		while(true)
		{
			boolean end = false;
			int men = 0;
			System.out.println("Enter an option: ");
			System.out.println("1 = Account Options");
			System.out.println("2 = Read Statement");
			System.out.println("3 = Category Information");
			System.out.println("4 = Clear Files");
			System.out.println("5 = End Program");
			in.nextLine();
			men = in.nextInt();
			switch(men)
			{
				case 1:
					System.out.println("Testing Account methods");
					Account acc = new Account("Goku");
					Account account = ModData.getAccount("yub");
					System.out.println(acc.getBalance()); 
					break;
				case 2:
					DisplayData dis = new DisplayData();
					dis.showBreakdown();
					dis.showBreakdownByCategory();
					break;
				case 3:
					List<Category> cats = ModData.getCategories();
					for(Category cat : cats)
					{
						System.out.println(cat.getName());
						cat.getCulprits().stream().forEach(x -> System.out.print(x + ", "));
						System.out.print("\n");
					}
					break;
				case 4:
					System.out.println("Clear Files? Y/N");
					boolean clear = Prompts.yesno();
					if(clear)
					{
						ModData.emptyFiles(true);
					}
					break;
				case 5:
					end = true;
					break;
			}
			if(end == true)
			{
				break;
			}
		}
		in.close();
	}
}
