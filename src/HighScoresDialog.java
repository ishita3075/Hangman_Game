import javax.swing.*;
import java.awt.*;
import java.util.Map;
// This class creates a dialog window that displays the top high scores
public class HighScoresDialog extends JDialog {
    // Constructor that takes the parent frame and the score database
    public HighScoresDialog(JFrame parent, ScoreDB scoreDB) {
        // Create a modal dialog with the title "High Scores"
        super(parent, "High Scores", true);
        // Set size and layout of the dialog
        setSize(300, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent); // Center the dialog on the parent window
        // Create a text area to display the scores
        JTextArea scoreArea = new JTextArea();
        scoreArea.setEditable(false); // User cannot edit the text
        scoreArea.setFont(new Font("Monospaced", Font.PLAIN, 16)); // Set font for better alignment
        // Get the top 10 scores from the score database
        Map<String, Integer> topScores = scoreDB.getTopScores(10);
        // Build the text to display in the score area
        StringBuilder sb = new StringBuilder();
        sb.append("Top 10 Players:\n\n");
        int rank = 1;
        for (Map.Entry<String, Integer> entry : topScores.entrySet()) {
            // Format: rank. playerName : score
            sb.append(String.format("%2d. %-10s : %d\n", rank++, entry.getKey(), entry.getValue()));
        }
        // Set the built string as the content of the text area
        scoreArea.setText(sb.toString());
        // Add the text area to the center with a scroll pane in case of overflow
        add(new JScrollPane(scoreArea), BorderLayout.CENTER);
        // Create a close button and add functionality to close the dialog
        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dispose()); // Close dialog on click
        add(closeBtn, BorderLayout.SOUTH); // Place button at the bottom
    }
}
