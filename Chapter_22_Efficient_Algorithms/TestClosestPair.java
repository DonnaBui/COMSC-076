package Chapter_22_Efficient_Algorithms;
/*
* Donna Bui - October 27, 2022 - Professor Henry Estrada's COMSC 076 @ EVC
* This program part of an assignment for Chapter 22 (Developing Efficient Algorithms).
* It will create 100 Points with random x/y values and place them in an array denoted by S, then determine which pair of two points have the closest distance.
* It contains 4 classes: TestClosestPair, Pair, Point, and CompareY. The last two are code from an assignment for Chapter 20. 
* It will print the two closest Points, their distance, and the program's execution time.
*/
import java.util.*;

public class TestClosestPair {
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis(); // execution start time
		Random r = new Random();
		r.setSeed(777);
		Point[] S = new Point[100]; // create a Point array
		// System.out.println("List of Points: "); // this is for debugging. it simply prints all the randomly generated points
		for (int i = 0; i < S.length; i++) {
			S[i] = new Point(r.nextDouble()*100,r.nextDouble()*100); // slightly modified code taken from the assignment for Chapter 20
			// System.out.println(S[i]); // also for debugging; remove the // slashes at the beginning of the line to view all the generated points
		}
		Pair closestPair = Pair.getClosestPair(S);
		long end = System.currentTimeMillis();
		double closestDistance = closestPair.getDistance();

		System.out.println("The shortest distance is " + closestDistance + " between the points " + closestPair.getP1() + " and " + closestPair.getP2());
		
		// End time; subtract the end and start times to get the total execution time in milliseconds
		System.out.print("Time spent running the divide-and-conquer algorithm is " + (end - start) + " milliseconds");
		// First time ran was 114 milliseconds; second time was 34 and third time was 11 milliseconds
	}
}

class Pair { 
	
	/** Standard variables */
	Point p1;
	Point p2;
	double distance;
	
