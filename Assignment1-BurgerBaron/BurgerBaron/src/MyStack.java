/**
 * Assignment 1 Burger Baron
 * @author Brandi Berg
 * @author Ryan Mowreader
 * TCSS 342 - Spring 2019 
 */

/*
 * Your assignment is to create a program that can take text orders in the format described 
 * and construct a custom burger with all ingredients in the proper order. Your program will 
 * implement the MyStack class as a linked structure that must function according to the 
 * following interface: 
 * 
 * The MyStack class must not use or extend any List type in Java or arrays. 
 * It must be built from scratch.
 */

/* This class will initialize a stack from scratch, using a linked list. */
public class MyStack<Type> {

	private int count;			//size of the stack
	private Node head;	//top of the stack

	/* • String toString() – this method converts the contents of the MyStack to a String for display. */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Node temp = head;
		while (temp != null) {
			sb.append(temp.toString());
			if(temp.next != null) {
				sb.append(", ");
			}
			temp = temp.next;
		}
		return ("[" + sb.toString() + "]" + "\n");
	}

	/* • int size() – this method returns the number of items in the MyStack. */
	public int size() {
		count = 0;
		Node temp = head;
		if(temp != null) {
			count++;
			temp = temp.next;
		}
		return count;
	}

	/* • Type peek() – this method returns the item on the top of the MyStack. */
	public Type peek() {
		return head.item;
	}

	/* • Type pop() – this method removes and returns the item on the top of the MyStack. */
	public Type pop() {
		Type temp = head.item;
		head = head.next;
		return temp;
	}

	/* • void push(Type item) – this method adds the item to the top of the MyStack .*/
	public void push(Type item) {
		if(head == null) {
			head = new Node(item, null);
		} else {
			head = new Node(item, head);
		}
	}

	/* • boolean isEmpty() – returns true if the MyStack is empty. */
	public boolean isEmpty() {	//need to throw an exception incase of the null stack. 
		if(head != null) {
			return false;
		}
		return true;
	}

	/* • MyStack <Type> () – a constructor that initializes an empty MyStack.*/
	public MyStack() {
		head = null;
		count = 0;
	}

	/* Helper Linked List class. */
	private final class Node{
		private Type item;
		private Node next;	

		@Override
		public String toString() {
			return item.toString();
		}
		
//		/* Returns the item. */
//		private Type getItem() {
//			return item;
//		}
		
		/* Constructs a node in the linked structure. */
		private Node(final Type theItem, final Node theNext) {
			item = theItem;
			next = theNext;
		}

	}
}








