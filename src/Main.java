import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Main extends JFrame {
    private final JLabel idField, lastnameField, firstnameField, phoneField;
    private final JButton previousButton, nextButton;
    private Connection con;
    private PreparedStatement stmt;
    private ResultSet rs;
    private static final String URL = "jdbc:mysql://localhost:3307/Customer";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private int currentRecord = 0;

    private void databaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = con.prepareStatement("SELECT * FROM users");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection error: " + e.getMessage());
            System.exit(1);
        }
    }

    public Main() {
        setTitle("Customer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLayout(new BorderLayout(10, 10));

        JPanel showPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        idField = new JLabel();
        lastnameField = new JLabel();
        firstnameField = new JLabel();
        phoneField = new JLabel();
        showPanel.add(new JLabel("ID: "));
        showPanel.add(idField);
        showPanel.add(new JLabel("Last Name: "));
        showPanel.add(lastnameField);
        showPanel.add(new JLabel("First Name: "));
        showPanel.add(firstnameField);
        showPanel.add(new JLabel("Phone: "));
        showPanel.add(phoneField);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        previousButton = new JButton("Previous");
        nextButton = new JButton("Next");
        previousButton.addActionListener(e -> previousCustomer());
        nextButton.addActionListener(e -> nextCustomer());
        buttonsPanel.add(previousButton);
        buttonsPanel.add(nextButton);

        databaseConnection();
        try {
            rs = stmt.executeQuery();
            if (rs.next()) {
                populateForm(rs);
                previousButton.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, "No Record");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error executing query: " + e.getMessage());
        }

        add(showPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void populateForm(ResultSet rs) throws SQLException {
        idField.setText(rs.getString("cus_id"));
        lastnameField.setText(rs.getString("cus_last_name"));
        firstnameField.setText(rs.getString("cus_first_name"));
        phoneField.setText(rs.getString("cus_phoneNumber"));
    }

    private void nextCustomer() {
        try {
            if (rs.next()) {
                currentRecord++;
                populateForm(rs);
                previousButton.setEnabled(true);
            } else {
                nextButton.setEnabled(false);
                JOptionPane.showMessageDialog(this, "End of records");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching next record: " + e.getMessage());
        }
    }

    private void previousCustomer() {
        try {
            if (rs.previous()) {
                currentRecord--;
                populateForm(rs);
                nextButton.setEnabled(true);
            } else {
                previousButton.setEnabled(false);
                JOptionPane.showMessageDialog(this, "Beginning of records");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching previous record: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}
