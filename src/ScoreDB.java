import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;
public class ScoreDB {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Hangman";
    private static final String USER = "root";
    private static final String PASSWORD = "root@1234";
    public Map<String, Integer> getTopScores(int limit) {
        Map<String, Integer> topScores = new LinkedHashMap<>();
        String query = "SELECT player_name, score FROM scores ORDER BY score DESC LIMIT ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("player_name");
                int score = rs.getInt("score");
                topScores.put(name, score);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topScores;
    }


    public void updateScore(String playerName, boolean won) {
        int delta = won ? 1 : -1;
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            // Check if player exists
            String checkQuery = "SELECT score FROM scores WHERE player_name = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, playerName);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int currentScore = rs.getInt("score");
                int newScore = Math.max(currentScore + delta, 0);
                String updateQuery = "UPDATE scores SET score = ? WHERE player_name = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setInt(1, newScore);
                updateStmt.setString(2, playerName);
                updateStmt.executeUpdate();
            } else {
                int startingScore = won ? 1 : 0;
                String insertQuery = "INSERT INTO scores (player_name, score) VALUES (?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setString(1, playerName);
                insertStmt.setInt(2, startingScore);
                insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getScore(String playerName) {
        int score = 0;
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String query = "SELECT score FROM scores WHERE player_name = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, playerName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                score = rs.getInt("score");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return score;
    }
}
