import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/* Maze Solver
 * Roshan Alexander
 * Nasir Ashraf
 * Miguel Aliaga */

/* This class simply defines each object in the 2 dimensional array. */

class Location {

	private int row;
	private int column;
	private boolean visitStatus = false;
	private Location next;

	public Location(int row, int column) { // constructor discretely sets object parameters
		this.row = row;
		this.column = column;
		visitStatus = false;
	}

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
}

/*
 * This class relies on the linked list class to maintain related objects in a
 * queue. New objects are inserted at the rear and deleted from the front.
 * Follows FIFO design
 */

class LocationQueue {

	private LocationLinkList theList;

	public LocationQueue() { // Constructor creates instance of the link list
		theList = new LocationLinkList();
	}

	public boolean isEmpty() {
		return theList.isEmpty();
	}

	public void insert(int row, int column) {
		theList.insertLast(row, column);
	}

	public Location remove() {
		return theList.deleteFirst();
	}
}

/* Linked list class stores location objects */

class LocationLinkList {

	private Location first;
	private Location last;

	public LocationLinkList() { // Constructor prepares the link list for incoming objects
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

	public void insertLast(int row, int column) { // inserts location at the rear
		Location locationLink = new Location(row, column);
		if (isEmpty())
			first = locationLink;
		else
			last.setNext(locationLink);
		last = locationLink;
	}

	public Location deleteFirst() { // deletes from the front
		Location temp = first;
		Location nextLocation = temp.getNext();
		if (nextLocation == null) {
			last = null;
		}
		first = nextLocation;
		return temp;
	}
}

/*
 * This class contains all logical algorithms for displaying, parsing, and
 * tracing through the 2 dimensional array. The user shall be queried for the
 * maze text file name that must be placed within the projects src folder. The
 * user can select to display or solve the maze. The algorithm will trace
 * through the array to the exit point dictated by the text files first line. If
 * there are two exit points, the algorithm will find the nearest exit and
 * notify the user while displaying the maze with a traced path
 */

public class Maze {

	private static int rows;
	private static int columns;
	private static int startRow;
	private static int startCol;
	private static int endRow;
	private static int endCol;

	private static char[][] mazeArray;
	private static File file;

	private static Scanner fileInput = new Scanner(System.in);
	private static Scanner choiceInput = new Scanner(System.in);

	private static String fileMessage = "Please enter the filename for the Maze:";
	private static String choiceMessage = "Enter the following: 1 <-- display maze 2 <-- solve maze";
	private static String foundMessage = "File found!" + "\n";
	private static String notFoundMessage = "File does not exist! Check the src folder!" + "\n";
	private static String altExitMessage = "We have exited from an alternate/closer location!!";
	private static String exitMessage = "We have exited from the maze!!";
	private static boolean isFileFound = false;

	// This method will ask the user for the file name. If the file is not found,
	// the user will indefinitely be
	// be asked for a valid file. After, the user can choose to display the maze or
	// solve it.

	private static void queryUser() {

		boolean isFileValid = false;
		while (!isFileValid) { // Continuously ask for a valid file
			try {
				System.out.println(fileMessage);
				file = new File("src/" + fileInput.nextLine() + ".txt");
				fileInput = new Scanner(file);
				isFileFound = true;
				isFileValid = true;
				break;
			} catch (FileNotFoundException e) { // Catch the exception and continue the loop
				System.out.println(notFoundMessage);
				continue;
			}
		}

		if (isFileFound) {
			System.out.println(foundMessage);
		}

		boolean shouldLoop = true; // the user's input will be scanned and matched with the appropriate case
									// statement
		while (shouldLoop) {
			System.out.println(choiceMessage);
			int choice = choiceInput.nextInt();
			switch (choice) {
			case 1:
				choice = 1;
				readMaze();
				System.out.println();
				shouldLoop = false;
				break;
			case 2:
				choice = 2;
				System.out.println();
				getSpecs();
				solveMaze();
				shouldLoop = false;
				break;
			default:
				System.out.println("Invalid choice");
			}
		}
	}

