
public abstract class Expenses {
	public abstract int currentIndex();

	public abstract int numItems();

	public abstract Item getItemAtIndex(int index);

	public double totalCost() {
		double total = 0;
		for (int i = 0; i < currentIndex(); i++) {
			total += getItemAtIndex(i).getCost() * getItemAtIndex(i).getQuantity();
		}
		return total;
	}
}
