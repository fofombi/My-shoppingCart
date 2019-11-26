// A basket interface is to be implemented for object with a fix capacity.
public interface Basket {
	public boolean isFull();

	public void addItem(Item item);

	public Item getItem(int index);

}
