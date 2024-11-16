import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class E_BloodDonationGUI extends JFrame {
    // Database connection variables
    private static final String DB_URL = "jdbc:sqlite:E_BloodDonationSystem.db";

    // GUI Components
    private JTextField nameField, bloodGroupField, contactField, searchField;
    private JTextArea resultArea;

    public E_BloodDonationGUI() {
        // Setting up the frame
        setTitle("E-Blood Donation System with Database");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Creating the input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2, 10, 10));

        // Input fields
        inputPanel.add(new JLabel("Donor Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Blood Group:"));
        bloodGroupField = new JTextField();
        inputPanel.add(bloodGroupField);

        inputPanel.add(new JLabel("Contact Number:"));
        contactField = new JTextField();
        inputPanel.add(contactField);

        // Register Button
        JButton registerButton = new JButton("Register Donor");
        registerButton.addActionListener(new RegisterButtonListener());
        inputPanel.add(registerButton);

        // Search Field and Button
        inputPanel.add(new JLabel("Search Blood Group:"));
        searchField = new JTextField();
        inputPanel.add(searchField);

        JButton searchButton = new JButton("Search Donor");
        searchButton.addActionListener(new SearchButtonListener());
        inputPanel.add(searchButton);

        // Result Area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Adding components to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Connect to the database
        createDatabase();
    }

    // Method to create database connection
    private void createDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                String sql = "CREATE TABLE IF NOT EXISTS donors (" +
                             "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                             "name TEXT, " +
                             "blood_group TEXT, " +
                             "contact TEXT)";
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(sql);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Action Listener for Register Button
    private class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String bloodGroup = bloodGroupField.getText();
            String contactNumber = contactField.getText();

            if (!name.isEmpty() && !bloodGroup.isEmpty() && !contactNumber.isEmpty()) {
                try (Connection conn = DriverManager.getConnection(DB_URL)) {
                    String sql = "INSERT INTO donors (name, blood_group, contact) VALUES (?, ?, ?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, name);
                        pstmt.setString(2, bloodGroup);
                        pstmt.setString(3, contactNumber);
                        pstmt.executeUpdate();
                        resultArea.append("Donor registered successfully!\n");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    // Action Listener for Search Button
    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String bloodGroup = searchField.getText();
            if (!bloodGroup.isEmpty()) {
                resultArea.append("=== Donors with Blood Group: " + bloodGroup + " ===\n");
                try (Connection conn = DriverManager.getConnection(DB_URL)) {
                    String sql = "SELECT * FROM donors WHERE blood_group = ?";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, bloodGroup);
                        ResultSet rs = pstmt.executeQuery();
                        while (rs.next()) {
                            String name = rs.getString("name");
                            String contact = rs.getString("contact");
                            resultArea.append("Name: " + name + ", Contact: " + contact + "\n");
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new E_BloodDonationGUI().setVisible(true));
    }
}
