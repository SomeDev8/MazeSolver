
public class LocationLinkList {

	private Location first;
	private Location last;

	public LocationLinkList() {
		first = null;
		last = null;
	}

	public boolean isEmpty() {
		if (first == null) {
			return true;
		} else {
			return false;
		}
	}

	public void insertLast(int row, int column) {
		Location locationLink = new Location(row, column);
		if (isEmpty())
			first = locationLink;
		else
			last.setNext(locationLink);
		last = locationLink;
	}

	public Location deleteFirst() {
		Location temp = first;
		Location nextLocation = temp.getNext();
		if (nextLocation == null) {
			last = null;
		}
		first = nextLocation;
		return temp;
	}
}
