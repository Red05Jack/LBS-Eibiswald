import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class HangmanGUI extends JFrame {
    private Hangman game;
    private JLabel wordLabel;           // Zeigt den aktuellen Stand des Wortes
    private JLabel remainingAttemptsLabel; // Zeigt die verbleibenden Versuche
    private JTextField guessField;      // Eingabefeld für den geratenen Buchstaben oder das Wort
    private JButton guessButton;        // Button zum Abgeben des Rates
    private JTextArea guessedLettersArea; // Zeigt die bereits geratenen Buchstaben und Wörter
    private JPanel imagePanel;          // Panel für das Bild
    private BufferedImage hangmanImage; // Hält das aktuelle Hangman-Bild
    private JLabel statusLabel;         // Zeigt den Status des Spiels (Gewonnen/Verloren)
    private JButton restartButton;      // Button zum Neustarten des Spiels

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
        imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (hangmanImage != null) {
                    // Skalierung des Bildes auf die Größe des Panels
                    g.drawImage(hangmanImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        imagePanel.setPreferredSize(new Dimension(400, 400));
        imagePanel.setBackground(Color.LIGHT_GRAY);

        // Panel für Eingabebereich (rechts)
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

        // Eingabefeld und Rate-Button (einzeilig)
        JPanel inputFieldPanel = new JPanel();
        guessField = new JTextField(10);
        guessField.addActionListener(new GuessButtonListener());
        guessButton = new JButton("Raten");
        guessButton.addActionListener(new GuessButtonListener());
        inputFieldPanel.add(new JLabel("Buchstaben oder Wort:"));
        inputFieldPanel.add(guessField);
        inputFieldPanel.add(guessButton);
        inputPanel.add(inputFieldPanel, BorderLayout.NORTH);

        // Panel für bereits geratene Buchstaben und Wörter (größerer Bereich)
        guessedLettersArea = new JTextArea(10, 20);  // Vergrößere die Textfläche
        guessedLettersArea.setEditable(false);
        guessedLettersArea.setLineWrap(true);  // Aktiviert Zeilenumbruch
        guessedLettersArea.setWrapStyleWord(true);  // Bricht bei Wortgrenzen um
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

        // Statuspanel für das Spielende (enthält Statuslabel und Neustartbutton)
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BorderLayout());

        // Statuslabel (Anzeige des Status)
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setForeground(Color.RED);
        statusPanel.add(statusLabel, BorderLayout.CENTER);

        // Neustart-Button hinzufügen, standardmäßig deaktiviert
        restartButton = new JButton("Neustarten");
        restartButton.setEnabled(false);  // Standardmäßig deaktiviert
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        statusPanel.add(restartButton, BorderLayout.EAST);

        // Füge SplitPane, Statuspanel und das Wortpanel zur Hauptansicht hinzu
        add(splitPane, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.NORTH);
        add(wordLabel, BorderLayout.SOUTH);

        // GUI sichtbar machen
        setVisible(true);

        // Lade das initiale Bild
        updateHangmanImage();
    }

    // Listener für den Raten-Button
    private class GuessButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String guess = guessField.getText();

            // Wenn keine Eingabe vorhanden ist
            if (guess.isEmpty()) {
                statusLabel.setText("Bitte einen Buchstaben oder ein Wort eingeben.");
                return;
            }

            // Überprüfung der Eingabe (einzelner Buchstabe oder Wort)
            boolean result = game.guessLetterOrWord(guess);

            // Aktualisierung des GUI-Status
            wordLabel.setText("Wort: " + game.getCurrentWordState());
            remainingAttemptsLabel.setText("Verbleibende Versuche: " + game.getRemainingAttempts());

            // Zeige die geratenen Buchstaben und Wörter an (Verwendung der Methode aus der Hangman-Klasse)
            guessedLettersArea.setText(game.getAllGuessedStrings());

            // Prüft, ob das Spiel gewonnen wurde
            if (game.isGameWon()) {
                statusLabel.setText("Glückwunsch! Du hast das Wort erraten.");
                guessButton.setEnabled(false); // Deaktiviere Ratebutton
                restartButton.setEnabled(true); // Aktiviere Neustart-Button
            }
            // Prüft, ob das Spiel verloren wurde
            else if (game.isGameOver()) {
                statusLabel.setText("Game Over! Das gesuchte Wort war: " + game.getWordToGuess());
                guessButton.setEnabled(false); // Deaktiviere Ratebutton
                restartButton.setEnabled(true); // Aktiviere Neustart-Button
            } else {
                statusLabel.setText(" ");
            }

            // Lösche das Eingabefeld nach der Eingabe
            guessField.setText("");
            updateHangmanImage();  // Aktualisiere das Bild bei jedem Versuch
        }
    }

    // Methode zum Zurücksetzen des Spiels
    private void resetGame() {
        game.restartGame();  // Nutze die neue Methode in der Hangman-Klasse
        wordLabel.setText(formatWordForDisplay(game.getCurrentWordState()));
        remainingAttemptsLabel.setText("Verbleibende Versuche: " + game.getRemainingAttempts());
        guessedLettersArea.setText("");
        statusLabel.setText("");  // Lösche Status-Nachricht nach dem Zurücksetzen
        guessButton.setEnabled(true);  // Reaktiviere Ratebutton
        restartButton.setEnabled(false);  // Deaktiviere Neustartbutton bis Spielende
        updateHangmanImage(); // Bild zurücksetzen
    }

    // Methode zum Laden und Skalieren des Bildes basierend auf den verbleibenden Versuchen
    private void updateHangmanImage() {
        int remainingAttempts = game.getRemainingAttempts();
        int imageIndex = 11 - remainingAttempts; // Bildpfad (0.png bis 11.png)

        String imagePath = "./images/" + imageIndex + ".png"; // Pfad zum Bild

        // Lade das Bild und skalieren es, falls es existiert
        try {
            hangmanImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            hangmanImage = null;
        }

        // Repaint das Panel, damit das Bild neu gezeichnet wird
        imagePanel.repaint();
    }

    // Methode zur Formatierung des Wortes mit Leerzeichen zwischen den Zeichen
    private String formatWordForDisplay(String word) {
        StringBuilder formattedWord = new StringBuilder();
        for (char c : word.toCharArray()) {
            formattedWord.append(c).append(" ");  // Füge ein Leerzeichen nach jedem Zeichen hinzu
        }
        return formattedWord.toString().trim();  // Entferne das letzte Leerzeichen
    }
}
