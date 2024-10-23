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
        letter = Character.toLowerCase(letter);

        if (guessedLetters.contains(letter)) {
            System.out.println("Dieser Buchstabe wurde bereits geraten.");
            return false;
        }

        guessedLetters.add(letter);

        if (wordToGuess.indexOf(letter) >= 0) {
            correctLetters.add(letter);
            return true;
        } else {
            remainingAttempts--;
            return false;
        }
    }


    
    // Methode um den aktuellen Zustand des Wortes (erratene Buchstaben) zurückzugeben
    public String getCurrentWordState() {
        StringBuilder currentState = new StringBuilder();


        for (char letter : wordToGuess.toCharArray()) {
            if (correctLetters.contains(letter)) {
                currentState.append(letter);
            } else {
                currentState.append('_');
            }
        }

        return currentState.toString();
    }


}
