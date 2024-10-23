import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class HangmanGUI extends JFrame {
    private Hangman game;
    private JLabel wordLabel;           // Zeigt den aktuellen Stand des Wortes
    private JLabel remainingAttemptsLabel; // Zeigt die verbleibenden Versuche
    private JTextField guessField;      // Eingabefeld für den geratenen Buchstaben oder das Wort
    private JButton guessButton;        // Button zum Abgeben des Rates
    private JTextArea guessedLettersArea; // Zeigt die bereits geratenen Buchstaben und Wörter
    private JLabel imageLabel;          // Label für das Bild (links)

    public HangmanGUI(Hangman game) {
        this.game = game;
        initComponents();
    }

    private void initComponents() {
        // Setze den Titel des Fensters
        setTitle("Hangman Game");

        // Grundlegende GUI-Einstellungen
        setSize(800, 500);  // Breitere GUI für besseres Verhältnis
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Hauptpanel mit JSplitPane für das Bild (links) und den Eingabebereich (rechts)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.67);  // Setze das Verhältnis auf 2:1 (links zu rechts)

        // Panel für das Bild (links) - Bildseitenverhältnis 1:1 sicherstellen
        JPanel imagePanel = new JPanel();
        imageLabel = new JLabel();
        updateHangmanImage(); // Initialisiere das Bild entsprechend den verbleibenden Versuchen
        imagePanel.add(imageLabel);
        imagePanel.setPreferredSize(new Dimension(400, 400));
        imagePanel.setBackground(Color.LIGHT_GRAY);

        // Panel für Eingabebereich (rechts)
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

        // Eingabefeld und Rate-Button (einzeilig)
        JPanel inputFieldPanel = new JPanel();
        guessField = new JTextField(10);
        guessButton = new JButton("Raten");
        guessButton.addActionListener(new GuessButtonListener());
        inputFieldPanel.add(new JLabel("Buchstaben oder Wort:"));
        inputFieldPanel.add(guessField);
        inputFieldPanel.add(guessButton);
        inputPanel.add(inputFieldPanel, BorderLayout.NORTH);

        // Panel für bereits geratene Buchstaben und Wörter (größerer Bereich)
        guessedLettersArea = new JTextArea(10, 20);  // Vergrößere die Textfläche
        guessedLettersArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(guessedLettersArea);
        inputPanel.add(scrollPane, BorderLayout.CENTER);

        // Label für verbleibende Versuche
        remainingAttemptsLabel = new JLabel("Verbleibende Versuche: " + game.getRemainingAttempts());
        inputPanel.add(remainingAttemptsLabel, BorderLayout.SOUTH);

        // Füge das Bild-Panel und das Eingabepanel dem SplitPane hinzu
        splitPane.setLeftComponent(imagePanel);  // Linker Teil: Bild
        splitPane.setRightComponent(inputPanel); // Rechter Teil: Eingabe und Infos

        // Panel für das zu erratende Wort (über die gesamte Breite)
        wordLabel = new JLabel("Wort: " + game.getCurrentWordState());
        wordLabel.setFont(new Font("Arial", Font.BOLD, 24));
        wordLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Füge SplitPane und das Wortpanel zur Hauptansicht hinzu
        add(splitPane, BorderLayout.CENTER);
        add(wordLabel, BorderLayout.SOUTH);

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
                JOptionPane.showMessageDialog(null, "Bitte einen Buchstaben oder ein Wort eingeben.");
                return;
            }

            // Überprüfung der Eingabe (einzelner Buchstabe oder Wort)
            boolean result = game.guessLetterOrWord(guess);

            // Aktualisierung des GUI-Status
            wordLabel.setText("Wort: " + game.getCurrentWordState());
            remainingAttemptsLabel.setText("Verbleibende Versuche: " + game.getRemainingAttempts());

            // Zeige die geratenen Buchstaben und Wörter an (Verwendung der Methode aus der Hangman-Klasse)
            guessedLettersArea.setText(game.getAllGuessedStrings());

            // Prüft, ob das Spiel gewonnen oder verloren wurde
            if (game.isGameWon()) {
                JOptionPane.showMessageDialog(null, "Glückwunsch, du hast das Wort erraten: " + game.getWordToGuess());
                resetGame();
            } else if (game.isGameOver()) {
                JOptionPane.showMessageDialog(null, "Game Over! Das gesuchte Wort war: " + game.getWordToGuess());
                resetGame();
            }

            // Lösche das Eingabefeld nach der Eingabe
            guessField.setText("");
            updateHangmanImage();  // Aktualisiere das Bild bei jedem Versuch
        }
    }

    // Methode zum Zurücksetzen des Spiels
    private void resetGame() {
        String[] wordList = {"hangman", "java", "computer", "spiel", "entwicklung"};
        game = new Hangman(wordList[(int)(Math.random() * wordList.length)], 6);
        wordLabel.setText("Wort: " + game.getCurrentWordState());
        remainingAttemptsLabel.setText("Verbleibende Versuche: " + game.getRemainingAttempts());
        guessedLettersArea.setText("");
        updateHangmanImage(); // Bild zurücksetzen
    }

    // Methode zum Aktualisieren des Bildes basierend auf den verbleibenden Versuchen
    private void updateHangmanImage() {
        int remainingAttempts = game.getRemainingAttempts();
        int imageIndex = 11 - remainingAttempts; // Bildpfad (0.png bis 11.png)

        String imagePath = "./images/" + imageIndex + ".png"; // Pfad zum Bild

        // Überprüfen, ob die Bilddatei existiert
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            ImageIcon imageIcon = new ImageIcon(imagePath);
            imageLabel.setIcon(imageIcon);
        } else {
            imageLabel.setText("Bild nicht gefunden");
        }
    }
}
