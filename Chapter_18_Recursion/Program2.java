package Chapter_18_Recursion;
/* 
 * Donna Bui - September 21, 2022 - Professor Henry Estrada's COMSC 076 @ EVC
 * This exercise was from Programming Exercise 18.25 on page 746 of the course textbook (11th Edition). 
 * The program will prompt the user to enter a string and then print all permutations (variations) of the user's string.
 */
import java.util.Scanner;

public class Program2 {

	 public static void main(String[] args) {
		 // Creates a scanner
		 Scanner sc = new Scanner(System.in);
		
		 // Prompts user for a string and calls the first reverseDisplay method
		 System.out.print("Enter a string: ");
 		 String s = sc.nextLine();
 		 displayPermutation(s);
	 }
	
	 public static void displayPermutation(String s) {
		 displayPermutation("", s); // Invokes the second displayPermutation method
	 }

	 public static void displayPermutation(String s1, String s2) {
		 if (s2.isEmpty() == false) { // If s2 is not empty, it iterates through each character of s2 and recursively invokes itself
			 for (int i = 0; i < s2.length(); i++) {  
				 displayPermutation(s1 + s2.charAt(i), s2.substring(0, i) + s2.substring(i + 1));
			 } 
		 }
		 else { // If s2 is empty, it will print s1
			 System.out.println(s1); 
		 }  
	 }
}
