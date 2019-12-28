/**
 * Assignment 1 Burger Baron
 * @author Brandi Berg
 * @author Ryan Mowreader
 * TCSS 342 - Spring 2019 
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class will run and test the program. 
 * @author Brandi Berg
 * @author Ryan Mowreader
 * @version 12 April 2019
 */
public class Main {

	private static int orderCount;

	/**
	 * This is an empty constructor. 
	 */
	public Main() {
		// does nothing
	}
	
	/**
	 * Test method for Burger. 
	 */
	public void testBurger() {
		System.out.println("Baron Burger Test:");
		System.out.println("Test Order: Baron Burger with no Sauce Tomato but Baron-Sauce");
		Burger burgerTestOne = new Burger(true);
		burgerTestOne.removeIngredient("Sauce");
		burgerTestOne.removeIngredient("Tomato");
		burgerTestOne.addIngredient("Baron-Sauce");
		System.out.println(burgerTestOne.toString());
		System.out.println();
		System.out.println("Burger Test:");
		System.out.println(
				"Test Order: Double Chicken Burger with Sauce Cheese Veggies but no Mustard");
		Burger burgerTestTwo = new Burger(false);
		burgerTestTwo.addPatty();
		burgerTestTwo.changePatties("Chicken");
		burgerTestTwo.addCategory("Sauce");
		burgerTestTwo.addCategory("Cheese");
		burgerTestTwo.addCategory("Veggies");
		burgerTestTwo.removeIngredient("Mustard");
		System.out.println(burgerTestTwo.toString());
		
		Burger burgerTestThree = new Burger(false);
		burgerTestThree.addPatty();
		System.out.println(burgerTestThree.toString());
	}

	
	/**
	 * Test method for MyStack. 
	 */
	public void testMyStack() {
		
		MyStack<Double> stack = new MyStack<Double>();
		System.out.println(stack.toString());
		stack.push(10.21);
		System.out.println(stack.toString());
		stack.push(-9.99);
		System.out.println(stack.toString());
		stack.pop();
		System.out.println(stack.toString());
		stack.pop();
		System.out.println(stack.toString());

	}
	
	/**
	 * Parses a line of input from the file and outputs the burger.
	 * @param line A line of input to be parsed.
	 * @throws IOException Constructs an IO exception with a specific message.
	 */
	public static void parseLine(String line) throws IOException {
		if (line.equals(""))
			return;
		int numOfPatties = 1;
		String pattyType = "Beef";
		boolean theWorks = false;
		Burger burger = null;
		String[] words = line.split(" ");
		for (String word : words) {
			if (word.equals("Baron")) {
				theWorks = true;
			}
			if (word.equals("Veggie") || word.equals("Chicken")) {
				pattyType = word;
			}
			if (word.equals("Double")) {
				numOfPatties = 2;
			}
			if (word.equals("Triple")) {
				numOfPatties = 3;
			}
		}
		burger = new Burger(theWorks);
		for (int i = 1; i < numOfPatties; i++) {
			burger.addPatty();
		}
		burger.changePatties(pattyType);
		outer: for (int i = 0; i < words.length; i++) {
			if (words[i].equals("with")) {
				if (words[i + 1].equals("no")) {
					for (int j = i + 2; j < words.length; j++) {
						if (words[j].equals("but")) {
							for (int k = j + 1; k < words.length; k++) {
								burger.addIngredient(words[k].toString());
							}
							break outer;
						}
						burger.removeIngredient(words[j]);
						burger.removeIngredient(words[j]);
					}
				} else {
					for (int j = i + 1; j < words.length; j++) {
						if (words[j].equals("but")) {
							for (int k = j + 2; k < words.length; k++) {
								burger.removeIngredient(words[k].toString());
							}
							break outer;
						}
						if (words[j].equals("Sauce") || words[j].equals("Cheese") || words[j].equals("Veggies")) {
							burger.addCategory(words[j]);
						} else {
							burger.addIngredient(words[j].toString());
						}
					}
				}
			}
		}
		System.out.println("Processing Order " + orderCount + ": " + line + "\n" + burger.toString() + "\n");
	}

	/**
	 * Static main method used to run the program and test the program elements.
	 * @param args Input arguments from User.
	 * @throws IOException throws IO if file isn't in source folder.
	 */
	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader("customer.txt"));
		while (reader.ready()) {
			String order = reader.readLine();
			parseLine(order);
			orderCount++;
		}
		reader.close();

	}

}

