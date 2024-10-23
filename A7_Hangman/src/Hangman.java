import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Random;

public class Hangman {
    private String wordToGuess;       // Das gesuchte Wort
    private int maxAttempts;          // Maximale Anzahl an Versuchen
    private int remainingAttempts;    // Verbleibende Versuche
    private Set<Character> guessedLetters; // Bereits geratene Buchstaben
    private Set<Character> correctLetters; // Korrekt geratene Buchstaben
    private Set<String> guessedWords; // Bereits geratene Wörter
    private List<String> wordList;    // Liste der verfügbaren Wörter für das Spiel

    public Hangman(List<String> wordList) {
        this.wordList = wordList;
        restartGame();  // Starte das Spiel mit einem zufälligen Wort und 11 Versuchen
    }

    // Methode um das Spiel zurückzusetzen (Neustart)
    public void restartGame() {
        Random random = new Random();
        this.wordToGuess = wordList.get(random.nextInt(wordList.size())).toLowerCase();
        this.maxAttempts = 11;  // Hardcoded maximale Versuche
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
            currentState.append(' ');  // Füge Leerzeichen für bessere Lesbarkeit hinzu
        }

        return currentState.toString().trim();  // Entferne das letzte Leerzeichen
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

    // Methode um alle geratenen Buchstaben und Wörter als String zurückzugeben
    public String getAllGuessedStrings() {
        StringBuilder guessed = new StringBuilder("Geratene Buchstaben: ");
        for (char letter : guessedLetters) {
            guessed.append(letter).append(' ');
        }
        guessed.append("\nGeratene Wörter: ");
        for (String word : guessedWords) {
            guessed.append(word).append(' ');
        }
        return guessed.toString();
    }
}