	// Retrieve all data from the first line of the text file and use the variables
	// throughout the class to parse the
	// the array. Each line will be parsed be character and stored in the 2D array.

	private static void getSpecs() {

		rows = fileInput.nextInt();
		columns = fileInput.nextInt();
		startRow = fileInput.nextInt();
		startCol = fileInput.nextInt();
		endRow = fileInput.nextInt();
		endCol = fileInput.nextInt();

		String fileText = fileInput.nextLine();
		fileText = fileInput.nextLine(); // handle the text not wrapping properly

		mazeArray = new char[rows][columns];

		for (int i = 0; i < rows; i++) {
			char[] cArray = fileText.toCharArray();
			for (int j = 0; j < columns; j++) {
				mazeArray[i][j] = cArray[j];
			}
			if (fileInput.hasNext()) {
				fileText = fileInput.nextLine();
			}
		}
	}

	// Defines whether the exit was nearer/not the intended exit or the intended
	// exit. Then, the maze is printed.

	private static void success(int exit1, int exit2) {
		if (exit1 != endRow || exit2 != endCol) {
			System.out.println(altExitMessage);
		} else {
			System.out.println(exitMessage);
		}
		displayMazePath();
	}

	// Reads the maze text file line by line and displays it

	private static void readMaze() {
		String fileText = fileInput.nextLine(); // Skip the first line with the maze specifications

		while (fileInput.hasNext()) {
			fileText = fileInput.nextLine();
			System.out.println(fileText);
		}
	}

	// Displays the traced path of the maze

	private static void displayMazePath() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				System.out.print(mazeArray[i][j]);
			}
			System.out.println();
		}
	}

	// The method begins with the starting location and inserts the location into
	// the queue. Each object is removed from the stack
	// starting with objects at the front (FIFO) of the queue. Every location around
	// the current in analyzed for an open path or ' '
	// character. When each object is marked as visited, the path is traced until
	// the queue is empty, the end points are reached,
	// or a closer exit is found

	private static void solveMaze() {
		LocationQueue queue = new LocationQueue();
		queue.insert(startRow, startCol);

		int curRow = 0;
		int curColumn = 0;
		int outerRow = rows - 1;
		int outerCol = columns - 1;

		while (queue.isEmpty() == false) {

			Location current = queue.remove();

			if (current.wasVisited() == false) {
				curRow = current.getRow();
				curColumn = current.getColumn();

				current.setVisited(1);
				mazeArray[curRow][curColumn] = '.';

				if (curRow == endRow && curColumn == endCol) { // the exit points have the reached there are no other
																// exits
					success(curRow, curColumn);
					break;
				} else {

					// check location above current
					// UP

					if (curRow != 0) { // condition prevents array exception
						if (mazeArray[curRow - 1][curColumn] == ' ') {
							queue.insert(curRow - 1, curColumn);
						}
					} else if (curRow != startRow || curColumn != startCol) { // condition prevents array exception
						success(curRow, curColumn);
						break;
					}

					// check location below current
					// DOWN

					if (curRow != outerRow) { // condition prevents array exception
						if (mazeArray[curRow + 1][curColumn] == ' ') {
							queue.insert(curRow + 1, curColumn);
						}
					} else if (curRow != startRow || curColumn != startCol) { // condition prevents array exception
						success(curRow, curColumn);
						break;
					}

					// check location to the right of current
					// RIGHT
					if (curColumn != outerCol) { // condition prevents array exception
						if (mazeArray[curRow][curColumn + 1] == ' ') {
							queue.insert(curRow, curColumn + 1);
						}
					} else if (curColumn != startCol || curRow != startRow) { // prevents array exception
						success(curRow, curColumn);
						break;
					}

					// check location to the left of current
					// LEFT

					if (curColumn != 0) { // condition prevents array exception
						if (mazeArray[curRow][curColumn - 1] == ' ') {
							queue.insert(curRow, curColumn - 1);
						}
					} else if (curRow != startRow || curColumn != startCol) { // condition prevents array exception
						success(curRow, curColumn);
						break;
					}
				}
			}
		}
	}

	// Main method

	public static void main(String[] args) {

		queryUser();
	}
}
