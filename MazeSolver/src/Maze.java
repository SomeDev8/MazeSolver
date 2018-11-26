import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Maze {

	private static int rows;
	private static int columns;
	private static int startRow;
	private static int startCol;
	private static int endRow;

	private static char[][] mazeArray;

	static Scanner fileInput = new Scanner(System.in);
	static String message = "Please enter the filename for the Maze:";

	private static void displayMaze() {

		System.out.println(message);

		File file = new File("src/" + fileInput.nextLine() + ".txt");

		try {
			fileInput = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (fileInput.hasNext()) {
			String fileText = fileInput.nextLine();
			System.out.println(fileText);

		}
	}

	private static void getSpecs() {

		System.out.println(message);

		File file = new File("src/" + fileInput.nextLine() + ".txt");

		try {
			fileInput = new Scanner(file);

			rows = fileInput.nextInt();
			columns = fileInput.nextInt();
			startRow = fileInput.nextInt();
			startCol = fileInput.nextInt();
			endRow = fileInput.nextInt();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String fileText = fileInput.nextLine(); // handle the text not wrapping properly
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

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				char c = mazeArray[i][j];
				System.out.print(c);
			}
			System.out.println();
		}

	}

	private static void tracePath() {

	}

	private static void loadArray() {

	}

	public static void main(String[] args) {

		// displayMaze();
		getSpecs();

	}
}
