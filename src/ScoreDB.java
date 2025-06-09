import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;
// This class handles all the database operations related to scores
public class ScoreDB {
    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Hangman";
    private static final String USER = "root";
    private static final String PASSWORD = "root@1234";
    // Method to get top scores from the database
    public Map<String, Integer> getTopScores(int limit) {
        // Using LinkedHashMap to maintain insertion order (ranking)
        Map<String, Integer> topScores = new LinkedHashMap<>();
        // SQL query to get top players sorted by score in descending order
        String query = "SELECT player_name, score FROM scores ORDER BY score DESC LIMIT ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            // Prepare and execute the SQL statement
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, limit); // Set the limit for top players
            ResultSet rs = stmt.executeQuery();
            // Process the result set and store player names with their scores
            while (rs.next()) {
                String name = rs.getString("player_name");
                int score = rs.getInt("score");
                topScores.put(name, score);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print error if database connection fails
        }
        return topScores;
    }
    // Method to update a player's score based on win/loss
    public void updateScore(String playerName, boolean won) {
        int delta = won ? 1 : -1; // Increase score if won, decrease if lost
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            // Check if the player already exists in the database
            String checkQuery = "SELECT score FROM scores WHERE player_name = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, playerName);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                // Player exists: update their score
                int currentScore = rs.getInt("score");
                int newScore = Math.max(currentScore + delta, 0); // Ensure score doesn't go below 0
                String updateQuery = "UPDATE scores SET score = ? WHERE player_name = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setInt(1, newScore);
                updateStmt.setString(2, playerName);
                updateStmt.executeUpdate();
            } else {
                // Player doesn't exist: insert new entry
                int startingScore = won ? 1 : 0;
                String insertQuery = "INSERT INTO scores (player_name, score) VALUES (?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setString(1, playerName);
                insertStmt.setInt(2, startingScore);
                insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }
    }
    // Method to get the score of a specific player
    public int getScore(String playerName) {
        int score = 0;
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            // Prepare query to fetch player's score
            String query = "SELECT score FROM scores WHERE player_name = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, playerName);
            ResultSet rs = stmt.executeQuery();

            // If player exists, retrieve the score
            if (rs.next()) {
                score = rs.getInt("score");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print error if something goes wrong
        }
        return score; // Return the player's score (0 if not found)
    }
}
