/**
 * Assignment 1 Burger Baron
 * @author Brandi Berg
 * @author Ryan Mowreader
 * TCSS 342 - Spring 2019 
 */

/*
 * Your program will implement the Burger class using only the MyStack class that must 
 * function according to the following interface:  
 * 
 * The Burger class must not use any List type in Java or arrays. It must only use the MyStack 
 * class to store ingredients. Hint: You may use a “recipe” stack in your program that has the complete 
 * burger according to the recipe.
 */

public class Burger {

	/** Integer representation of the number of patties on a burger.*/
	private int myNumberOfPatties;

	/** A string representation of the type of patty on the burger.*/
	private String myPattyType;	

	/** A stack object created to hold recipe.*/
	private MyStack<String> myRecipeStack;

	/** A stack object created to hold ingredients.*/
	private MyStack<String> myIngredientsStack;

	/** A stack object created to hold orders.*/
	private MyStack<String> myOrderStack;

	/** A stack object created to hold random information.*/
	private MyStack<String> myDumpStack;


	/**
	 * Helper method to make burger.
	 */
	private void createRecipe() {
		myRecipeStack.push("Pickles");
		myRecipeStack.push("Bun");
		myRecipeStack.push("Mayonnaise");
		myRecipeStack.push("Baron-Sauce");
		myRecipeStack.push("Lettuce");
		myRecipeStack.push("Tomato");
		myRecipeStack.push("Onions");
		if (myNumberOfPatties > 1) {
			for (int i = 0; i < myNumberOfPatties-1; i++) {
				myRecipeStack.push(myPattyType);
			}
		}
		myRecipeStack.push("Pepperjack");
		myRecipeStack.push("Mozzarella");
		myRecipeStack.push("Cheddar");
		myRecipeStack.push(myPattyType);
		myRecipeStack.push("Mushrooms");
		myRecipeStack.push("Mustard");
		myRecipeStack.push("Ketchup");
		myRecipeStack.push("Bun");
	}

	/**
	 * Helper method returns a boolean if the burger has an ingredient.
	 * @param type an ingredient category.
	 * @return t/f based off whether or not the burger contains the ingredient.
	 */
	private boolean hasIngredient(String type) {
		boolean hasIngredient = false;
		while (!myIngredientsStack.isEmpty()) {
			if (type.equals(myIngredientsStack.peek())) {
				myIngredientsStack.pop();
				hasIngredient = true;
				break;
			}
			myDumpStack.push(myIngredientsStack.pop());
		}
		while (!myDumpStack.isEmpty()) {
			myIngredientsStack.push(myDumpStack.pop());
		}
		return hasIngredient;
	}


	/**
	 * This method converts the Burger to a String for display.
	 * ToString method override.
	 */
	@Override
	public String toString() {
		StringBuilder orderString = new StringBuilder();
		orderString.append("[");
		while (!myOrderStack.isEmpty()) {
			orderString.append(myOrderStack.pop());
			if (!myOrderStack.isEmpty()) {
				orderString.append(", ");
			} else {
				orderString.append("]");
			}
		}
		return orderString.toString();
	}

	/**
	 * This method adds the ingredient type to the Burger in the proper location. 
	 * @param type an ingredient category.
	 */
	public void addIngredient(String type) {
		myIngredientsStack.push(type);
		while (!myOrderStack.isEmpty()) {
			myIngredientsStack.push(myOrderStack.pop());
		}
		while (myRecipeStack.isEmpty() == false) {
			if (hasIngredient(myRecipeStack.peek())) {
				myOrderStack.push(myRecipeStack.pop());
			} else {
				myRecipeStack.pop();
			}
		}
		createRecipe();
	}

	/**
	 * This method adds all items of the type to the Burger in the proper locations. 
	 * @param type
	 */
	public void addCategory(String type) {
		if (type.equals("Sauce")) {
			myIngredientsStack.push("Mayonnaise");
			myIngredientsStack.push("Baron-Sauce");
			myIngredientsStack.push("Mustard");
			myIngredientsStack.push("Ketchup");
		} else if (type.equals("Cheese")) {
			myIngredientsStack.push("Cheddar");
			myIngredientsStack.push("Mozzarella");
			myIngredientsStack.push("Pepperjack");
		} else if (type.equals("Veggies")) {
			myIngredientsStack.push("Mushrooms");
			myIngredientsStack.push("Onions");
			myIngredientsStack.push("Tomato");
			myIngredientsStack.push("Lettuce");
			myIngredientsStack.push("Pickles");
		}
		while (!myOrderStack.isEmpty()) {
			myIngredientsStack.push(myOrderStack.pop());
		}
		while (myRecipeStack.isEmpty() == false) {
			if (hasIngredient(myRecipeStack.peek())) {
				myOrderStack.push(myRecipeStack.pop());
			} else {
				myRecipeStack.pop();
			}
		}
		createRecipe();
	}

	/**
	 * This method adds one patty to the burger up to a maximum of three patties.
	 */
	public void addPatty() {
		myNumberOfPatties++;
		while (!myRecipeStack.isEmpty()) {
			myRecipeStack.pop();
		}
		createRecipe();
		addIngredient(myPattyType);
	}

	/**
	 * This method changes all patties in the Burger to the patty type.
	 * @param pattyType the type of patty.
	 */
	public void changePatties(String pattyType) {
		if (pattyType == myPattyType) return;
		while (!myOrderStack.isEmpty()) {
			if (myOrderStack.peek().equals(myPattyType)) {
				myOrderStack.pop();
				myOrderStack.push(pattyType);
			} else {
				myDumpStack.push(myOrderStack.pop());
			}
		}
		while (!myDumpStack.isEmpty()) {
			myOrderStack.push(myDumpStack.pop());
		}
		myPattyType = pattyType;

		while (!myRecipeStack.isEmpty()) {
			myRecipeStack.pop();
		}
		createRecipe();
	}

	/**
	 * A constructor that initializes a Burger with only a bun and patty if the works is false
	 * otherwise a Baron Burger if the works is true.
	 * @param theWorks representation of a Baron Burger object.
	 */
	public Burger(boolean theWorks) {
		myNumberOfPatties = 1;
		myPattyType = "Beef";
		myRecipeStack = new MyStack<String>();
		myIngredientsStack = new MyStack<String>();
		myOrderStack = new MyStack<String>();
		myDumpStack = new MyStack<String>();		
		myIngredientsStack.push("Bun");
		myIngredientsStack.push(myPattyType);
		myIngredientsStack.push("Bun");

		if (theWorks) {
			addCategory("Sauce");
			addCategory("Cheese");
			addCategory("Veggies");
		}
		createRecipe();
		while (myRecipeStack.isEmpty() == false) {
			if (hasIngredient(myRecipeStack.peek())) {
				myOrderStack.push(myRecipeStack.pop());
			} else {
				myRecipeStack.pop();
			}
		}
		createRecipe();
	}

	/**
	 * This method removes the ingredient type from the Burger. 
	 * @param type an ingredient category.
	 */
	public void removeIngredient(String type) {
		while (!myOrderStack.isEmpty()) {
			if (type.equals(myOrderStack.peek())) {
				myOrderStack.pop();
				break;
			} else {
				myDumpStack.push(myOrderStack.pop());
			}
		}
		while (!myDumpStack.isEmpty()) {
			myOrderStack.push(myDumpStack.pop());
		}
	}
}



