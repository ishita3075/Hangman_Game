import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Entry point of the Hangman Game application.
 */
public class App {

    public static void main(String[] args) {

        // JDBC URL for MySQL database
        String url = "jdbc:mysql://localhost:3306/Hangman";

        // MySQL login credentials
        String username = "root";
        String password = "root@1234";

        // Attempt to connect to the MySQL database
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected to database"); // Print if connection succeed
            System.out.println(connection); // Print connection object for debugging
        } catch (SQLException e) {
            System.err.println("Connection failed"); // Print if connection fails
        }

        // Launch GUI on Event Dispatch Thread

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Launch the Hangman game UI
                new Hangman().setVisible(true);
            }
        });
    }
}
