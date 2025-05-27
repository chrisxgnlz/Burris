package codigojules;

import javax.swing.*;
import java.awt.*; // For FlowLayout, though not strictly used yet in this initial step
import java.awt.event.*; // For ActionListener, though not strictly used yet

public class GuiCalculator extends JFrame {

    private JTextField numField1;
    private JTextField numField2;
    private JButton sumButton;
    private JLabel resultLabel;
    private JLabel promptLabel1;
    private JLabel promptLabel2;

    public GuiCalculator() {
        // Set the title of the window
        setTitle("Calculadora Gráfica Jules");
        // Set default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setLayout(new FlowLayout()); // Will be replaced by GridLayout
        setLayout(new GridLayout(3, 2, 10, 10)); // 3 rows, 2 columns, 10px gaps

        // Initialize the components
        promptLabel1 = new JLabel("Primer número:");
        numField1 = new JTextField(10); // 10 is an example column width
        promptLabel2 = new JLabel("Segundo número:");
        numField2 = new JTextField(10);
        sumButton = new JButton("Sumar");
        resultLabel = new JLabel("Resultado: ");

        // Add the components to the JFrame
        add(promptLabel1);
        add(numField1);
        add(promptLabel2);
        add(numField2);
        add(sumButton);
        add(resultLabel);

        // Add ActionListener to the sumButton
        sumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get text from input fields
                    String text1 = numField1.getText();
                    String text2 = numField2.getText();

                    // Parse text to double
                    double num1 = Double.parseDouble(text1);
                    double num2 = Double.parseDouble(text2);

                    // Calculate sum
                    double sum = num1 + num2;

                    // Set result label
                    resultLabel.setText("Resultado: " + sum);
                } catch (NumberFormatException ex) {
                    // Handle invalid input
                    resultLabel.setText("Error: Ingrese números válidos");
                    // Optionally, use JOptionPane for a popup error message:
                    // JOptionPane.showMessageDialog(GuiCalculator.this,
                    // "Error: Ingrese números válidos en ambos campos.",
                    // "Error de Entrada",
                    // JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        // Set the window to be visible
        // setVisible(true); // Visibility will be handled by main method

        pack(); // Pack the window to fit components
        setLocationRelativeTo(null); // Center the window
    }

    public static void main(String[] args) {
        // Ensure GUI updates are handled on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GuiCalculator().setVisible(true);
            }
        });
    }
}
