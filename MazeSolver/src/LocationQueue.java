
public class LocationQueue {

	private int front;
	private int rear;
	private int nItems;

	public LocationQueue() {
		front = 0;
		rear = -1;
		nItems = 0;
	}

	public void insert(int row, int column) {
		Location location = new Location(row, column);
		nItems++;
	}

	public boolean isEmpty() {
		if (nItems == 0) {
			return true;
		} else {
			return false;
		}
	}
}

