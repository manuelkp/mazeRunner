package mazeRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MazeRunner {
	static int totalSteps;
	static String tempArray[][];

	public MazeRunner(File folder) throws FileNotFoundException {

		File[] listOfFiles = folder.listFiles();
		String[][] maze = null;
		int step = 0;
		int WIDTH = 0, LENGTH = 0, START_X = 0, START_Y = 0, END_X = 0, END_Y = 0;

		for (File file : listOfFiles) {
			if (file.isFile()) {
				System.out.println("\n"+file.getName()); ////
				Scanner sc = new Scanner(file);
				int lineCounter = 0; // to count the first three lines
				step = 0;
				totalSteps = 9999999;
				while (sc.hasNextLine()) {
					String tempStr;
					if (lineCounter == 0) { // WIDTH AND LENGTH
						tempStr = sc.nextLine();
						String[] splitStr = tempStr.split(" ");
						WIDTH = Integer.parseInt(splitStr[0]);
						LENGTH = Integer.parseInt(splitStr[1]);
						maze = new String[LENGTH][WIDTH];
					} else if (lineCounter == 1) { // START_X and START_Y
						tempStr = sc.nextLine();
						String[] splitStr = tempStr.split(" ");
						START_X = Integer.parseInt(splitStr[0]);
						START_Y = Integer.parseInt(splitStr[1]);
					} else if (lineCounter == 2) { // END_X AND END_Y
						tempStr = sc.nextLine();
						String[] splitStr = tempStr.split(" ");
						END_X = Integer.parseInt(splitStr[0]);
						END_Y = Integer.parseInt(splitStr[1]);
					} else {
						makeMaze(maze, lineCounter - 3, sc.nextLine(), START_X, START_Y, END_X, END_Y);
					}
					tempArray = new String[LENGTH][WIDTH];
					lineCounter++;
				}

				sc.close();

			}

			runTheMaze(maze, START_X, START_Y, step);	
			fixMaze(maze, END_X, END_Y);
			printMaze(maze);

		}
	}

	private void fixMaze(String[][] maze, int tempX, int tempY) {
		for (int j = totalSteps; j >= 0; j--) {
			if (maze[getArrayPos(tempY, maze.length)][getArrayPos(tempX + 1, maze[0].length)].equals(j + "")) {
				maze[getArrayPos(tempY, maze.length)][getArrayPos(tempX + 1, maze[0].length)] = "X";
				tempX = tempX + 1;
			} else if (maze[getArrayPos(tempY, maze.length)][getArrayPos(tempX - 1, maze[0].length)]
					.equals(j + "")) {
				maze[getArrayPos(tempY, maze.length)][getArrayPos(tempX - 1, maze[0].length)] = "X";
				tempX = tempX - 1;
			} else if (maze[getArrayPos(tempY - 1, maze.length)][getArrayPos(tempX, maze[0].length)]
					.equals(j + "")) {
				maze[getArrayPos(tempY - 1, maze.length)][getArrayPos(tempX, maze[0].length)] = "X";
				tempY = tempY - 1;
			} else if (maze[getArrayPos(tempY + 1, maze.length)][getArrayPos(tempX, maze[0].length)]
					.equals(j + "")) {
				maze[getArrayPos(tempY + 1, maze.length)][getArrayPos(tempX, maze[0].length)] = "X";
				tempY = tempY + 1;
			}

		}
		
		for (int k = 0; k < maze.length; k++) {
			for (int l = 0; l < maze[0].length; l++) {
				if (!maze[k][l].equals("S") && !maze[k][l].equals("E") && !maze[k][l].equals("X")
						&& !maze[k][l].equals("#")) {
					maze[k][l] = " ";
				}
			}

		}		
	}

	private static void printMaze(String[][] maze) {
		System.out.println("");
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[0].length; j++) {
				System.out.print(maze[i][j]);
			}
			System.out.println("");
		}

	}

	/***************************************************************************
	 * Creates the array of the maze. 1 becomes #, 0 becomes ' ', the starting
	 * point becomes S and the ending E
	 ****************************************************************************/
	private static void makeMaze(String[][] maze, int linePos, String line, int START_X, int START_Y, int END_X,
			int END_Y) {
		/* maze.length = height - maze[0].length = width */
		String[] splitStr = line.split(" ");

		for (int i = 0; i < maze[0].length; i++) {
			if (linePos < maze.length) {
				if (i == START_X && linePos == START_Y) {
					maze[linePos][i] = "S";
				} else if (i == END_X && linePos == END_Y) {
					maze[linePos][i] = "E";
				} else if (splitStr[i].equals("1"))
					maze[linePos][i] = "#";
				else
					maze[linePos][i] = " ";
			}
		}

	}

	/************************************************
	 * Recursive function to find the correct route.
	 ************************************************/
	private static void runTheMaze(String[][] maze, int x, int y, int step) {
		if (maze[y][x] == "E") {
			if (totalSteps > step) {
				totalSteps = step;
			}
		} else {
			if (maze[y][x] != "S")
				maze[y][x] = String.valueOf(step);
			if (isValidPos(maze, x + 1, y, step)) {
				runTheMaze(maze, getArrayPos(x + 1, maze[0].length), getArrayPos(y, maze.length), step + 1); // E
			}
			if (isValidPos(maze, x, y + 1, step)) {
				runTheMaze(maze, getArrayPos(x, maze[0].length), getArrayPos(y + 1, maze.length), step + 1); // S
			}
			if (isValidPos(maze, x, y - 1, step)) {
				runTheMaze(maze, getArrayPos(x, maze[0].length), getArrayPos(y - 1, maze.length), step + 1); // N
			}
			if (isValidPos(maze, x - 1, y, step)) {
				runTheMaze(maze, getArrayPos(x - 1, maze[0].length), getArrayPos(y, maze.length), step + 1); // W
			}
		}
	}

	/***************************************************************************
	 * In order for runTheMaze() to go to the next point, this has to be a ' '.
	 ***************************************************************************/
	public static boolean isValidPos(String[][] maze, int x, int y, int step) {
		x = getArrayPos(x, maze[0].length);
		y = getArrayPos(y, maze.length);


		if (maze[y][x].equals(" ") || maze[y][x].equals("E"))
			return true;
		else if (maze[y][x].equals("S"))
			return false;

		if (!maze[y][x].equals("#"))
			if (Integer.parseInt(maze[y][x]) > step + 1)
				return true;
		return false;
	}

	public static boolean isNeighbour(String[][] maze, int x, int y) {
		x = getArrayPos(x, maze[0].length);
		y = getArrayPos(y, maze.length);

		if (maze[y][x] == "X" || maze[y][x] == "S" || maze[y][x] == "E")
			return true;
		else
			return false;
	}

	/********************************************************************
	 * In order to do the horizontal and vertical wrapping, it checks if
	 * the the coordinates are out of the array /*** limits and changes 
	 * them accordingly.
	 ************************************************************************/
	public static int getArrayPos(int pos, int minMax) {
		if (pos >= minMax)
			pos = pos % minMax;
		else if (pos < 0)
			pos = minMax + pos;
		return pos;
	}
}