package Chapter_18_Recursion;
/* 
 * Donna Bui - September 21, 2022 - Professor Henry Estrada's COMSC 076 @ EVC
 * This exercise was from Programming Exercise 18.12 on page 744 of the course textbook (11th Edition). 
 * The program will prompt the user to enter a string and then print the user's string in reverse order.
 * It utilizes a recursive method that makes efficient use of memory by calling a helper method with the string's high index (length).
 */

import java.util.Scanner;

public class Program1 {

	public static void main(String[] args) {
		// Creates a scanner
		Scanner sc = new Scanner(System.in);
		
		// Prompts user for a string and calls the first reverseDisplay method
		System.out.print("Enter a string: ");
		String s = sc.nextLine();

		reverseDisplay(s);
		// For testing, call reverseDisplay("Able was I, I saw Elba");
		// Output of that should be: ablE was I ,I saw elbA
	}

	// Recursive reverseDisplay method - It will take the user's string and call the helper method on it
	public static void reverseDisplay(String s) {
		reverseDisplay(s, s.length() - 1); 
		// This will call the helper method with the user's string and the string's length - 1 
		// It calls s.length() - 1 instead of simply s.length() because the s.charAt() method starts at index 0 instead of 1.
		System.out.println(""); // Creates a new line for output in case we want to call this initial method more than once
	}

	// Helper method that includes the string's high index (basically the string's length; also could be interpreted as remaining characters).
	public static void reverseDisplay(String s, int high) {
		if (high >= 0) { //  This saves memory by stopping when the string's length is no longer greater than or equal to 0.
			System.out.print(s.charAt(high)); // Prints the character at the index
			reverseDisplay(s, high - 1); // Continues to call itself until all the characters in the string have been printed.
		}
	}
}
