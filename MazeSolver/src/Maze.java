import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
	private static boolean isFileFound = false;

	private static void queryUser() {

		boolean isFileValid = false;
		while (!isFileValid) {
			try {
				System.out.println(fileMessage);
				file = new File("src/" + fileInput.nextLine() + ".txt");
				fileInput = new Scanner(file);
				isFileFound = true;
				isFileValid = true;
				break;
			} catch (FileNotFoundException e) {
				System.out.println(notFoundMessage);
				continue;
			}
		}

		if (isFileFound) {
			System.out.println(foundMessage);
		}

		boolean shouldLoop = true;
		while (shouldLoop) {
			System.out.println(choiceMessage);
			int choice = choiceInput.nextInt();
			switch (choice) {
			case 1:
				choice = 1;
				displayMaze();
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

	private static void solveMaze() {
		LocationQueue queue = new LocationQueue();
		queue.insert(startRow, startCol);
		
		int row = 0;
		int column = 0;
		while (queue.isEmpty() == false) {

			Location current = queue.remove();
			
			if (current.wasVisited() == false) {
				row = current.getRow();
				column = current.getColumn();
				System.out.println(row + " " + column  );

				current.setVisited(1);
				mazeArray[row][column] = '.';

				if (row == endRow && column == endCol) {
					success();
					break;
				} else {
					// check location above current
					// UP
					if (row != 0) {
						if (mazeArray[row - 1][column] == ' ') {
							queue.insert(row - 1, column);
						}
					}

					// check location below current
					// DOWN

					if (mazeArray[row + 1][column] == ' ') {
						queue.insert(row + 1, column);
					}

					// check location to the right of current
					// RIGHT

					if (mazeArray[row][column + 1] == ' ') {
						queue.insert(row, column + 1);

					}

					// check location to the left of current
					// LEFT

					if (column != 0) {
						if (mazeArray[row][column - 1] == ' ') {
							queue.insert(row, column - 1);
						}
					}
				}
			}
		}
	}

	private static void success() {
		System.out.println("We have exited the maze!!");
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				System.out.print(mazeArray[i][j]);
			}
			System.out.println();
		}
	}

	public static void userChoice() {
		System.out.println(choiceMessage);
	}

	private static void displayMaze() {

		while (fileInput.hasNext()) {
			String fileText = fileInput.nextLine();
			System.out.println(fileText);
		}
	}
	
	public static void main(String[] args) {

		queryUser();
	}
}
