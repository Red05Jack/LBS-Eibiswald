import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FractionCalculatorGlassDesign extends JFrame {

    // Fields for the fractions
    private JTextField numerator1, denominator1, numerator2, denominator2;
    private JLabel resultLabel;

    public FractionCalculatorGlassDesign() {
        // Set the title of the window
        setTitle("Glass Design Fraction Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);
        setUndecorated(true); // Remove the title bar
        setLocationRelativeTo(null); // Center the window

        // Set background color to #e09c87
        getContentPane().setBackground(new Color(0xE09C87));

        // Create a custom panel for the close and minimize buttons
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        controlPanel.setOpaque(false);

        // Create the close button
        JButton closeButton = new JButton("X");
        styleControlButton(closeButton);
        closeButton.addActionListener(e -> System.exit(0)); // Close the application

        // Create the minimize button
        JButton minimizeButton = new JButton("_");
        styleControlButton(minimizeButton);
        minimizeButton.addActionListener(e -> setState(JFrame.ICONIFIED)); // Minimize the window

        // Add buttons to the control panel
        controlPanel.add(minimizeButton);
        controlPanel.add(closeButton);

        // Main panel with rounded edges and transparency (glass-like)
        JPanel mainPanel = new RoundedPanel(30, new Color(255, 255, 255, 200));
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setOpaque(false); // Transparency for the glass effect

        // GridBagLayout for structured layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Initialize input fields with custom UI
        numerator1 = createCustomTextField();
        denominator1 = createCustomTextField();
        numerator2 = createCustomTextField();
        denominator2 = createCustomTextField();
        resultLabel = new JLabel("Result: ");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 20));
        resultLabel.setForeground(Color.BLACK);

        // Create panel for fraction 1
        JPanel fraction1Panel = createFractionPanel(numerator1, denominator1);

        // Create panel for fraction 2
        JPanel fraction2Panel = createFractionPanel(numerator2, denominator2);

        // Create calculate button
        JButton calculateButton = new JButton("Berechnen");
        styleButton(calculateButton);
        calculateButton.addActionListener(new CalculateActionListener());

        // Layout the components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        mainPanel.add(fraction1Panel, gbc);

        gbc.gridy = 1;
        mainPanel.add(new JLabel("+"), gbc);

        gbc.gridy = 2;
        mainPanel.add(fraction2Panel, gbc);

        gbc.gridy = 3;
        mainPanel.add(new JLabel("="), gbc);

        gbc.gridy = 4;
        mainPanel.add(resultLabel, gbc);

        gbc.gridy = 5;
        mainPanel.add(calculateButton, gbc);

        // Create a container panel to hold the control panel and main panel
        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.add(controlPanel, BorderLayout.NORTH);
        container.add(mainPanel, BorderLayout.CENTER);

        // Add the container panel to the frame
        add(container);

        // Make the window visible
        setVisible(true);
    }

    // Method to create custom text fields
    private JTextField createCustomTextField() {
        JTextField textField = new JTextField(3);
        textField.setFont(new Font("Arial", Font.BOLD, 24));
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        textField.setBackground(new Color(255, 255, 255, 200)); // Slight transparency for glass effect
        return textField;
    }

    // Method to create a panel for a fraction (numerator over denominator)
    private JPanel createFractionPanel(JTextField numerator, JTextField denominator) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(0, 0, 0, 0)); // Transparent background
        numerator.setMaximumSize(new Dimension(100, 50));
        denominator.setMaximumSize(new Dimension(100, 50));
        panel.add(numerator);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(new JSeparator(SwingConstants.HORIZONTAL)); // Line between numerator and denominator
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(denominator);
        return panel;
    }

    // Method to style the calculate button
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE); // Set button color to white
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setOpaque(true); // Ensure the button is not transparent
    }

    // Method to style control buttons (close and minimize)
    private void styleControlButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(40, 30));
        button.setOpaque(true);
    }

    // Action listener for the calculation button
    private class CalculateActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Read the numerators and denominators
                int num1 = Integer.parseInt(numerator1.getText());
                int den1 = Integer.parseInt(denominator1.getText());
                int num2 = Integer.parseInt(numerator2.getText());
                int den2 = Integer.parseInt(denominator2.getText());

                // Perform the fraction addition
                int resultNum = (num1 * den2) + (num2 * den1);
                int resultDen = den1 * den2;

                // Simplify the result
                int gcd = gcd(resultNum, resultDen);
                resultNum /= gcd;
                resultDen /= gcd;

                // Display the result
                resultLabel.setText(resultNum + "/" + resultDen);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Bitte gültige Zahlen eingeben.");
            }
        }
    }

    // Helper function to find the greatest common divisor (GCD)
    private int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    // Main method to run the application
    public static void main(String[] args) {
        // Run the calculator on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new FractionCalculatorGlassDesign();  // Create and show the calculator
        });
    }

    // Custom JPanel class to create rounded corners
    class RoundedPanel extends JPanel {
        private int cornerRadius;
        private Color backgroundColor;

        public RoundedPanel(int radius, Color bgColor) {
            this.cornerRadius = radius;
            this.backgroundColor = bgColor;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(backgroundColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        }
    }
}
