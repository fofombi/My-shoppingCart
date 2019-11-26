import java.util.ArrayList;
import java.util.Arrays;

public class ShoppingList extends Expenses implements Basket {
	private ArrayList<Item> items;
	private int CAPACITY = 7; // capacity of cart
	private int index = 0;

	public ShoppingList() {
		this.CAPACITY = 7;
		this.items = new ArrayList<Item>();
	}

	public ShoppingList(int capacity) {
		this.CAPACITY = capacity;
		this.items = new ArrayList<Item>();
	}

	public int currentIndex() {
		return index;
	}

	public int numItems() {
		return index;
	}

	// checks whether our cart reaches maximum capacity
	public boolean isFull() {
		return index >= CAPACITY;
	}

	// to add item , takes item object as parameter
	public void addItem(Item item) {
		if (isFull()) {
			System.out.println("\nSorry no place in your List...");
			return;
		}
		if (inList(item)) {
			System.out.println("Item is already in List");
			return;
		}

//		this.items[index++] = item;
		this.items.add(item);
		index++;
	}

	public Item getItem(int index) {
		return items.get(index);
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	// Check if item is already in Shopping List
	public boolean inList(Item item) {
		return this.items.contains(item);
	}

	void bubbleSort(Item arr[]) {
		// from https://www.geeksforgeeks.org/bubble-sort/ with modifications
		int n = arr.length;
		for (int i = 0; i < n - 1; i++)
			for (int j = 0; j < n - i - 1; j++)
				if (arr[j].compareTo(arr[j + 1]) > 0) {
					// swap arr[j+1] and arr[i]
					Item temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
	}

	public void sortItems() {
		bubbleSort(this.items.toArray(new Item[0]));
	}

	// Overriding tostring to print all items in cart
	@Override
	public String toString() {
		String list = ("\n     ---------------------------------------------------------------\n");
		list = list + String.format("%10s\t%1s\t%1s\t%1s \n", "ID", "Cost", "Pty", "Qty");
		list = list + "     ---------------------------------------------------------------\n";
		for (int i = 0; i < index; i++) {
			list = list + getItem(i).toString() + "\n";
		}
		list = list + "\n";
		list = list + ("     ---------------------------------------------------------------\n");
		return list;
	}

	@Override
	public Item getItemAtIndex(int index) {
		return getItem(index);
	}
}
