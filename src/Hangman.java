import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Hangman extends JFrame implements ActionListener {
    private int incorrectGuesses;
    private String[] wordChallenge;
    private final WordDB wordDB;
    private final ScoreDB scoreDB;
    private final String playerName;

    private JLabel hangmanImage, categoryLabel, hiddenWordLabel, resultLabel, wordLabel;
    private JButton[] letterButtons;
    private JDialog resultDialog;
    private Font customFont;

    public Hangman(String playerName) {
        super("Hangman Game");

        this.playerName = (playerName == null || playerName.trim().isEmpty()) ? "Guest" : playerName.trim();
        this.scoreDB = new ScoreDB();

        setSize(CommonConstants.FRAME_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(Color.BLACK); // CHANGED

        wordDB = new WordDB();
        letterButtons = new JButton[26];
        wordChallenge = wordDB.loadChallenge();
        customFont = CustomTools.createFont(CommonConstants.FONT_PATH);
        createResultDialog();
        addGuiComponents();

        incorrectGuesses = 0;
    }

    private void addGuiComponents() {
        hangmanImage = CustomTools.loadImage(CommonConstants.IMAGE_PATH);
        hangmanImage.setBounds(0, 0, hangmanImage.getPreferredSize().width, hangmanImage.getPreferredSize().height);

        categoryLabel = new JLabel(wordChallenge[0]);
        categoryLabel.setFont(customFont.deriveFont(30f));
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        categoryLabel.setOpaque(true);
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setBackground(CommonConstants.SECONDARY_COLOR);
        categoryLabel.setBorder(BorderFactory.createLineBorder(CommonConstants.SECONDARY_COLOR));
        categoryLabel.setBounds(
                0,
                hangmanImage.getPreferredSize().height - 28,
                CommonConstants.FRAME_SIZE.width,
                categoryLabel.getPreferredSize().height
        );

        hiddenWordLabel = new JLabel(CustomTools.hideWords(wordChallenge[1]));
        hiddenWordLabel.setFont(customFont.deriveFont(64f));
        hiddenWordLabel.setForeground(Color.WHITE);
        hiddenWordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hiddenWordLabel.setBounds(
                0,
                categoryLabel.getY() + categoryLabel.getPreferredSize().height + 50,
                CommonConstants.FRAME_SIZE.width,
                hiddenWordLabel.getPreferredSize().height
        );

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.BLACK); // CHANGED
        buttonPanel.setBounds(
                -5,
                hiddenWordLabel.getY() + hiddenWordLabel.getPreferredSize().height,
                CommonConstants.BUTTON_PANEL_SIZE.width,
                CommonConstants.BUTTON_PANEL_SIZE.height
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        int row = 0, col = 0;

        Dimension buttonSize = new Dimension(60, 40);

        for (char c = 'A'; c <= 'Z'; c++) {
            JButton button = new JButton(Character.toString(c));
            button.setPreferredSize(buttonSize);
            button.setBackground(CommonConstants.PRIMARY_COLOR);
            button.setFont(customFont.deriveFont(22f));
            button.setForeground(Color.WHITE);
            button.addActionListener(this);
            letterButtons[c - 'A'] = button;

            gbc.gridx = col;
            gbc.gridy = row;
            gbc.gridwidth = 1;
            buttonPanel.add(button, gbc);

            col++;
            if (col == 7) {
                col = 0;
                row++;
            }
        }

        JButton resetButton = new JButton("Reset");
        resetButton.setPreferredSize(buttonSize);
        resetButton.setFont(customFont.deriveFont(22f));
        resetButton.setForeground(Color.WHITE);
        resetButton.setBackground(CommonConstants.SECONDARY_COLOR);
        resetButton.addActionListener(this);
        gbc.gridx = col;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        buttonPanel.add(resetButton, gbc);

        col++;
        if (col == 7) {
            col = 0;
            row++;
        }

        JButton quitButton = new JButton("Quit");
        quitButton.setPreferredSize(buttonSize);
        quitButton.setFont(customFont.deriveFont(22f));
        quitButton.setForeground(Color.WHITE);
        quitButton.setBackground(CommonConstants.SECONDARY_COLOR);
        quitButton.addActionListener(this);
        gbc.gridx = col;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        buttonPanel.add(quitButton, gbc);

        col++;
        if (col >= 6) {
            col = 0;
            row++;
        }

        JButton highScoresButton = new JButton("High Scores");
        highScoresButton.setPreferredSize(new Dimension(buttonSize.width * 2 + 10, buttonSize.height));
        highScoresButton.setFont(customFont.deriveFont(22f));
        highScoresButton.setForeground(Color.WHITE);
        highScoresButton.setBackground(CommonConstants.SECONDARY_COLOR);
        highScoresButton.addActionListener(e -> {
            HighScoresDialog dialog = new HighScoresDialog(this, scoreDB);
            dialog.setVisible(true);
        });
        gbc.gridx = col;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        buttonPanel.add(highScoresButton, gbc);

        getContentPane().add(categoryLabel);
        getContentPane().add(hangmanImage);
        getContentPane().add(hiddenWordLabel);
        getContentPane().add(buttonPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("Reset".equals(command) || "Restart".equals(command)) {
            resetGame();
            if ("Restart".equals(command)) {
                resultDialog.setVisible(false);
            }
            return;
        }

        if ("Quit".equals(command)) {
            dispose();
            return;
        }

        JButton button = (JButton) e.getSource();
        button.setEnabled(false);

        if (wordChallenge[1].contains(command)) {
            button.setBackground(Color.GREEN);

            char[] hiddenWord = hiddenWordLabel.getText().toCharArray();
            for (int i = 0; i < wordChallenge[1].length(); i++) {
                if (wordChallenge[1].charAt(i) == command.charAt(0)) {
                    hiddenWord[i] = command.charAt(0);
                }
            }
            hiddenWordLabel.setText(String.valueOf(hiddenWord));

            if (!hiddenWordLabel.getText().contains("*")) {
                scoreDB.updateScore(playerName, true);
                int currentScore = scoreDB.getScore(playerName);
                resultLabel.setText("You got it right! Score: " + currentScore);
                wordLabel.setText("Word: " + wordChallenge[1]);
                resultDialog.setVisible(true);
            }
        } else {
            button.setBackground(Color.RED);
            incorrectGuesses++;
            CustomTools.updateImage(hangmanImage, "resources/" + (incorrectGuesses + 1) + ".png");

            if (incorrectGuesses >= 6) {
                scoreDB.updateScore(playerName, false);
                int currentScore = scoreDB.getScore(playerName);
                resultLabel.setText("Too Bad, Try Again? Score: " + currentScore);
                wordLabel.setText("Word: " + wordChallenge[1]);
                resultDialog.setVisible(true);
            }
        }
    }

    private void createResultDialog() {
        resultDialog = new JDialog(this, "Result", true);
        resultDialog.setSize(CommonConstants.RESULT_DIALOG_SIZE);
        resultDialog.getContentPane().setBackground(Color.BLACK); // CHANGED
        resultDialog.setResizable(false);
        resultDialog.setLocationRelativeTo(this);
        resultDialog.setLayout(new GridLayout(3, 1));

        resultDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                resetGame();
            }
        });

        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setForeground(Color.WHITE);

        wordLabel = new JLabel("", SwingConstants.CENTER);
        wordLabel.setForeground(Color.WHITE);

        JButton restartButton = new JButton("Restart");
        restartButton.setForeground(Color.WHITE);
        restartButton.setBackground(CommonConstants.SECONDARY_COLOR);
        restartButton.addActionListener(this);

        resultDialog.add(resultLabel);
        resultDialog.add(wordLabel);
        resultDialog.add(restartButton);
    }

    private void resetGame() {
        wordChallenge = wordDB.loadChallenge();
        incorrectGuesses = 0;
        CustomTools.updateImage(hangmanImage, CommonConstants.IMAGE_PATH);
        categoryLabel.setText(wordChallenge[0]);
        hiddenWordLabel.setText(CustomTools.hideWords(wordChallenge[1]));
        for (JButton button : letterButtons) {
            button.setEnabled(true);
            button.setBackground(CommonConstants.PRIMARY_COLOR);
        }
    }
}
