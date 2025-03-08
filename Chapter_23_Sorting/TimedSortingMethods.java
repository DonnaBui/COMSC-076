package Chapter_23_Sorting;
/*
* Donna Bui - November 2, 2022 - Professor Henry Estrada's COMSC 076 @ EVC
* This program part of an assignment for Chapter 23 (Sorting).
* It will create 6 arrays of different sizes ranging from 50 thousand to 300 thousand and run various sorting methods on the arrays with random values each time.
* It will implement Selection Sort, Merge Sort, Quick Sort, and Heap Sort methods from the textbook and print a table of each methods' execution times for varying array sizes.
*/
public class TimedSortingMethods {

	public static void main (String[] args){

		// Arrays of different sizes to run sorting tests on.
		int array50k [] = new int[50000];
		int array100k [] = new int[100000];
		int array150k [] = new int[150000];
		int array200k [] = new int[200000];
		int array250k [] = new int[250000];
		int array300k [] = new int[300000];

		// (Note: \t is formatted/printed differently for each IDE.
		/** I am using Eclipse and it looks fine, but it may appear different when used on a different IDE. */
		// On a different IDE, two \t might be needed for it to be properly spaced instead of just one like with Eclipse.
		
		// Prints the table for execution time. 
		System.out.println("Array Size \t| \tSelection \tMerge \tQuick \tHeap \tRadix");
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("50,000  \t|" + " \t " + selectionSort(array50k)  + "\t\t " + mergeSort(array50k)  + "\t " + quickSort(array50k)  + "\t " + heapSort(array50k) + "\t " + radixSort(array50k) );
		System.out.println("100,000 \t|" + " \t " + selectionSort(array100k) + "\t\t " + mergeSort(array100k) + "\t " + quickSort(array100k) + "\t " + heapSort(array100k)+ "\t " + radixSort(array100k) );
		System.out.println("150,000 \t|" + " \t " + selectionSort(array150k) + "\t\t " + mergeSort(array150k) + "\t " + quickSort(array150k) + "\t " + heapSort(array150k)+ "\t " + radixSort(array150k) );
		System.out.println("200,000 \t|" + " \t " + selectionSort(array200k) + "\t\t " + mergeSort(array200k) + "\t " + quickSort(array200k) + "\t " + heapSort(array200k)+ "\t " + radixSort(array200k) );
		System.out.println("250,000 \t|" + " \t " + selectionSort(array250k) + "\t\t " + mergeSort(array250k) + "\t " + quickSort(array250k) + "\t " + heapSort(array250k)+ "\t " + radixSort(array250k) );
		System.out.println("300,000 \t|" + " \t " + selectionSort(array300k) + "\t\t " + mergeSort(array300k) + "\t " + quickSort(array300k) + "\t " + heapSort(array300k)+ "\t " + radixSort(array300k) );
		
		/* Output - Specs: HP Pavilion Laptop 15-cs0xxx, 16 GB Ram, Intel Core i5-8520U CPU @ 1.60 GHz
		 
	 	Array Size 	| 	Selection 	Merge 	Quick 	Heap 	Radix
		-----------------------------------------------------------
		50,000  	| 	 472		 29		 34	 	 70	 	 8
		100,000 	| 	 1736		 56	 	 17	 	 70	 	 5
		150,000 	| 	 4151		 81	 	 31	 	 79	 	 8
		200,000 	| 	 6875		 117	 53	 	 131	 10
		250,000 	| 	 18289		 383	 197	 387	 21
		300,000 	| 	 22548		 179	 110	 232	 15
		 
		 */
	}

	// Method to generate random values from 0-1000 for an array */
	public static void getRandomValues(int [] arr) { 
		for (int i = 0; i < arr.length; i++) {
			double temp = Math.random() * 1000;
			arr[i] = (int) temp;
		}
	}

	// Method to reduce redundancy when calculating execution time for each sorting method
	public static long getExecutionTime(long start, long end) { 
		long executionTime = end - start; // Calculate the execution time
		return executionTime;
	}


