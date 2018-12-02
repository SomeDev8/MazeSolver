
public class Location {

	private int row;
	private int column;
	private boolean visitStatus = false;

	private Location next;

	public Location getNext() {
		return this.next;
	}

	public void setNext(Location location) {
		this.next = location;
	}

	public void setVisited(int value) {

		if (value == 1) {
			visitStatus = true;

		} else {
			visitStatus = false;
		}
	}

	public boolean wasVisited() {
		return visitStatus;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public Location(int row, int column) {
		this.row = row;
		this.column = column;
		visitStatus = false;
	}
}
