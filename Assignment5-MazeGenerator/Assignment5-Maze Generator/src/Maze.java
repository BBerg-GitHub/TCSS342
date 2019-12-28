/**
 * 
 */

/** The Maze class can create a maze randomly and find solution path. 
 *  Users can pass the size of width and depth to customize the maze. 
 * @author Jiarui Xiong
 * @author Brandi Berg
 * @version 6-7-2019
 *
 */


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;



public class Maze {	

	/** Random number generator.*/
	private Random rand = new Random();
	
	/** the width of a maze.*/
	private int myWidth;
	
	/** the depth of a maze.*/
	private int myDepth;
	
	/** the boolean can check if it needs a step to show how to get the solution path.*/
	private boolean myDebug;
	
	/** A maze through generator.*/
	private Node[][] myMaze;
	
	/** All points by passing.*/
	private ArrayList<Point> myVertices;
	
	/** Using Depth First search.*/
	/*
	 *  Reference: https://www.geeksforgeeks.org/depth-first-search-or-dfs-for-a-graph/
	 *  to understand what DFS is
	 */
	private Stack<Point> myAlgorithm;
	
	/**
	 *  The global variable helps me show graphical display.
	 */
	private Stack<Point> myWindow;
	
	
	/**
	 *  The constructor for Maze class.
	 * @param width  the number of width given by users 
	 * @param depth the number of depth given by users
	 * @param debug if true, it shows the step how to get solution path, otherwise, does not show the procedure of the path
	 */
	public Maze(int width,int depth, boolean debug) {
		myWidth = width * 2 + 1;
		
		myDepth = depth * 2 + 1;
		
		myDebug = debug;
		
		myMaze = new Node[myWidth][myDepth];
		
		myVertices = new ArrayList<Point>();
		
		myAlgorithm = new Stack<Point>();
		
		myWindow = new Stack<Point>();
		
		setMaze();
		
		setPath();
		
		findThePath();
		
		showDisplayWindow();		
	}
	
	/**
	 *  The display method displays the maze using กฎXกฏ and กฎ กฎ characters. Using '+' as solution path.
	 */
	public void display() {
		for (int i = 0; i < myWidth; i++) {
			for (int j = 0; j < myDepth; j++) {
				System.out.print(myMaze[i][j].myValue + " ");
			}
			System.out.println();
		}
		System.out.println("\n");
	}
	
	/**
	 *  The method can create the maze with walls, without any operations.
	 */
	private void setMaze() {		
		for (int i = 0; i < myWidth; i++) {
			for (int j = 0; j < myDepth; j++) {
				if (i % 2 == 0 || j % 2 == 0) {
					
					myMaze[i][j] = new Node('X', false);
				}
				else {
					myMaze[i][j] = new Node(' ', true);
					myVertices.add(new Point(i, j));
				}
			}
		}
//		System.out.println("Before Entrance and Exit.");
//		System.out.println("----------------------------");
		display();
//		System.out.println("----------------------------");
		myMaze[0][1].setValue(' '); // entrance
		myMaze[myWidth - 1][myDepth- 2].setValue(' '); // exit
	}
	
	
	/**
	 *  The method can look for a path that tried to get exit in a maze. I will store possible points to list.
	 */
	private void setPath() {
		Point start = new Point(1, 1); // Starting from here
		Point end = new Point(myWidth - 2, myDepth - 2); // end at the point
		storageChecked(start);
		List<Point> myWay = new ArrayList<Point>();
		Stack<Point> myPath = new Stack<Point>();
		
		while (!myAlgorithm.isEmpty()) {
			myWay = otherVertices(start); // exception the starting point, continue to the next point until get exit.
			if (myWay.size() != 0) {
				if (myDebug) {
					display(); // show the maze
				}
				int temp = rand.nextInt(myWay.size());
				Point tempPoint = myWay.get(temp); // go to the next point by random #
				remove(start, tempPoint);
				storageChecked(tempPoint);
				start = tempPoint;
				
				if (start.equals(end)) {
					myPath.addAll(myAlgorithm);
				}
			}
			else {
				myAlgorithm.pop();
				if (!myAlgorithm.isEmpty()) {
					start = myAlgorithm.peek();
				}
			}
		}
		myAlgorithm.addAll(myPath);
		myWindow.addAll(myPath);
		
		
	}
	


	/**
	 *  The method can store all points that have ready checked.
	 * @param thePoint the point will store to myAlgorithm stack
	 */
	private void storageChecked(final Point thePoint) {
		if (myDebug) {
			myMaze[(int) thePoint.getX()][(int) thePoint.getY()].setValue('V');
		}
		myMaze[(int) thePoint.getX()][(int) thePoint.getY()].setCheck(false);
		myVertices.add(thePoint);
		myAlgorithm.push(thePoint);
		
		myWindow.push(thePoint);	
	}
	
	
	
