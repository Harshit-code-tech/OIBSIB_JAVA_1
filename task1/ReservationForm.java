package internship.oasis.task1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ReservationForm extends JFrame {
    private final JTextField nameField;
    private final JTextField trainNumberField;
    private final JComboBox<String> classTypeComboBox;
    private final JTextField journeyDateField;
    private final JTextField sourceField;
    private final JTextField destinationField;

    private int remainingPersons;
    private final String pnr;

    public ReservationForm() {
        setTitle("Reservation Form");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));

        JLabel nameLabel = new JLabel("Name:");
        panel.add(nameLabel);
        nameField = new JTextField();
        panel.add(nameField);

        JLabel trainNumberLabel = new JLabel("Train Number:");
        panel.add(trainNumberLabel);
        trainNumberField = new JTextField();
        panel.add(trainNumberField);

        JLabel classTypeLabel = new JLabel("Class Type:");
        panel.add(classTypeLabel);
        String[] classTypes = {"First Class", "Second Class", "Third Class", "Super Class", "Sleeper Class"};
        classTypeComboBox = new JComboBox<>(classTypes);
        panel.add(classTypeComboBox);

        JLabel journeyDateLabel = new JLabel("Journey Date:");
        panel.add(journeyDateLabel);
        journeyDateField = new JTextField();
        panel.add(journeyDateField);

        JLabel sourceLabel = new JLabel("Source:");
        panel.add(sourceLabel);
        sourceField = new JTextField();
        panel.add(sourceField);

        JLabel destinationLabel = new JLabel("Destination:");
        panel.add(destinationLabel);
        destinationField = new JTextField();
        panel.add(destinationField);

        // Generate PNR at the beginning
        pnr = DatabaseDemo.generatePNR();

        JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (remainingPersons > 0) {
                        String name = nameField.getText();
                        String trainNumber = trainNumberField.getText();
                        String classType = (String) classTypeComboBox.getSelectedItem();
                        String journeyDate = journeyDateField.getText();
                        String source = sourceField.getText();
                        String destination = destinationField.getText();

                        DatabaseDemo.insertReservation(name, trainNumber, classType, journeyDate, source, destination, pnr);

                        remainingPersons--;

                        if (remainingPersons == 0) {
                            clearFields();
                            nameField.setEditable(true);
                            JOptionPane.showMessageDialog(null, "PNR for all persons: " + pnr); // Display PNR
                            dispose();
                        } else {
                            disableFields();

                            nameField.setText("");
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid number of persons. Please enter a valid number.");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Failed to insert reservation. Error: " + ex.getMessage());
                }
            }
        });
        panel.add(insertButton);

        add(panel);

        remainingPersons = getNumberOfPersons();
    }

    private int getNumberOfPersons() {
        String input = JOptionPane.showInputDialog("Enter the number of persons:");
        if (input != null && !input.isEmpty()) {
            return Integer.parseInt(input);
        }
        return 0;
    }

    private void clearFields() {
        nameField.setText("");
        trainNumberField.setText("");
        classTypeComboBox.setSelectedIndex(0);
        journeyDateField.setText("");
        sourceField.setText("");
        destinationField.setText("");
    }

    private void clearFieldsExceptName() {
        trainNumberField.setText("");
        classTypeComboBox.setSelectedIndex(0);
        journeyDateField.setText("");
        sourceField.setText("");
        destinationField.setText("");
    }

    private void disableFields() {
        trainNumberField.setEditable(false);
        classTypeComboBox.setEnabled(false);
        journeyDateField.setEditable(false);
        sourceField.setEditable(false);
        destinationField.setEditable(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ReservationForm().setVisible(true);
            }
        });
    }
}
