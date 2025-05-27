package codigojules;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*; // For ActionListener and ActionEvent

public class Calculator extends JFrame {

    private JTextField displayField; // To show numbers and results

    // Declare button instance variables
    private JButton button1, button2, button3, button4, button5, button6, button7, button8, button9, button0, buttonDot;
    private JButton buttonPlus, buttonMinus, buttonMultiply, buttonDivide; // Operation buttons
    private JButton buttonEquals, buttonClear; // Equals and Clear buttons

    // Instance variables for calculator logic
    private double currentOperand = 0;
    private String currentOperation = ""; // Stores "+", "-", "*", "/"
    private boolean startNewNumber = true; // True if the next digit should start a new number

    public Calculator() {
        setTitle("Calculadora Gráfica Burris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize the display field
        displayField = new JTextField();
        displayField.setEditable(false); // User shouldn't type directly into it
        displayField.setHorizontalAlignment(JTextField.RIGHT); // Numbers usually align to the right
        displayField.setFont(new Font("Arial", Font.BOLD, 24)); // Make text bigger and bold

        // Add the display field to the top of the frame
        add(displayField, BorderLayout.NORTH);

        // Create digitPanel
        JPanel digitPanel = new JPanel(new GridLayout(4, 3, 5, 5)); // 4 rows, 3 columns, 5px gaps
        Font buttonFont = new Font("Arial", Font.PLAIN, 18);

        // Initialize digit buttons and add action listeners
        ActionListener digitListener = e -> {
            String buttonText = ((JButton)e.getSource()).getText();
            if (startNewNumber) {
                displayField.setText(buttonText);
                startNewNumber = false;
            } else {
                displayField.setText(displayField.getText() + buttonText);
            }
        };

        button7 = new JButton("7"); button7.setFont(buttonFont); button7.addActionListener(digitListener); digitPanel.add(button7);
        button8 = new JButton("8"); button8.setFont(buttonFont); button8.addActionListener(digitListener); digitPanel.add(button8);
        button9 = new JButton("9"); button9.setFont(buttonFont); button9.addActionListener(digitListener); digitPanel.add(button9);
        button4 = new JButton("4"); button4.setFont(buttonFont); button4.addActionListener(digitListener); digitPanel.add(button4);
        button5 = new JButton("5"); button5.setFont(buttonFont); button5.addActionListener(digitListener); digitPanel.add(button5);
        button6 = new JButton("6"); button6.setFont(buttonFont); button6.addActionListener(digitListener); digitPanel.add(button6);
        button1 = new JButton("1"); button1.setFont(buttonFont); button1.addActionListener(digitListener); digitPanel.add(button1);
        button2 = new JButton("2"); button2.setFont(buttonFont); button2.addActionListener(digitListener); digitPanel.add(button2);
        button3 = new JButton("3"); button3.setFont(buttonFont); button3.addActionListener(digitListener); digitPanel.add(button3);
        button0 = new JButton("0"); button0.setFont(buttonFont); button0.addActionListener(digitListener); digitPanel.add(button0);

        buttonDot = new JButton(".");
        buttonDot.setFont(buttonFont);
        buttonDot.addActionListener(e -> {
            if (startNewNumber) {
                displayField.setText("0."); // Start with "0." if dot is first
                startNewNumber = false;
            } else if (!displayField.getText().contains(".")) {
                displayField.setText(displayField.getText() + ".");
            }
        });
        digitPanel.add(buttonDot);

        buttonClear = new JButton("C");
        buttonClear.setFont(buttonFont);
        buttonClear.addActionListener(e -> {
            displayField.setText("");
            currentOperand = 0;
            currentOperation = "";
            startNewNumber = true;
        });
        digitPanel.add(buttonClear);

        // Add digitPanel to the frame
        add(digitPanel, BorderLayout.CENTER);

        // Create operationPanel
        JPanel operationPanel = new JPanel(new GridLayout(5, 1, 5, 5)); // 5 rows, 1 column, 5px gaps
        // Font buttonFont = new Font("Arial", Font.PLAIN, 18); // Already declared above, can reuse

        ActionListener operationListener = e -> {
            String operation = ((JButton)e.getSource()).getText();
            String currentText = displayField.getText();

            if (currentText.isEmpty() || currentText.equals("Error: Div by 0")) { // Or other errors
                // Optionally handle setting display to "0" or an error state
                return;
            }

            try {
                double displayedValue = Double.parseDouble(currentText);
                if (!startNewNumber && !currentOperation.isEmpty()) {
                    performCalculation(displayedValue);
                     // If performCalculation resulted in an error, displayField is updated
                    if (displayField.getText().startsWith("Error")) {
                        return; // Stop further processing on error
                    }
                }
                // Update currentOperand with the value that was on display *before* this operation
                // or the result of a previous calculation if one just happened.
                currentOperand = Double.parseDouble(displayField.getText());
                currentOperation = operation;
                startNewNumber = true;

            } catch (NumberFormatException ex) {
                displayField.setText("Error: Invalid number");
                currentOperation = "";
                startNewNumber = true;
            }
        };

        buttonPlus = new JButton("+"); buttonPlus.setFont(buttonFont); buttonPlus.addActionListener(operationListener); operationPanel.add(buttonPlus);
        buttonMinus = new JButton("-"); buttonMinus.setFont(buttonFont); buttonMinus.addActionListener(operationListener); operationPanel.add(buttonMinus);
        buttonMultiply = new JButton("*"); buttonMultiply.setFont(buttonFont); buttonMultiply.addActionListener(operationListener); operationPanel.add(buttonMultiply);
        buttonDivide = new JButton("/"); buttonDivide.setFont(buttonFont); buttonDivide.addActionListener(operationListener); operationPanel.add(buttonDivide);

        buttonEquals = new JButton("=");
        buttonEquals.setFont(buttonFont);
        buttonEquals.addActionListener(e -> {
            String currentText = displayField.getText();
            if (currentText.isEmpty() || currentOperation.isEmpty() || startNewNumber) {
                // Do nothing if no number, no operation, or if we just set an operation and expect a new number
                return;
            }
            try {
                performCalculation(Double.parseDouble(currentText));
                currentOperation = ""; // Reset operation after equals
                // startNewNumber = true; // performCalculation already sets this
            } catch (NumberFormatException ex) {
                displayField.setText("Error: Invalid number");
                currentOperation = "";
                startNewNumber = true;
            }
        });
        operationPanel.add(buttonEquals);

        // Add operationPanel to the frame
        add(operationPanel, BorderLayout.EAST);

        pack(); // Adjust window size to components
        setLocationRelativeTo(null); // Center on screen
    }

    private void performCalculation(double secondNumber) {
        if (currentOperation.isEmpty()) {
            // This case should ideally not be reached if UI logic is correct,
            // but as a safeguard, we can set the display to the second number.
            displayField.setText(String.valueOf(secondNumber));
            currentOperand = secondNumber; // Update currentOperand
            startNewNumber = true;
            return;
        }
        double result = 0;
        switch (currentOperation) {
            case "+":
                result = currentOperand + secondNumber;
                break;
            case "-":
                result = currentOperand - secondNumber;
                break;
            case "*":
                result = currentOperand * secondNumber;
                break;
            case "/":
                if (secondNumber == 0) {
                    displayField.setText("Error: Div by 0");
                    currentOperand = 0; // Reset operand
                    currentOperation = ""; // Reset operation
                    startNewNumber = true;
                    return;
                }
                result = currentOperand / secondNumber;
                break;
            default: // Should not happen
                startNewNumber = true;
                return;
        }
        // Check for integer result to display cleanly
        if (result == (long) result) {
            displayField.setText(String.format("%d", (long) result));
        } else {
            displayField.setText(String.format("%s", result));
        }
        currentOperand = result; // Store result for chained operations
        // currentOperation = ""; // Resetting operation is handled by "=" or next op choice
        startNewNumber = true; // Ready for a new number input
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Calculator().setVisible(true);
            }
        });
    }
}
