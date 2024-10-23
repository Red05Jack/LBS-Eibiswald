import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HangmanGUI extends JFrame {
    private Hangman game;
    private JLabel wordLabel;           // Zeigt den aktuellen Stand des Wortes
    private JLabel remainingAttemptsLabel; // Zeigt die verbleibenden Versuche
    private JTextField guessField;      // Eingabefeld für den geratenen Buchstaben oder das Wort
    private JButton guessButton;        // Button zum Abgeben des Rates
    private JTextArea guessedLettersArea; // Zeigt die bereits geratenen Buchstaben und Wörter
    private JLabel messageLabel;        // Zeigt Nachrichten wie "Korrekt" oder "Falsch" an

    public HangmanGUI(Hangman game) {
        this.game = game;
        initComponents();
    }

    private void initComponents() {
        // Setze den Titel des Fensters
        setTitle("Hangman Game");

        // Grundlegende GUI-Einstellungen
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel für das Wort und die verbleibenden Versuche
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 1));

        wordLabel = new JLabel("Wort: " + game.getCurrentWordState());
        wordLabel.setFont(new Font("Arial", Font.BOLD, 24));
        wordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(wordLabel);

        remainingAttemptsLabel = new JLabel("Verbleibende Versuche: " + game.getRemainingAttempts());
        remainingAttemptsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(remainingAttemptsLabel);

        add(topPanel, BorderLayout.NORTH);

        // Panel für Eingabe und Rate-Button
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        guessField = new JTextField(10);
        inputPanel.add(guessField);

        guessField.addActionListener(new GuessButtonListener());

        guessButton = new JButton("Raten");
        guessButton.addActionListener(new GuessButtonListener());
        inputPanel.add(guessButton);

        add(inputPanel, BorderLayout.CENTER);

        // Panel für bereits geratene Buchstaben und Wörter
        guessedLettersArea = new JTextArea(5, 30);
        guessedLettersArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(guessedLettersArea);
        add(scrollPane, BorderLayout.SOUTH);

        // Nachrichten-Label
        messageLabel = new JLabel("");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(messageLabel, BorderLayout.SOUTH);

        // GUI sichtbar machen
        setVisible(true);
    }

    // Listener für den Raten-Button
    private class GuessButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String guess = guessField.getText();

            // Wenn keine Eingabe vorhanden ist
            if (guess.isEmpty()) {
                messageLabel.setText("Bitte einen Buchstaben oder ein Wort eingeben.");
                return;
            }

            // Überprüfung der Eingabe (einzelner Buchstabe oder Wort)
            boolean result = game.guessLetterOrWord(guess);

            // Aktualisierung des GUI-Status
            wordLabel.setText("Wort: " + game.getCurrentWordState());
            remainingAttemptsLabel.setText("Verbleibende Versuche: " + game.getRemainingAttempts());

            // Falls die Eingabe korrekt war
            if (result) {
                messageLabel.setText("Korrekt!");
            } else {
                messageLabel.setText("Falsch!");
            }

            // Fügt das geratenen Wort oder den Buchstaben in das Textfeld hinzu
            guessedLettersArea.append("Geraten: " + guess + "\n");

            // Prüft, ob das Spiel gewonnen wurde
            if (game.isGameWon()) {
                JOptionPane.showMessageDialog(null, "Glückwunsch, du hast das Wort erraten: " + game.getWordToGuess());
                resetGame();
            }

            // Prüft, ob das Spiel verloren wurde
            if (game.isGameOver()) {
                JOptionPane.showMessageDialog(null, "Game Over! Das gesuchte Wort war: " + game.getWordToGuess());
                resetGame();
            }

            // Lösche das Eingabefeld nach der Eingabe
            guessField.setText("");
        }
    }

    // Methode zum Zurücksetzen des Spiels
    private void resetGame() {
        String[] wordList = {"hangman", "java", "computer", "spiel", "entwicklung"};
        game = new Hangman(wordList[(int)(Math.random() * wordList.length)], 6);
        wordLabel.setText("Wort: " + game.getCurrentWordState());
        remainingAttemptsLabel.setText("Verbleibende Versuche: " + game.getRemainingAttempts());
        guessedLettersArea.setText("");
        messageLabel.setText("");
    }
}