	/** Slightly modified source code from textbook chapter 7: SelectionSort.java */
	public static long selectionSort(int[] arr) {

		getRandomValues(arr); // Generate random values for the array
		long start = System.currentTimeMillis(); // Start time

		for (int i = 0; i < arr.length - 1; i++) {
			// Find the minimum in the arr[i..arr.length-1]
			int currentMin = arr[i];
			int currentMinIndex = i;

			for (int j = i + 1; j < arr.length; j++) {
				if (currentMin > arr[j]) {
					currentMin = arr[j];
					currentMinIndex = j;
				}
			}

			// Swap arr[i] with arr[currentMinIndex] if necessary;
			if (currentMinIndex != i) {
				arr[currentMinIndex] = arr[i];
				arr[i] = currentMin;
			}
		}
		long end = System.currentTimeMillis(); // End time
		return getExecutionTime(start, end); // Return execution time
	}

	/** Slightly modified source code from textbook chapter 23: MergeSort.java */
	public static long mergeSort(int[] arr) {

		getRandomValues(arr); // Generate random values for the array
		long start = System.currentTimeMillis(); // Start time

		if (arr.length > 1) {
			// Merge sort the first half
			int[] firstHalf = new int[arr.length / 2];
			System.arraycopy(arr, 0, firstHalf, 0, arr.length / 2);
			mergeSort(firstHalf);

			// Merge sort the second half
			int secondHalfLength = arr.length - arr.length / 2;
			int[] secondHalf = new int[secondHalfLength];
			System.arraycopy(arr, arr.length / 2,
					secondHalf, 0, secondHalfLength);
			mergeSort(secondHalf);

			// Merge firstHalf with secondHalf into arr
			merge(firstHalf, secondHalf, arr);
		}

		long end = System.currentTimeMillis(); // End time
		return getExecutionTime(start, end); // Return execution time
	}
	// Merge two sorted arrays 
	public static void merge(int[] arr1, int[] arr2, int[] temp) {
		int current1 = 0; // Current index in arr1
		int current2 = 0; // Current index in arr2
		int current3 = 0; // Current index in temp

		while (current1 < arr1.length && current2 < arr2.length) {
			if (arr1[current1] < arr2[current2])
				temp[current3++] = arr1[current1++];
			else
				temp[current3++] = arr2[current2++];
		}

		while (current1 < arr1.length)
			temp[current3++] = arr1[current1++];

		while (current2 < arr2.length)
			temp[current3++] = arr2[current2++];
	}


	/** Slightly modified source code from textbook chapter 23: QuickSort.java */
	public static long quickSort(int[] arr) {
		getRandomValues(arr); // Generate random values for the array
		long start = System.currentTimeMillis(); // Start time
		
		quickSort(arr, 0, arr.length - 1); // Call the recursive overloaded method
		
		long end = System.currentTimeMillis(); // End time
		return getExecutionTime(start, end); // Return execution time
	}
	// Recursive method; similar to divide and conquer
	private static void quickSort(int[] arr, int first, int last) {
		if (last > first) {
			int pivotIndex = partition(arr, first, last);
			quickSort(arr, first, pivotIndex - 1);
			quickSort(arr, pivotIndex + 1, last);
		}
	}
	// Partition the array arr[first..last] 
	private static int partition(int[] arr, int first, int last) {
		int pivot = arr[first]; // Choose the first element as the pivot
		int low = first + 1; // Index for forward search
		int high = last; // Index for backward search

		while (high > low) {
			// Search forward from left
			while (low <= high && arr[low] <= pivot)
				low++;

			// Search backward from right
			while (low <= high && arr[high] > pivot)
				high--;

			// Swap two elements in the arr
			if (high > low) {
				int temp = arr[high];
				arr[high] = arr[low];
				arr[low] = temp;
			}
		}

		while (high > first && arr[high] >= pivot)
			high--;

		// Swap pivot with arr[high]
		if (pivot > arr[high]) {
			arr[first] = arr[high];
			arr[high] = pivot;
			return high;
		}
		else {
			return first;
		}
	}	  

