// File: SimpleSum.java

import java.util.Scanner; // Import the Scanner class

public class SimpleSum {
    public static void main(String[] args) {
        // Inside main method:
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Ingrese el primer número: ");
            // System.out.flush(); // Not strictly needed with print followed by nextDouble, but can be kept for safety
            double num1 = scanner.nextDouble();

            System.out.print("Ingrese el segundo número: ");
            // System.out.flush(); // Similarly, optional here
            double num2 = scanner.nextDouble();

            double sum = num1 + num2;

            System.out.println("La suma es: " + sum);
        } catch (java.util.InputMismatchException e) {
            System.out.println("Error: Por favor, ingrese números válidos.");
        }
        // Scanner is closed automatically by try-with-resources
    }
}
