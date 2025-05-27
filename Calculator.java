// File: Calculator.java

// Import the Scanner class for reading user input
import java.util.Scanner;

/**
 * The Calculator class provides a simple command-line utility
 * for adding two numbers.
 */
public class Calculator {

    /**
     * The main method is the entry point of the program.
     * It prompts the user to enter two numbers, calculates their sum,
     * and then prints the result to the console.
     * @param args Command-line arguments (not used in this program).
     */
    public static void main(String[] args) {
        // Use try-with-resources to ensure the Scanner is closed automatically.
        // This is a good practice to prevent resource leaks.
        try (Scanner scanner = new Scanner(System.in)) { // Create a Scanner object to read input from the console
            // Prompt the user to enter the first number
            System.out.print("Ingrese el primer número: ");
            System.out.flush();
            // Read the first number entered by the user
            double num1 = scanner.nextDouble();

            // Prompt the user to enter the second number
            System.out.print("Ingrese el segundo número: ");
            System.out.flush();
            // Read the second number entered by the user
            double num2 = scanner.nextDouble();

            // Calculate the sum of the two numbers
            double sum = num1 + num2;

            // Print the calculated sum to the console
            System.out.println("La suma es: " + sum);
        }
        // The Scanner is automatically closed here due to the try-with-resources statement.
    }
}
