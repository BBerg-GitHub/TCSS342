import java.io.IOException;

/**
 * 
 */

/** The Main class can test the class of the maze. 
 * @author Jiarui Xiong
 * @author Brandi Berg
 * @version 6-7-2019
 *
 */

public class Main {
	
	/** The main method test Maze class and methods.
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		Maze test = new Maze(5, 5, true);
//		testMaze();
	}
	
	/**
	 *  The method can test all method in maze.
	 */
	public static void testMaze() {
		Maze test1 = new Maze(5, 5, false);
		System.out.println("Test display method");
		test1.display();
		System.out.println("Test End");
		
//		Maze test2 = new Maze(8, 6, false);
//		
//		
//		Maze test3 = new Maze(16, 16, true);
//		Maze test4 = new Maze(10, 20, true);	
	}
	
	
}
