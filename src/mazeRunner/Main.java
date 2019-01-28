package mazeRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	public static void main(String args[]) throws FileNotFoundException {

		Scanner sc = new Scanner(System.in);
		System.out.println("Name/Path of the folder?");
		String filename = sc.nextLine();
//		String filename = "Samples";
		File folder = new File(filename);

		 while (!folder.exists()) {
		 System.out.println("\nPlease insert a valid folder name:");
		 filename = sc.nextLine();
		
		 System.out.println();
		 folder = new File(filename);
		 }
		 sc.close();

		new MazeRunner(folder);
	}
}
