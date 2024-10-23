import java.util.HashSet;
import java.util.Set;

public class Hangman {
    private String wordToGuess;       // Das gesuchte Wort
    private int maxAttempts;          // Maximale Anzahl an Versuchen
    private int remainingAttempts;    // Verbleibende Versuche
    private Set<Character> guessedLetters; // Bereits geratene Buchstaben
    private Set<Character> correctLetters; // Korrekt geratene Buchstaben

    public Hangman(String wordToGuess, int maxAttempts) {
        this.wordToGuess = wordToGuess.toLowerCase();
        this.maxAttempts = maxAttempts;
        this.remainingAttempts = maxAttempts;
        this.guessedLetters = new HashSet<>();
        this.correctLetters = new HashSet<>();
    }

    // Methode um zu raten und zu prüfen, ob der Buchstabe im Wort ist
    public boolean guessLetter(char letter) {
    }


}
