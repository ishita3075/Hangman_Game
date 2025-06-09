import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        // Database connection check (optional)
        String url = "jdbc:mysql://localhost:3306/Hangman";
        String username = "root";
        String password = "root@1234";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected to database");
        } catch (SQLException e) {
            System.err.println("Connection failed");
            e.printStackTrace();
        }

        // Run GUI on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            String playerName = JOptionPane.showInputDialog(null, "Enter your name:", "Welcome to Hangman", JOptionPane.PLAIN_MESSAGE);

            if (playerName == null || playerName.trim().isEmpty()) {
                playerName = "Guest";
            }

            Hangman game = new Hangman(playerName.trim());
            game.setVisible(true);
        });
    }
}
