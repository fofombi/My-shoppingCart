import java.util.Iterator;
import java.util.Scanner;

//Shopping cart class to collect items
public class Assigment5 {
	public static void main(String[] args) {
		ShoppingList inventory = new ShoppingList();

		Scanner keyboard = new Scanner(System.in);
		String name;
		int priority;

		while (inventory.numItems() < 7) {
			System.out.println(String.format("Your inventory has %d item types.", inventory.numItems()));
			System.out.println("Please enter the item inventoryItems:\t");
			name = keyboard.nextLine();
			priority = 0;
			while (priority < 1 || priority > 7) {
				System.out.println("Please enter item's priority [1-7]:\t");
				priority = Integer.parseInt(keyboard.nextLine());
				if (priority > 7 || priority < 1) {
					System.out.println("Priority out of range");

				}
			}
			int quantity = 0;
			while (quantity < 1) {
				System.out.println("Please enter inventoryQuantity:\t");
				quantity = Integer.parseInt(keyboard.nextLine());
				if (quantity < 1) {
					System.out.println("The inventoryQuantity can Not be less than 1");
				}

			}
			Item item = new Item();
			item.setName(name);
			item.setPriority(priority);
			item.setQuantity(quantity);
			inventory.addItem(item);

		}

		System.out.println("Setting the inventoryPrice of the items");

		while (inventory.totalCost() <= 100.00) {
			System.out.println(
					String.format("The total cost of the items is %.2f which is not > 100.00", inventory.totalCost()));
			System.out.println("Please enter items' prices down to the penny.");
			for (int i = 0; i < inventory.numItems(); i++) {
				Item item = inventory.getItem(i);
				System.out.println(String.format("%s : \t", item.getName()));
				double cost = Double.parseDouble(keyboard.nextLine());
				item.setCost(cost);
			}
			System.out.println(String.format("Inventory total is %.2f", inventory.totalCost()));
		}

		inventory.sortItems(); // sort by priority

		System.out.println(inventory.toString()); // TODO: remove

		// Time to SHOP!!!
		ShoppingList shoppingCart = new ShoppingList();
		int inventory_index = 0;
		double bankAccount = 0.0;
		while (bankAccount < 1) {
			System.out.println("What is the budget?");
			bankAccount = Double.parseDouble(keyboard.nextLine());
			if (bankAccount < 1) {
				System.out.println("budget out of range");

			}
		}
		double budget = bankAccount;
		while (shoppingCart.totalCost() <= budget && inventory_index < inventory.numItems()) {

			Item inventoryItem = inventory.getItem(inventory_index);
			Item cartItem = new Item(inventoryItem.getName(), inventoryItem.getCost(), inventoryItem.getPriority(),
					inventoryItem.getQuantity());
			cartItem.setQuantity(0);
			while (inventoryItem.isAvailable()) {
				double currentTotal = shoppingCart.totalCost() + (cartItem.getCost() * cartItem.getQuantity());
				double newTotal = inventoryItem.getCost() + currentTotal;
				if (newTotal > budget) {
					break;

				} else {
					inventoryItem.decrementQuantity();
					cartItem.incrementQuantity();
				}

			}
			if (cartItem.isAvailable()) {
				shoppingCart.addItem(cartItem);
			}
			inventory_index++;
		}
		String userName = "";
		while (userName.length() < 1) {
			System.out.println("What is your Name?");
			boolean hasNum = false;
			String temp = keyboard.nextLine();
			for (int i = 0; i < temp.length(); i++) {
				char c = temp.charAt(i);
				if (c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8'
						|| c == '9' || c == '0') {
					hasNum = true;

				}
			}
			if (hasNum) {
				System.out.println("Your Name should NOT have a Number.");
			} else {
				userName = temp;
			}
		}
		System.out.println("Do you want to save to a file? [Y/N]");

		char c = ' ';
		while (!(c == 'y' || c == 'n' || c == 'Y' || c == 'N')) {
			c = keyboard.nextLine().charAt(0);
			if (!(c == 'y' || c == 'n' || c == 'Y' || c == 'N')) {
				System.out.println("Wrong input. Do you want to save to a file? [Y/N]");
			}
		}

		boolean saveToFile = (c == 'y' || c == 'Y');
		PrintingOfOutput printer;
		if (saveToFile) {
			printer = new FilePrintingOfOuput();
		} else {
			printer = new ConsolePrintingOfOutput();
		}

		try {
			printer.printOutLn("Your Name is " + userName);
			printer.printOutLn("Your bankAccount balance before shopping is " + String.format("%.2f", bankAccount));

			printer.printOutLn("What is purchased:");
			for (int i = 0; i < shoppingCart.numItems(); i++) {
				Item item = shoppingCart.getItem(i);
				printer.printOutLn(String.format("%s\t%.2f\t%d", item.getName(), item.getCost(), item.getQuantity()));
			}

			printer.printOutLn("What is NOT purchased:");
			for (int i = 0; i < inventory.numItems(); i++) {
				Item inventory_item = inventory.getItem(i);
				if (inventory_item.isAvailable()) {
					printer.printOutLn(String.format("%s\t%.2f\t%d", inventory_item.getName(), inventory_item.getCost(),
							inventory_item.getQuantity()));
				}

			}
			double shoppingCost = shoppingCart.totalCost();
			double newBankAccount = bankAccount - shoppingCost;
			printer.printOutLn("Your bankAccount balance after shopping is " + String.format("%.2f", newBankAccount));

		} catch (PrintingOutputException e) {
			e.printStackTrace();
		}
		if (saveToFile) {
			System.out.println("Done write in to File");
		}
		else {
			System.out.println("Done");
		}

		keyboard.close();

	}
}
