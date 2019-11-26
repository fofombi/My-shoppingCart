
// Item class for each item in cart
public class Item extends NamedObject implements Comparable<Item> {
	private double cost; // cost of product
	private int priority;
	private int quantity;// inventoryQuantity of item
	// default constructor

	public Item() {
	}

	// constructor takes item parameters
	public Item(String name, double cost, int priority, int quantity) {
		this.name = name;
		this.cost = cost;
		this.priority = priority;
		this.quantity = quantity;
	}

	public  Item createCopy(){
		return   new Item(this.name, this.cost,this.priority,this.quantity);
	}

	public String toString() {
		String output = String.format("%10s\t%5.2f\t%2d \t%2d", name, cost, priority, quantity);
		return output;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getPriority() {
		return priority;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void incrementQuantity() {
		quantity++;

	}
	public double getAllItemsCost() {
		return cost*quantity;
	}

	public void decrementQuantity() {
		quantity--;
	}

	public boolean isAvailable() {
		return quantity > 0;

	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int compareTo(Item item) {
		if (getPriority() > item.getPriority()) {
			return 1;
		}
		if (getPriority() < item.getPriority()) {
			return -1;
		}
		return 0;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Item) {
			return (((Item) o).getName().toLowerCase().equals(this.getName().toLowerCase()));
		}
		return false;
	}

}