	/**
	 *  The method can remove 'X' the wall in a maze, make sure users can pass the wall. Calculation between previous and next point.
	 * @param theFirst the previous point
	 * @param theSecond next point
	 */
	private void remove(final Point theFirst, final Point theSecond) {
		myMaze[(int) ((theFirst.getX() + theSecond.getX()) / 2)][(int) ((theFirst.getY() + theSecond.getY()) / 2)].setValue(' ');
	}
	
	
	/**
	 *  The method can check unchecked adjacency vertices, and store into a new list. 
	 * @param thePoint the center point and look for adjacency vertices
	 * @return a list including unchecked adjacency vertices
	 */
	private List<Point> otherVertices(Point thePoint) {
		List<Point> myList = new ArrayList<Point>();
		
		int xBorder = (int) thePoint.getX();
		int yBorder = (int) thePoint.getY();
		
		if (xBorder + 2 < myWidth) {
			if (myMaze[xBorder + 2][yBorder].myCheck) {
				myList.add(new Point(xBorder + 2, yBorder));
			}
		}
		
		if (yBorder + 2 < myDepth ) {
			if (myMaze[xBorder][yBorder + 2].myCheck) {
				myList.add(new Point(xBorder, yBorder + 2));
			}
		}
		
		if (xBorder - 2 > 0) {
			if (myMaze[xBorder - 2][yBorder].myCheck) {
				myList.add(new Point(xBorder - 2, yBorder));
			}
		}
		
		if (yBorder - 2 > 0) {
			if (myMaze[xBorder][yBorder - 2].myCheck) {
				myList.add(new Point(xBorder, yBorder - 2));
			}
		}
		
		return myList;
	}
	
	/**
	 *  The method can find solution for a maze and display on console.
	 */
	private void findThePath() {
		if (myDebug) {
			display();
			for (int i = 0; i < myVertices.size(); i++) {
				myMaze[(int) myVertices.get(i).getX()][(int) myVertices.get(i).getY()].setValue(' ');
			}
		}
//		System.out.println("My path:" + myAlgorithm.toString());
		while (!myAlgorithm.isEmpty()) {
			myMaze[(int) myAlgorithm.peek().getX()][(int) myAlgorithm.peek().getY()].setValue('+');
			
			myAlgorithm.pop();
		}
		display();
	}
	
	/**
	 *  The method for extra credit. It will use drawing Panel class to show the best path of the maze.
	 * 
	 */
	
	/*
	 *  drawingPanel class is from my java 1 class when I took at Green River college.
	 *  I think it can save time than using GUI. 
	 *  x is vertical, y is horizontal. Therefore, I switch those order when I use it as a point.
	 */
	private void showDisplayWindow() {
		DrawingPanel myPanel = new DrawingPanel(myWidth * 50, myDepth * 50);
		
		
		Graphics g = myPanel.getGraphics();
		
		
		for (int i = 0; i < myWidth; i++) {
			for (int j = 0; j < myDepth; j++) {
				g.setColor(Color.BLUE);
				g.drawRect(j * 50, i * 50, 50, 50);
			}
			
		}
//		System.out.println(myWindow.toString());

		
		for (int i = 0; i < myMaze.length; i++) {
			for (int j = 0; j < myMaze[i].length; j++) {
				if (myMaze[i][j].myCheck) {
					g.setColor(Color.BLACK);
					g.fillRect(j * 50, i * 50, 50, 50);
				}
				if (myMaze[i][j].myValue != 'X') {
					g.setColor(Color.BLACK);
					g.fillRect(j * 50, i * 50, 50, 50);
				}
			}
		}
		
//		System.out.println(myWindow.toString());
		List<Point> myList = new ArrayList<Point>();
		

		for (int i = 0; i < myMaze.length; i++) {
			for (int j = 0; j < myMaze[i].length; j++) {
				if (myMaze[i][j].myValue == '+') {
					
					/*
					 *  For drawing panel  x is vertical, y is horizontal
					 */
					myList.add(new Point(j , i));
				}
			}	
		}
//		System.out.println(myList.toString());
		

//		System.out.println(myList.toString());
		for (int i = 0; i < myList.size(); i++) {
			g.setColor(Color.CYAN);
			g.fillRect((int) myList.get(i).getX() * 50, (int) myList.get(i).getY() * 50, 50, 50);

		}
		
		/*
		 *  I tried to draw a line to highlight the path many times, but I failed.
		 */
//		
//		for (int i = 1; i < myList.size(); i++) {
////			Font myFont = new Font(null, Font.BOLD, 48);
////			g.setFont(myFont);
//
//			g.setColor(Color.BLACK);
//			g.drawLine((int) myList.get(i - 1).getX() * 50 + 25, (int) myList.get(i - 1).getY() * 50 + 25, 
//					(int) myList.get(i).getX() * 50 + 25, (int) myList.get(i).getY() * 50 + 25);
//			
//			myPanel.sleep(300);
//		}
//		g.drawLine((int) myList.get(i).getY() * 50, (int) myList.get(i).getX() * 50, 
//				(int) myList.get(i).getY() * 50, (int) myList.get(i+1).getX() * 50);
		
		
//		List<Point> myWall = new ArrayList<Point>();
//		for (int i = 0; i < myList.size() - 1; i++) {
//			int x = (int) (myList.get(i).getX() + myList.get(i + 1).getX());
//			int y = (int) (myList.get(i).getY() + myList.get(i + 1).getY());
////			System.out.println("X: " + x / 2);
////			System.out.println("Y: " + y / 2);
//			myWall.add(new Point(x / 2, y / 2));
//		}
//		
//		for (int i = 0; i < myWall.size(); i++) {
//			g.setColor(Color.CYAN);
//			g.fillRect((int) myWall.get(i).getX() * 50, (int) myWall.get(i).getY() * 50, 50, 50);	
//		}
		
		
		
	}
	
	
	class Node {
		boolean myCheck;
		
		char myValue;
		
		Node (char theValue, boolean theCheck) {
			myValue = theValue;
			myCheck = theCheck;
			
		}
		
		void setCheck(boolean theCheck) {
			myCheck = theCheck;
		}
		
		void setValue(char theValue) {
			myValue = theValue;
		}
	}		
}
	
	
	
	
	
