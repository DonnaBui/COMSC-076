package Chapter_20_ListsStacksQueues;
/*
* Donna Bui - October 12, 2022 - Professor Henry Estrada's COMSC 076 @ EVC
* This program part of an assignment for Chapter 20 (Lists, Stacks, and Queues).
* It will create 100 Points with random x/y values and place them in an array, then apply the Arrays.sort method to display the
* points in increasing order of their x-coordinates, and increasing order of their y-coordinates, respectively.
*/
import java.util.*;	

public class SortPoints {							
	
	public static void main(String[] args) {

		Point[] points = new Point[100]; // create a Point array
		for (int i = 0; i < points.length; i++) {
			points[i] = new Point(Math.random()*100,Math.random()*100); // assign a point with random x and y values to each index in the array
		// the values go from 0-100 (non-inclusive)
		}

		Arrays.sort(points); // sorts the array based on its x-value
		List<Point> listX = Arrays.asList(points); // creates an ArrayList of type Point with the values in the now-sorted array of points
		System.out.println("Points sorted on x-coordinates:");
		for (int i = 0; i < listX.size(); i++) { // iterate through each value in the ArrayList to print all the coordinates
			System.out.println("(" + listX.get(i) + ")");
		}
		
		System.out.println(""); // space it out !

		Arrays.sort(points, new CompareY()); // sorts the array based on its y-value
		List<Point> listY = Arrays.asList(points);  // creates another ArrayList of type Point with the newly sorted values
		System.out.println("Points sorted on y-coordinates:");
		for (int i = 0; i < listY.size(); i++) { // similar to above, iterates through each value in the ArrayList to print all the coordinates
			System.out.println("(" + listY.get(i) + ")");
		}
	}
}
	
	 class Point implements Comparable<Point> {		
	
		 // variables
		double x; 
		double y;
		
		public Point(double x, double y) { // standard constructor
			this.x=x;
			this.y=y;
		}

		public double getX() { // standard getter method for X value
			return x;
		}		
		
		public double getY() { // standard getter method for Y value
			return y;
		}		
			
		public String toString() { // standard toString method to print the values
			return x + "," + y;		
		}
		
		public int compareTo(Point p) {	// compareTo method for Arrays.sort - compares the values of this point to that of Point p based on x values first
			
			if (x == p.getX()) { // if both X values are equal, compare their Y values
				if (y > p.getY())
					return 1; // if y is greater than Y of Point p, return 1
				else if (y < p.getY())
					return -1; // if y is less than Y of Point p, return -1
				else
					return 0; // if y is equal to Y of Point p, return 0
			}
			else if (x > p.getX()) 
					return 1; // if x is greater than X of Point p, return 1
			else return -1; // if x is not greater than X of Point p, return -1
		}
	}
	
	class CompareY implements Comparator<Point>	{

		public int compare(Point p1, Point p2) { // compare method for Arrays.sort - compares the values of 2 points based on y values first		
			if (p1.getY() == p2.getY()) { // if both Point's Y values are equal, compare their X values
				if (p1.getX() > p2.getX())
					return 1; // if x1 is greater than x2, return 1
				else if (p1.getX() < p2.getX())
					return -1; // if x1 less than x2 of Point p, return -1
				else
					return 0; // if x1 is equal to x2 of Point p, return 0
			}
			else if (p1.getY() > p2.getY()) 
					return 1; // if y1 is greater than y2 of Point p, return 1
			else 
				return -1; // if y1 is not greater than y2 of Point p, return -1
		}

	}
	
