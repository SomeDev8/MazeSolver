
public class LocationQueue {

	private LocationLinkList theList;

	public LocationQueue() 
	{
		theList = new LocationLinkList();
	} 

	public boolean isEmpty() 
	{
		return theList.isEmpty();
	}

	public void insert(int row, int column) 
	{
		theList.insertLast(row, column);
	}

	public Location remove() 
	{
		return theList.deleteFirst();
	}
}
