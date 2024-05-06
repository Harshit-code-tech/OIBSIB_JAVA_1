package internship.oasis.task1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseDemo {
    private static final String URL = "jdbc:mysql://localhost:3306/internship";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "abcd1234";

    public static String generatePNR() {
        return String.valueOf((int) ((Math.random() * (999999 - 100000)) + 100000));
    }

    public static String insertReservation(String name, String trainNumber, String classType, String journeyDate, String source, String destination, String pnr) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "INSERT INTO reservations (name, train_number, class_type, journey_date, source, destination, pnr) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, name);
                statement.setString(2, trainNumber);
                statement.setString(3, classType);
                statement.setString(4, journeyDate);
                statement.setString(5, source);
                statement.setString(6, destination);
                statement.setString(7, pnr);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Reservation inserted successfully!");
                    return pnr;
                }
            }
        }
        return null;
    }
}
