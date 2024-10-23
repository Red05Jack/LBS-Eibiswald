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
    public boolean guessLetterOrWord(String guess) {
        guess = guess.toLowerCase();

        // Wenn die Länge des Strings 1 ist, wird es als Buchstabe behandelt
        if (guess.length() == 1) {
            char letter = guess.charAt(0);

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
        // Wenn die Länge des Strings größer als 1 ist, wird es als Wort behandelt
        else {
            if (guess.equals(wordToGuess)) {
                // Der Spieler hat das richtige Wort geraten
                correctLetters.addAll(lettersInWord());
                return true;
            } else {
                // Der Spieler hat das falsche Wort geraten
                remainingAttempts--;
                return false;
            }
        }
    }

    // Hilfsmethode, um alle Buchstaben des gesuchten Wortes als Set zurückzugeben
    private Set<Character> lettersInWord() {
        Set<Character> letters = new HashSet<>();
        for (char letter : wordToGuess.toCharArray()) {
            letters.add(letter);
        }
        return letters;
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


    // Methode um zu prüfen, ob das Spiel gewonnen wurde
    public boolean isGameWon() {
        for (char letter : wordToGuess.toCharArray()) {
            if (!correctLetters.contains(letter)) {
                return false;
            }
        }
        return true;
    }

    // Methode um zu prüfen, ob das Spiel verloren wurde
    public boolean isGameOver() {
        return remainingAttempts <= 0;


    }


    // Methode um die verbleibenden Versuche zu erhalten
    public int getRemainingAttempts() {
        return remainingAttempts;
    }



    // Methode um das gesuchte Wort zurückzugeben
    public String getWordToGuess() {
        return wordToGuess;
    }


}
