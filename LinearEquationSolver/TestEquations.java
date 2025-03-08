package test;

abstract class Equation {
    double[] coefficients;

    public Equation() {
        this.coefficients = null;
    }

    public Equation(double[] coefficients) {
        this.coefficients = coefficients;
    }

    public abstract double[] findSolution();
}

class LinearEquation extends Equation {
    public LinearEquation(double a, double b) { // In case someone specifies each individual value
        super(new double[]{a, b}); // We implement the coefficients property here and use the constructor in the Equation class
    }

    public LinearEquation(double[] coefficients) { // In case someone gives an array of values instead
        super(coefficients); // We can just use the constructor in the Equation class
    }

    public double[] findSolution() {
        double a = coefficients[0];
        double b = coefficients[1];
        
        if (a == 0) { // No solution for a linear equation when a = 0
            return null; 
        }
        return new double[]{-b / a}; // Else, use the given formula and return it in an array of length 1.
    }
    
    public String toString() { // Format into the form ax + b = 0 with two decimal points.
        return String.format("%.2fx + %.2f = 0", coefficients[0], coefficients[1]);
    }
}

class QuadraticEquation extends Equation {
    public QuadraticEquation(double a, double b, double c) { // We have two constructors here for the same reasons as LinearEquation.
        super(new double[]{a, b, c});
    }

    public QuadraticEquation(double[] coefficients) { 
        super(coefficients);
    }

    public double[] findSolution() {
        double a = coefficients[0];
        double b = coefficients[1];
        double c = coefficients[2];
        
        // Given a quadratic equation of form ax^2 + bx + c = 0, the discriminant d = b^2 - 4ac
        double d = (b * b) - (4 * a * c);
        
        if (d < 0) { // If D < 0, then there are no real solutions.
            return null;
            
        } else if (d == 0) { // If D = 0, there is only 1 real solution.
            return new double[]{-b / (2 * a)}; 
            
        } else { // If D > 0, there are 2 real solutions.
            double root1 = (-b + Math.sqrt(d)) / (2 * a);
            double root2 = (-b - Math.sqrt(d)) / (2 * a);
            return new double[]{root1, root2}; 
        }
    }

    public String toString() { // Format into the form ax^2 + bx + c = 0 with 2 decimal points and a subscript for the exponent
        return String.format("%.2fx\u00b2 + %.2fx + %.2f = 0", coefficients[0], coefficients[1], coefficients[2]);
    }
}

public class TestEquations {

    public static void displayResult(QuadraticEquation qEqn, double[] result) {
        if (result == null) {
            System.out.println(qEqn + " has no solution.");
        } else if (result.length == 1) {
            System.out.printf("%s has one solution: %.3f%n", qEqn, result[0]);
        } else {
            System.out.printf("%s has two solutions: %.3f and %.3f%n", qEqn, result[0], result[1]);
        }
    }

    public static void main(String[] args) {
        // Solve 3x + 9 = 0
        LinearEquation l1 = new LinearEquation(3, 9);
        System.out.println("Solution of " + l1 + " is " + l1.findSolution()[0]);

        // Solve 3x  + 2 + 7 = 0
        QuadraticEquation q1 = new QuadraticEquation(3.0, 2, 7);
        displayResult(q1, q1.findSolution());

        // Solve x  - 5x + 6.25 = 0
        QuadraticEquation q2 = new QuadraticEquation(1, -5, 6.25);
        displayResult(q2, q2.findSolution());

        // Solve 10x  + 10x - 11.90 = 0
        QuadraticEquation q3 = new QuadraticEquation(10, 10, -11.90);
        displayResult(q3, q3.findSolution());
    }
}

