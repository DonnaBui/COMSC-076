package TriangleObject_Exercise_13_1_Pg_535;

/* 
 * Donna Bui - September 3, 2022 - Professor Henry Estrada's COMSC 076 @ EVC
 * This exercise was from Programming Exercise 13.1 on page 535 of the course text (11th Edition). 
 * It will prompt the user for a String value (the triangle's color), a Boolean value (whether the triangle is filled), 
 * and 3 numbers (as sides of the triangle) and validate if those 3 numbers form a triangle.
 * The program will then create a Triangle object with the given values and print the properties (area, perimeter, color, filled boolean)
 * It will also create a Circle & Rectangle object using a constructor w/ parameters and print the respective properties
 */

import java.util.Scanner;

public class TestGeometricObjects {  
	
	public static void main (String []  args) {

		// variable declarations
		Scanner sc = new Scanner(System.in);
		String color;
		boolean filled;
		double s1;
		double s2;
		double s3;
	 
		// Prompt user for 3 values for sides
		System.out.println("Enter the sides of a triangle: ");
		s1 = sc.nextDouble();
		s2 = sc.nextDouble();
		s3 = sc.nextDouble();
	 
		// Loop to verify if the 3 values form a valid triangle
		while (s1 > s2 + s3 || s2 > s1 + s3 || s3 > s1 + s2) {
		 	System.out.println("Unable to create a triangle with those sides.");
		 	System.out.println("Enter the sides of a triangle: ");
		 	s1 = sc.nextDouble();
		 	s2 = sc.nextDouble();
		 	s3 = sc.nextDouble();
		}
		
		// After condition is met, proceed to ask user for color & filled values
		System.out.println("Enter a color: ");
		sc.nextLine();
		color = sc.nextLine();
		
		System.out.println("Enter (true/false) for whether the triangle is filled: ");
		filled = sc.nextBoolean();
		
		// Use a constructor to build a Triangle object with user-given values
		GeometricObject Triangle = new Triangle(s1, s2, s3, color, filled);

		// Use a constructor with parameters to build circle Object, instead of reading in the data 
		GeometricObject Circle = new Circle(2, "yellow", true);
	 
		// Use a constructor to with parameters to build a Rectangle object, instead of reading in the data
		GeometricObject Rectangle = new Rectangle(2,4, "red", false);
	
		// Output the results for all three geometric objects: see assignment for a sample format
		System.out.println(Triangle.getInfo());
		System.out.println(Circle.getInfo());
		System.out.println(Rectangle.getInfo());
	 
		// closing the scanner! :)
		sc.close();
 	 }

} 

// Source code from text - changed toString method to getInfo
abstract class GeometricObject {
	  private String color = "none";
	  private boolean filled = false;
	  private java.util.Date dateCreated;

	  /** Construct a default geometric object */
	  protected GeometricObject() {
	    dateCreated = new java.util.Date();
	  }

	  /** Construct a geometric object with color and filled value */
	  protected GeometricObject(String color, boolean filled) {
	    dateCreated = new java.util.Date();
	    this.color = color;
	    this.filled = filled;
	  }

	  /** Return color */
	  public String getColor() {
	    return color;
	  }

	  /** Set a new color */
	  public void setColor(String color) {
	    this.color = color;
	  }

	  /** Return filled. Since filled is boolean,
	   *  the get method is named isFilled */
	  public boolean isFilled() {
	    return filled;
	  }

	  /** Set a new filled */
	  public void setFilled(boolean filled) {
	    this.filled = filled;
	  }

	  /** Get dateCreated */
	  public java.util.Date getDateCreated() {
	    return dateCreated;
	  }

	  public String getInfo() {
	    return "created on " + dateCreated + "\ncolor: " + color +
	      " and filled: " + filled;
	  }

	  /** Abstract method getArea */
	  public abstract double getArea();

	  /** Abstract method getPerimeter */
	  public abstract double getPerimeter();
	}

// Triangle class - pretty similar to circle and rectangle class 
class Triangle extends GeometricObject {    
	private double s1;
	private double s2;
	private double s3;
 
