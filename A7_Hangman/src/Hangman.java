import java.util.HashSet;
import java.util.Set;

public class Hangman {
    private String wordToGuess;       // Das gesuchte Wort
    private int maxAttempts;          // Maximale Anzahl an Versuchen
    private int remainingAttempts;    // Verbleibende Versuche
    private Set<Character> guessedLetters; // Bereits geratene Buchstaben
    private Set<Character> correctLetters; // Korrekt geratene Buchstaben
    private Set<String> guessedWords; // Bereits geratene Wörter

    public Hangman(String wordToGuess, int maxAttempts) {
        this.wordToGuess = wordToGuess.toLowerCase();
        this.maxAttempts = maxAttempts;
        this.remainingAttempts = maxAttempts;
        this.guessedLetters = new HashSet<>();
        this.correctLetters = new HashSet<>();
        this.guessedWords = new HashSet<>();
    }

    // Methode um Buchstaben oder ein ganzes Wort zu raten
    public boolean guessLetterOrWord(String guess) {
        // Überprüfen, ob der String leer ist
        if (guess == null || guess.isEmpty()) {
            System.out.println("Eingabe ist leer. Bitte einen Buchstaben oder ein Wort eingeben.");
            return false;
        }

        guess = guess.toLowerCase();

        // Wenn die Länge des Strings 1 ist, wird es als Buchstabe behandelt
        if (guess.length() == 1) {
            char letter = guess.charAt(0);

            // Überprüfen, ob der Buchstabe bereits geraten wurde
            if (guessedLetters.contains(letter)) {
                System.out.println("Dieser Buchstabe wurde bereits geraten.");
                return false;
            }

            // Buchstaben zur Liste der geratenen Buchstaben hinzufügen
            guessedLetters.add(letter);

            // Wenn der Buchstabe im Wort ist
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
            // Überprüfen, ob das Wort bereits geraten wurde
            if (guessedWords.contains(guess)) {
                System.out.println("Dieses Wort wurde bereits geraten.");
                return false;
            }

            // Wort zur Liste der geratenen Wörter hinzufügen
            guessedWords.add(guess);

            // Wenn das geratene Wort korrekt ist
            if (guess.equals(wordToGuess)) {
                correctLetters.addAll(lettersInWord());
                return true;
            } else {
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
