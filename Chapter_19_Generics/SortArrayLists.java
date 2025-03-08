package Chapter_19_Generics;
/* 
 * Donna Bui - September 27, 2022 - Professor Henry Estrada's COMSC 076 @ EVC
 * This problem is based on Case Study 19.5: Sorting an Array of Objects on page 780 of the course textbook (11th Edition). 
 * It will sort 3 different types of ArrayLists (Integer, Double, and String) and print the values in order from lowest to highest.
 */
import java.util.*;

public class SortArrayLists {
	
	public static void main(String[] args) {
		/* 
		* Declare and initialize each ArrayList using Arrays.asList
		* This is more efficient than adding each value manually using ArrayList.add() for each value in each ArrayList
		* It also helps eliminate redundancy since we can get it done all in one line of code for each; 3 lines in total
		*/
		ArrayList<Integer> intArray = new ArrayList<>(Arrays.asList(2, 4, 3)); 
		ArrayList<Double> doubleArray = new ArrayList<>(Arrays.asList(3.4, 1.2, -12.3)); 
		ArrayList<String> stringArray = new ArrayList<>(Arrays.asList("Bob", "Alice", "Ted", "Carol")); 

		/* This is just for debugging
		* System.out.print("Original intArray: ");
		* printList(intArray);
		* System.out.print("Original doubleArray: ");
		* printList(doubleArray);
		* System.out.print("Original stringArray: ");
		* printList(stringArray);
		*/
		
		// Call the sorting method on each ArrayList
		sort(intArray);
		sort(doubleArray);
		sort(stringArray);
		
		// Print each of the sorted arrays
		System.out.print("Sorted intArray: ");
		printList(intArray);
		System.out.print("Sorted doubleArray: ");
		printList(doubleArray);
		System.out.print("Sorted stringArray: ");
		printList(stringArray);
	}
	
	public static <E extends Comparable<E>> 
		void sort(ArrayList<E> list) {
		
		// Declare variables
		E currentMin; // Element variable, uses the same letter as the one in <E extends Comparable<E>> and ArrayList<E>
		int currentMinIndex; // Stores the index of the "lowest" value in the list
		
		for (int i = 0; i < list.size() - 1; i++) {
			// If I were using normal Arrays, I would use currentMin = list[i];
			// Here, I use list.get(i) because I'm working with ArrayLists.
			currentMin = list.get(i); // Sets the value of currentMin to the value at index i 
			currentMinIndex = i; // Sets currentMinIndex to i  
		
			// Compares the next value in the list to the values we currently have above
			for (int j = i + 1; j < list.size(); j++) {
				if (currentMin.compareTo(list.get(j)) > 0) { // Checks if the value of currentMin (the value at index i) is greater than the value at index j in the ArrayList
					currentMin = list.get(j); // Sets the value of currentMin to the value at index j (the current lowest value) 
					currentMinIndex = j; // Sets the value of currentMinIndex to j (the index of the current lowest value)
				}
			}
		
		 // With normal Arrays, I would use something like list[currentMinIndex] = list[i];
		 // However, since I'm working with ArrayLists here, I need to use the list.set(index, value) method
			if (currentMinIndex != i) { // if currentMinIndex is not already equal to i, then:
				list.set(currentMinIndex, list.get(i)); // Set the value at currentMinIndex (which is j) to the value at index i (which comes from list.get(i)) 
				list.set(i, currentMin); // Set the old value at index i to currentMin (the value at index j)
				// This is done because the value at index i is greater than the value at index j, and the goal is to order the values from lowest to highest
				// Therefore by switching the two, it will be arranged such that the lower value will come before the greater value in the ArrayList.
				/** note: I'm only using "j" to help visualize what the value of currentMinIndex and currentMin might be. 
					If we were to try to actually use j as a variable, it wouldn't work because j is outside of the scope of the nested for loop. */
			} 
		}
	}
	
	// Method to print each value in an ArrayList
	public static <E extends Comparable<E>> // Include this in the method header to make it a Generic method
		void printList(ArrayList<E> list) { // The method will automatically assume the value type for the ArrayList
		for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i) + " "); // Iterates through each value in the list and prints it
		}
		System.out.println(); 
	}
}