	/** Slightly modified source code from textbook chapter 23: HeapSort.java */
	  public static long heapSort(int[] arr) {
		  
		  getRandomValues(arr);
		  long start = System.currentTimeMillis(); // Start time
			
	    // Create a Heap of integers
	    Heap<Integer> heap = new Heap<>();

	    // Add elements to the heap
	    for (int i = 0; i < arr.length; i++)
	      heap.add(arr[i]);

	    // Remove elements from the heap
	    for (int i = arr.length - 1; i >= 0; i--)
	    	arr[i] = heap.remove();
	    
	    long end = System.currentTimeMillis(); // End time
		return getExecutionTime(start, end); // Return execution time
	  }
	  
	  
	  /** Radix sort helper method */
	  static long radixSort(int arr[]) {
		 getRandomValues(arr); // Generate random values for the array
		long start = System.currentTimeMillis(); // Start time
		
		radixSort(arr, arr.length); // Overloaded method
		
		long end = System.currentTimeMillis(); // End time
		return getExecutionTime(start, end); // Return execution time
	  }
	  /** Radix sort using countingSort method */
	  static void radixSort(int arr[], int size) { 

		// Get maximum element
		int max = getMax(arr, size);

		// Apply counting sort to sort elements based on place value.
		for (int place = 1; max / place > 0; place *= 10)
			countingSort(arr, size, place);

	}
	
	static int getMax(int arr[], int n) {
		int max = arr[0];
		for (int i = 1; i < n; i++)
			if (arr[i] > max)
				max = arr[i];
		return max;
	}
	
	static void countingSort(int arr[], int size, int place) {
		int[] output = new int[size + 1];
		int max = arr[0];
		for (int i = 1; i < size; i++) {
			if (arr[i] > max)
				max = arr[i];
		}
		int[] count = new int[max + 1];

		for (int i = 0; i < max; ++i)
			count[i] = 0;

		// Calculate count of elements
		for (int i = 0; i < size; i++)
			count[(arr[i] / place) % 10]++;

		// Calculate cumulative count
		for (int i = 1; i < 10; i++)
			count[i] += count[i - 1];

		// Place the elements in sorted order
		for (int i = size - 1; i >= 0; i--) {
			output[count[(arr[i] / place) % 10] - 1] = arr[i];
			count[(arr[i] / place) % 10]--;
		}

		for (int i = 0; i < size; i++)
			arr[i] = output[i];
	}
}





/** Slightly modified Heap Class source code from textbook chapter 23: Heap.java */
class Heap<E extends Comparable<E>> {
	  private java.util.ArrayList<Integer> list = new java.util.ArrayList<>();

	  /** Create a default heap */
	  public Heap() {
	  }

	  /** Create a heap from an array of objects */
	  public Heap(int[] objects) {
	    for (int i = 0; i < objects.length; i++)
	      add(objects[i]);
	  }

	  /** Add a new object into the heap */
	  public void add(int newObject) {
	    list.add(newObject); // Append to the heap
	    int currentIndex = list.size() - 1; // The index of the last node

	    while (currentIndex > 0) {
	      int parentIndex = (currentIndex - 1) / 2;
	      // Swap if the current object is greater than its parent
	      if (list.get(currentIndex).compareTo(
	          list.get(parentIndex)) > 0) {
	    	  int temp = list.get(currentIndex);
	        list.set(currentIndex, list.get(parentIndex));
	        list.set(parentIndex, temp);
	      }
	      else
	        break; // the tree is a heap now

	      currentIndex = parentIndex;
	    }
	  }

	  /** Remove the root from the heap */
	  public int remove() {
	    if (list.size() == 0) return -1;

	    int removedObject = list.get(0);
	    list.set(0, list.get(list.size() - 1));
	    list.remove(list.size() - 1);

	    int currentIndex = 0;
	    while (currentIndex < list.size()) {
	      int leftChildIndex = 2 * currentIndex + 1;
	      int rightChildIndex = 2 * currentIndex + 2;

	      // Find the maximum between two children
	      if (leftChildIndex >= list.size()) break; // The tree is a heap
	      int maxIndex = leftChildIndex;
	      if (rightChildIndex < list.size()) {
	        if (list.get(maxIndex).compareTo(
	            list.get(rightChildIndex)) < 0) {
	          maxIndex = rightChildIndex;
	        }
	      }

	      // Swap if the current node is less than the maximum
	      if (list.get(currentIndex).compareTo(
	          list.get(maxIndex)) < 0) {
	    	  int temp = list.get(maxIndex);
	        list.set(maxIndex, list.get(currentIndex));
	        list.set(currentIndex, temp);
	        currentIndex = maxIndex;
	      }
	      else
	        break; // The tree is a heap
	    }

	    return removedObject;
	  }

	  /** Get the number of nodes in the tree */
	  public int getSize() {
	    return list.size();
	  }
	  
	  /** Return true if heap is empty */
	  public boolean isEmpty() {
	    return list.size() == 0;
	  }
	}