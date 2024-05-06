package internship.oasis.task1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CancellationForm extends JFrame {
    private final JTextField pnrField;

    private static final String URL = "jdbc:mysql://localhost:3306/internship";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "abcd1234";

    public CancellationForm() {
        setTitle("Cancellation Form");
        setSize(300, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        JLabel pnrLabel = new JLabel("PNR Number:");
        panel.add(pnrLabel);
        pnrField = new JTextField();
        panel.add(pnrField);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pnr = pnrField.getText();
                try {
                    cancelReservation(pnr);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Failed to cancel reservation. Error: " + ex.getMessage());
                }
            }
        });
        panel.add(cancelButton);

        add(panel);
    }

    private void cancelReservation(String pnr) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "DELETE FROM reservations WHERE pnr = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, pnr);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(null, "Cancellation successful!");
                } else {
                    JOptionPane.showMessageDialog(null, "No reservation found with the provided PNR number.");
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CancellationForm().setVisible(true);
            }
        });
    }
}
