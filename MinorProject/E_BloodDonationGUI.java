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
    // Action Listener for Register Button
private class RegisterButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        String bloodGroup = bloodGroupField.getText().toUpperCase(); // Convert to uppercase for consistency
        String contactNumber = contactField.getText();

        // Basic input validation
        if (!name.isEmpty() && isValidBloodGroup(bloodGroup) && contactNumber.matches("\\d+")) {
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
        } else {
            resultArea.append("Invalid input. Please check the blood group and contact number.\n");
        }
    }
}

// Method to validate blood group
private boolean isValidBloodGroup(String bloodGroup) {
    String[] validGroups = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
    for (String group : validGroups) {
        if (group.equalsIgnoreCase(bloodGroup)) {
            return true;
        }
    }
    return false;
}


    // Action Listener for Search Button
    // Action Listener for Search Button
private class SearchButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String bloodGroup = searchField.getText().toUpperCase();
        if (!bloodGroup.isEmpty()) {
            resultArea.append("=== Donors with Blood Group: " + bloodGroup + " ===\n");
            boolean found = false;
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                String sql = "SELECT * FROM donors WHERE blood_group = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, bloodGroup);
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {
                        String name = rs.getString("name");
                        String contact = rs.getString("contact");
                        resultArea.append("Name: " + name + ", Contact: " + contact + "\n");
                        found = true;
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            if (!found) {
                resultArea.append("No donors found with blood group " + bloodGroup + ".\n");
            }
        }
    }
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new E_BloodDonationGUI().setVisible(true));
    }
}