	public Triangle() { // default constructor; gives default values if user doesn't provide any 
		this.s1 = 0;
		this.s2 = 0;
		this.s3 = 0;
	}
	
	public Triangle(double s1, double s2, double s3, String color, boolean filled) { 
		this.s1 = s1;
		this.s2 = s2;
		this.s3 = s3;
		setColor(color);
	    setFilled(filled);
	}

	@Override
	public double getArea() {
		double s = (s1 + s2 + s3)/2;
		double area = Math.sqrt(s*(s-s1)*(s-s2)*(s-s3)); 
		return Math.round(area * 100) / 100d;
	}

	@Override
	public double getPerimeter() {
		return (s1 + s2 + s3);
	}
	
	@Override
	public String getInfo() {
		return "\nTriangle Info: "
				+ "\nSides: " + s1 + ", " + s2 + ", " + s3
				+ "\nArea: " + getArea()
				+ "\nPerimeter: " + getPerimeter()
				+ "\nColor: " + getColor()
				+ "\nFilled: " + isFilled()
				;
	}
}

class Circle extends GeometricObject { // source code from text - modified to add getInfo method and round decimals to 2 digits
	
	  private double radius;

	  public Circle() { // default constructor; gives default values if user doesn't provide any 
		  this.radius = 0;
	  }

	  // modified to add color & fill values
	  public Circle(double radius, String color, boolean filled) {
	    this.radius = radius;
	    setColor(color);
	    setFilled(filled);
	  }

	  /** Return radius */
	  public double getRadius() {
	    return radius;
	  }

	  /** Set a new radius */
	  public void setRadius(double radius) {
	    this.radius = radius;
	  }

	  @Override /** Return area */
	  public double getArea() {
	    return Math.round(radius * radius * Math.PI * 100) / 100d;
	  }

	  /** Return diameter */
	  public double getDiameter() {
	    return Math.round(2 * radius * 100) / 100d;
	  }

	  @Override /** Return perimeter */
	  public double getPerimeter() {
	    return Math.round(2 * radius * Math.PI * 100) / 100d;
	  }

	  @Override /* Prints the circle info */
	  public String getInfo() {
	    return "\nCircle Info: "
	    		+ "\nRadius: " + radius
	    		+ "\nArea: " + getArea()
	    		+ "\nPerimeter: " + getPerimeter()
	    		+ "\nColor: " + getColor()
	    		+ "\nFilled: " + isFilled()
	    		;
	    		
	  }
	  
	}

class Rectangle extends GeometricObject { // source code from text - modified to add getInfo method & color/filled values to main constructor
	  private double width;
	  private double height;

	  public Rectangle() { // default constructor; gives default values if user doesn't provide any 
		  this.height = 0;
		  this.width = 0;
	  }
	  
	  // same as Circle class, modified to add color/filled value setters
	  public Rectangle(double width, double height, String color, boolean filled) {
	    this.width = width;
	    this.height = height;
	    setColor(color);
	    setFilled(filled);
	  }

	  /** Return width */
	  public double getWidth() {
	    return width;
	  }

	  /** Set a new width */
	  public void setWidth(double width) {
	    this.width = width;
	  }

	  /** Return height */
	  public double getHeight() {
	    return height;
	  }

	  /** Set a new height */
	  public void setHeight(double height) {
	    this.height = height;
	  }

	  @Override /** Return area */
	  public double getArea() {
	    return width * height;
	  }

	  @Override /** Return perimeter */
	  public double getPerimeter() {
	    return 2 * (width + height);
	  }
	  
	  public String getInfo() {
		    return "\nRectangle Info: "
		    		+ "\nWidth: " + width
		    		+ "\nHeight: " + height
		    		+ "\nArea: " + getArea()
		    		+ "\nPerimeter: " + getPerimeter()
		    		+ "\nColor: " + getColor()
		    		+ "\nFilled: " + isFilled()
		    		;
		    		
		  }
	}
