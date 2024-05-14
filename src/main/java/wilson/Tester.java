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
			men = in.nextInt();
			System.out.print("\n");
			switch(men)
			{
				case 1:
					System.out.println("Enter an option: ");
					System.out.println("1 = View Account Balance");
					System.out.println("2 = Add/Remove Account");
					System.out.println("3 = Edit balance");
					int accOp = in.nextInt();
					System.out.print("\n");
					Account acc;
					System.out.print("Enter Account Name: ");
					String accName = in.next();
					System.out.print("\n");
					if(accOp == 1)
					{
						acc = ModData.getAccount(accName);
						System.out.println("$" + acc.getBalance());
					}
					if(accOp == 2)
					{
						ModData.removeAccount(accName);
					}
					if(accOp == 3)
					{
						acc = ModData.getAccount(accName);
						System.out.print("Enter new balance: $");
						double bal = in.nextDouble();
						System.out.print("\n");
						acc.setBalance(bal);
						ModData.updateAccount(acc);
					}
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
					System.out.println("Enter 1 to create a new category, 2 to get rid of a category, 3 to \n"
							+ "add/remove an item from a category");
					int x = in.nextInt();
					System.out.println();
					System.out.println("Type the category name: ");
					String cat = in.next();
					switch(x)
					{
						case 1:
							String[] yub = Prompts.newCategory(cat);
							ModData.createCategory(new Category(yub[0], yub[1]));
							break;
						case 2:
							ModData.removeCategory(cat);
							break;
						case 3:
							List<Category> meow = ModData.getCategories();
							Category c = meow.stream().filter(y -> y.getName() == cat).toList().get(0);
							System.out.println("Enter an item name: ");
							String name = in.next();
							if(c.getCulprits().contains(name))
							{
								c.removeCulprit(name);
							}
							else
							{
								c.addCulprit(name);
							}
							break;
					}
					System.out.println();
					
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