	/** Standard constructor */
	public Pair(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	/** Return the distance of the two points in this pair */
	public double getDistance() {
		if (p1 != null && p2 != null) { // make sure both of the Points exist 
			this.distance = distance(p1,p2);
			return distance;
		}
		else { 	
			System.out.println("Cannot get distance; Pair does not contain two valid Points.");
			return 0;
		}
	}
		
	/** Standard setter and getter methods */
    public Point getP1() {
        return p1;
    }
    
    public Point getP2() {
        return p2;
    }
    
    public void setP1(Point p1) {
        this.p1 = p1;
    }
    
    public void setP2(Point p2) {
        this.p2 = p2;
    }
	
 // The easier stuff 
 	/** Compute the distance between two points p1 and p2 */
 	public static double distance(Point p1, Point p2) {
 		return distance(p1.getX(), p1.getY(), p2.getX(), p2.getY()); // overloaded method - uses the getter methods from the Point class we already have and calls the other version of distance() using the x/y values from each Point
 		// same as calling distance(x1, y1, x2, y2); except it's using getter methods to get the values from the Point
 	}

 	/** Compute the distance between points (x1, y1) and (x2, y2) */
 	public static double distance(double x1, double y1, double x2, double y2) {
 		return Math.sqrt((x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1)); // distance formula: sqrt of [(x2-x1)^2 + (y2-y1)^2]
 		// x1 and y1 belong to point 1, x2 and y2 belong to point 2
 	}
 	
 	
	/** Return the distance of the closest pair of points */
	public static Pair getClosestPair(double[][] doubleArr) { // Here, the parameter is a 2D array containing values for coordinates
		Point[] pointArray = new Point[doubleArr.length]; // Since we're using this method to convert a double array to a point array, we can just make the Point array a regular array instead of a 2D array.
		// This is because a Point object already has two values: an x double and a y double, so we will take the values from the double array and use them to create Point objects to be added to the Point array
		for (int i = 0; i < doubleArr.length; i++) { 
			pointArray[i] = new Point(doubleArr[i][0], doubleArr[i][1]);
			// Think of the double Array like this:  (double[row][column] doubleArr)
			// Each row in the double Array corresponds to a pair of values/coordinates in a Point: column 0 contains the x values, while column 1 contains the y values.
			// The for-loop will go through each row in the double Array and take those x/y values from the columns and use them to create and add a Point to the Point array.
		}
		return getClosestPair(pointArray);
		// This is an overloaded method; now that the double array has been converted to a Point array, we're going to call the other version of this method which takes a Point array as a parameter
		// The other version of this method is the one that will sort the Point array here and call yet another overloaded method to return the closest pair of points.
	}

	
	/** Return the distance of the closest pair of points */
	// Overloaded getClosestPair() method. Takes a Point array, sorts it on X and Y values, then calls the overloaded recursive distance() method using the two sorted Point arrays
	public static Pair getClosestPair(Point[] points) { 
		// Makes a copy of the original Point array and sorts it based on X values
		Point[] pointsOrderedOnX = points.clone(); 
		Arrays.sort(pointsOrderedOnX); 
		// Makes another copy of the original Point array and sorts it based on Y values. This will come in handy later.
		Point[] pointsOrderedOnY = points.clone(); 
		Arrays.sort(pointsOrderedOnY, new CompareY());
		
		return distance(pointsOrderedOnX, 0, points.length - 1, pointsOrderedOnY);
		// The two parameters in between are the low and high indexes (low = 0, high = points.length - 1). We want the recursive method to start at index 0 and stop when it reaches the last index of the Point array.
		// Remember that .length always returns 1 more than the last index of an array, so we have to subtract 1 or else it'll go out of bounds.
	}

	 
	// Here's the hard part
	/** Return the distance of the closest pair of points in pointsOrderedOnX[low, high]. This is a recursive method. pointsOrderedOnX and pointsOrderedOnY are not changed in the subsequent recursive calls.*/
	public static Pair distance(Point[] pointsOrderedOnX, int low, int high, Point[] pointsOrderedOnY) {
		
		// Base case 1: The starting index (low) is either invalid OR there are less than two points in the array, resulting in the start/end indexes (low/high) being equal.
		if (low >= high) { 
            return null; // If the start index is invalid/there are less than 2 points to check, it will return nothing (null).
        } 
		
		// Base case 2: There are only 2 points in the array OR only two points remain in the array, so if you add 1 to the starting index (low) you will reach the end index (high).
		else if (low + 1 == high) { 
			// Since there are only two points in the array, those 2 will automatically be the closest pair of points, and this base case will return a Pair containing those two Points. 
            return new Pair(pointsOrderedOnX[low], pointsOrderedOnX[high]);
        } 
		
		// Case 3: There are more than two points in the array, so the method will recursively call itself and use the divide-and-conquer approach until the array is reduced to 2 values or a base case.
		else { 
            int mid = (low + high) / 2; // Approximately finds the middle index of the array. We will use this value to split the array into two parts (S1 and S2) and recursively reduce it down to two Pairs (four Points in total).
            Pair S1 = distance(pointsOrderedOnX, low, mid, pointsOrderedOnY); // Recursive method, it starts at index low (usually 0; the start of an array) and ends at index mid, which we previously calculated using (low+high)/2
            Pair S2 = distance(pointsOrderedOnX, mid + 1, high, pointsOrderedOnY); // same as S1, but it starts immediately after index mid and ends at index high, which is the last index in the array
            // S1 checks all Points on the left half of the array, while S2 checks all Points on the right half of the array.
              
            double closestDistance;
            Pair closestPair;
            
         // Subcase 1: Both S1 and S2 are not valid Pairs, so the closestDistance will automatically be 0 and the closestPair will be null.
            if (S1 == null && S2 == null) {  
            	closestDistance = 0;
                closestPair = null;
            }
            
        // Subcase 2: if there is no valid pair on the left side (S1), and S2 is a valid Pair, then the right pair (S2) is automatically the closest
            else if (S1 == null && S2 != null) {  // The closestDistance and closestPair values will be set to the values of S2
            	closestDistance = S2.getDistance(); 
            	closestPair = S2;
    		} 
            
        // Subcase 3: if there is no valid pair on the right side (S2), and S1 is a valid Pair, then the left pair (S1) is automatically the closest
            else if (S2 == null && S1 != null) { // The closestDistance and closestPair values will be set to the values of S1
            	closestDistance = S1.getDistance(); 
            	closestPair = S1;
    		} 
            
        // Subcase 4: Both S1 and S2 are valid Pairs, so proceed to calculate which of the two Pairs have the closer distance
            else { 
    			if (S1.getDistance() <= S2.getDistance()) { // If S1 has a smaller distance than S2, then S1 has the closer distance
    				closestDistance = S1.getDistance(); 
                	closestPair = S1;
 
    			}
    			else { // If S2 has a smaller distance than S1, then S2 has the closer distance
    				closestDistance = S2.getDistance(); 
                	closestPair = S2;
                	
    			}	
    		}
         

          /** Implementing the pseudocode provided in the assignment for Obtaining stripL and stripR. */
            // It just cross-checks each Point in pointsOrderedOnX with the Points in pointsOrderedOnY.
            
            List<Point> stripL = new ArrayList<>();
            List<Point> stripR = new ArrayList<>();
            
            /* 	Pseudocode for Obtaining stripL and stripR algorithm:
    		 * 
    		 * 	Line 1:	 for each point p in pointsOrderedOnY 			 
    		 *	Line 2:		if (p is in S1 and mid.x – p.x <= d) 		  
    		 *	Line 3:			append p to stripL;						 
    		 *	Line 4:		else if (p is in S2 and p.x - mid.x <= d)	  
    		 *	Line 5:			append p to stripR;						 
    		 *
    		 */ /** Note: my comments in the following code refer to the lines in the pseudocode above. */
          
            for (Point p : pointsOrderedOnY) {  // Line 1 (in the pseudocode)
                if (p.getX() <= pointsOrderedOnX[mid].getX() && pointsOrderedOnX[mid].getX() - p.getX() <= closestDistance) { // Line 2: stripL contains Points in S1 that have a smaller distance than the current closestDistance
                    stripL.add(p); // Line 3: adds the point p to stripL
                } 
                else if (p.getX() > pointsOrderedOnX[mid].getX() && p.getX() - pointsOrderedOnX[mid].getX() <= closestDistance) { // Line 4: stripR contains Points in S2 that have a smaller distance than the current closestDistance
                    stripR.add(p); // Line 5: adds the point p to stripR
                }
            }
            
            /*
             * 	Pseudocode for Finding the Closest Pair:
    		 *  
    		 * 	Line 1:	  d = min(d1, d2); 
    		 * 	Line 2:	  r = 0;  // r is the index of a point in stripR
    		 * 	Line 3:	  for (each point p in stripL) {	 
    		 * 	Line 4:		  while (r < stripR.length && q[r].y <= p.y - d)  // Skip the points in stripR below p.y - d
    		 *  Line 5:			  r++;
    		 *  Line 6:		  let r1 = r;
    		 *  Line 7: 		  while (r1 < stripR.length && |q[r1].y  - p.y| <=  d) { 
    		 *  Line 8: 			  if (distance(p, q[r1]) < d) {  // Check if (p, q[r1] is a possible closest pair  
    		 *  Line 9:					  d = distance(p, q[r1]);
    		 *  Line 10: 				  (p, q[r1]) is now the current closest pair;
    		 *  Line 11: 			  }
    		 *  Line 12:	 		  r1 = r1 + 1;
    		 *  				  }
    		 * 			  }
    		 */ /** Note: my comments in the following code refer to the lines in the pseudocode above. */
            
            // Line 1: no need for the variable d here since we already have the variable closestDistance defined above; they would both be the same thing
            int r = 0; // Line 2: r is the index of a point in stripR
            for (Point p : stripL) { //	Line 3: iterates through each value in stripL
                while (r < stripR.size() && stripR.get(r).getY() <= p.getY() - closestDistance) { // Line 4: while loop to skip the points in stripR below p.y - d
                    r++; // Line 5: remember that r is the index of a point in stripR. we're using this while loop to iterate through each value (each Point) in the List stripL.
                }
                int r1 = r; // Line 6: makes a second variable equivalent to the current r index. Now we're starting at the index where we left off in stripL
                while (r1 < stripR.size() && Math.abs(stripR.get(r1).getY() - p.getY()) <= closestDistance) { // Line 7: while-loop runs until we either reach the end of stripR or we encounter two points that have a greater/equal distance to the current closestDistance
                	
                    if (distance(p, stripR.get(r1)) < closestDistance) { // Line 8: Checks the distance of the points p and stripR[r1] (the Point at index r1 in stripR)
                    	closestDistance = distance(p, stripR.get(r1)); // Line 9: Sets the new closestDistance to the distance between point P and strip[r1]
                    	closestPair = new Pair(p, stripR.get(r1)); // Line 10: Sets the new closestPair
                    }
                    r1++; // Line 12: increases the value of r1 (the index) by one after it runs through the if-statement
                }
            }
            return closestPair; // After everything is done, this overloaded distance() method will return the closestPair (woah, that was a lot!)
        }
    }
}



/** Everything below includes the same classes and methods as ones we made for the assignment for Chapter 20 (Lists, Stacks, and Queues) */

class Point implements Comparable<Point> {		
	
	 // variables
	double x; 
	double y;
	
	public Point(double x, double y) { // standard constructor
		this.x = x;
		this.y = y;
	}

	public double getX() { // standard getter method for X value
		return x;
	}		
	
	public double getY() { // standard getter method for Y value
		return y;
	}		
		
	public String toString() { // standard toString method to print the values
		return "(" + x + ", " + y + ")";		
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