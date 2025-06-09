import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class HighScoresDialog extends JDialog {

    public HighScoresDialog(JFrame parent, ScoreDB scoreDB) {
        super(parent, "High Scores", true);
        setSize(300, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);

        JTextArea scoreArea = new JTextArea();
        scoreArea.setEditable(false);
        scoreArea.setFont(new Font("Monospaced", Font.PLAIN, 16));

        Map<String, Integer> topScores = scoreDB.getTopScores(10); // top 10
        StringBuilder sb = new StringBuilder();
        sb.append("Top 10 Players:\n\n");
        int rank = 1;
        for (Map.Entry<String, Integer> entry : topScores.entrySet()) {
            sb.append(String.format("%2d. %-10s : %d\n", rank++, entry.getKey(), entry.getValue()));
        }

        scoreArea.setText(sb.toString());
        add(new JScrollPane(scoreArea), BorderLayout.CENTER);

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dispose());
        add(closeBtn, BorderLayout.SOUTH);
    }
}
