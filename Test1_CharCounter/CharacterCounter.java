package Test1;
/*
* Donna Bui - October 1, 2022 - Professor Henry Estrada's COMSC 076 @ EVC
* This program is for the second section of Test #1
* It will prompt the user to enter a valid character and starting index for a given array and then print the number of times the character occurs in the array.
*/
import java.util.Scanner;

public class CharacterCounter {
	
	 public static int charCount(char[] charArray, int start, char ch) { 
		if (start < charArray.length) { // Base case! 
		/* Since the start value serves as a value for the array's index, and it increases by 1 each time the recursive method is called, we want it to stop once it reaches the end of the array. 
		 * Otherwise, it'll go out of the array's range and the program will throw an ArrayIndexOutOfBoundsException
		 */
			if (charArray[start] == ch) { // This checks if the char in the array at the index [start] is equal to the character chosen by the user (char ch)
				return 1 + charCount(charArray, start + 1, ch); // Recursive call! It adds 1 to the returning integer (which is the # of times char ch occurs in the array) and increases the index (start) by 1 before calling itself again
			} 
			else { // If the char at index [start] doesn't match char ch, then it makes a recursive call without increasing the returning integer by 1, but still increases the index by 1
				return charCount(charArray, start + 1, ch); 
			}
		}
		return 0; // If the base case has been reached and no matching values were found, it will return 0
	}
	
	public static void main(String[] args) {
		
		// Variables
		Scanner sc = new Scanner(System.in);
		char[] charsArray = {'T', 'h', 'i', 's', ' ', 'i', 's', ' ', 't', 'h', 'e', ' ', 's', 't', 'r', 'i', 'n', 'g'}; // Length of this array is 18
		int start;
		
		// Prompt the user to enter a list of characters
		System.out.print("Enter any character: ");
		char ch = sc.next().charAt(0); // .charAt(0) makes sure we don't get more than 1 character from the input
		// It will only take the first character from the user's input even if they do enter more than 1 character
		
		// Ask user what index they want to start from
		System.out.println("What index do you want to start from in the array? ");
		System.out.print("Please enter a value between 0 and " + (charsArray.length - 1) + ": "); 
		// charsArray.length would return 18, but indexes start at 0 so we do (charsArray.length - 1) to make sure it stays between indexes 0-17
		start = sc.nextInt();
		
		// Checks if number is in the valid range (0-17 for the provided array)
		// If not between 0-17, ask user to enter a value again until they give a valid number
		while (start < 0 || start > charsArray.length - 1) {
			System.out.println(start + " is outside of the valid range.");
			System.out.print("Please enter a value between 0 and " + (charsArray.length - 1) + ": ");
			start = sc.nextInt();
		}
		
		// Print how many times the character occurs, starting from the given index to the end of the array
		System.out.println("Searching for \"" + ch + "\" between indexes " + start + " to " + (charsArray.length - 1) + "...");
		System.out.println("The character \"" + ch + "\" occurs " + charCount(charsArray, start, ch) + " time(s) in the array ");
		
		sc.close(); // close the scanner
	}
}